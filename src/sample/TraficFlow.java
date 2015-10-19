package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;

import java.sql.Time;
import java.util.Random;

/**
 * Created by shawn on 10/13/2015.
 */
public class TraficFlow implements Runnable
{
    private Queue<Car> q;
    private Queue<Car> qResult;
    private long id;
    private Random randomGenerator;
    private char[] alfa = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p',
    'q','r','s','t','u','v','w','x','y','z'};
    private boolean intersectionOpen;

    public TraficFlow(Queue<Car> q)
    {
        id = 0;
        this.q = q;
        qResult = new Queue<Car>();
        randomGenerator = new Random();
    }
    public TraficFlow()
    {
        id = 0;
        this.q = new Queue<Car>();
        qResult = new Queue<Car>();
        randomGenerator = new Random();
    }
    public TraficFlow(int laneNumber)
    {
        id = 0;
        this.q = new Queue<Car>();
        qResult = new Queue<Car>();
        randomGenerator = new Random();
        lane = laneNumber;
    }
    private long idGenerator()
    {
        id++;
        return id;
    }

    public String nameGenerator()
    {
        char r ;
        String result = "";
        for(int i =0 ; i < 10 ; i++)
        {
            r = alfa[randomGenerator.nextInt(alfa.length)];
            result += r ;
        }
        return result;
    }
    public void runClock()
    {
        boolean running  = true;
        new Thread()
        {
            public void run()
            {
                long pre = System.currentTimeMillis();
                while(running)
                {
                    long now = System.currentTimeMillis();

                    if(now == pre + 1000L)
                    {
                        System.out.println(new Time(now));
                        pre = System.currentTimeMillis();
                    }

                }
            }
        }.start();
    }
    public String currentTime = null;
    public SimpleStringProperty CurrentTime = new SimpleStringProperty();
    public void getClock()
    {
        boolean running  = true;
        new Thread()
        {
            public void run()
            {
                long pre = System.currentTimeMillis();
                Time time = new Time(pre);
                currentTime = time.toString();
                CurrentTime.setValue(currentTime);
                while(running)
                {
                    long now = System.currentTimeMillis();

                    if(now == pre + 1000L)
                    {
                        time = new Time(now);
                        currentTime = time.toString();
                        CurrentTime.setValue(currentTime);
                        pre = System.currentTimeMillis();
                    }

                }
            }
        }.start();
    }
    public int lane ;
    //private long duration = 1000 * 60 * 1/4;
    private long end ;
    private long duration;
    private long allDuration;
    public long stopTime, startTime, traficPeriod;
    public void calculateStopTime(TraficFlow f2,long duration)
    {
        try
        {
            if(this.q.getLength() == f2.q.getLength())
            {
                this.duration = duration/2;
                f2.duration = duration/2 ;
            }
            else if(this.q.getLength() > f2.q.getLength())
            {
                int g = this.q.getLength() - f2.q.getLength() ;
                long gap = g * 1000;
                gap = gap/2;
                if(gap < duration)
                {
                    this.duration = duration / 2 + gap;
                    f2.duration = duration / 2 - gap;
                }
                else
                {
                    this.duration = duration / 2 + duration/4;
                    f2.duration = duration / 2 - duration/4;
                }

                //this.duration = duration * this.q.getLength()/( this.q.getLength() + f2.q.getLength() );
                //f2.duration = duration * f2.q.getLength()/( this.q.getLength() + f2.q.getLength() );


            }
            else
            {
                int g = f2.q.getLength() - this.q.getLength() ;
                long gap = g * 500L;
                //
                if(gap > duration)
                {
                    this.duration = duration / 2 - gap;
                    f2.duration = duration / 2 + gap;
                }

                else
                {
                    this.duration = duration / 2 - duration/4;
                    f2.duration = duration / 2 + duration/4;
                }
            }
            this.startTime = System.currentTimeMillis();
            this.traficPeriod = this.startTime + duration;
            this.stopTime = this.startTime + this.duration;
            this.allDuration = duration;

            f2.startTime = this.stopTime;
            f2.traficPeriod = this.startTime + duration;
            f2.stopTime = f2.startTime + f2.duration ;
            f2.allDuration = duration;

        }
        catch (Exception e)
        {
        }
    }
    public void calculateStopTime(long duration)
    {
        try
        {
            this.duration = duration/2 ;
            this.startTime = System.currentTimeMillis();
            this.traficPeriod = this.startTime + duration;
            this.stopTime = this.startTime + this.duration;

        }
        catch (Exception e)
        {
        }

    }
    private void waitandGo(long waitingTime)
    {
        try {
            Thread.sleep(waitingTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public synchronized void addCarToIntersection()
    {
        int rArrival;
        while(System.currentTimeMillis() < traficPeriod )
        {
            if(System.currentTimeMillis() < stopTime && System.currentTimeMillis() > startTime)
                rArrival = randomGenerator.nextInt(3);
            else
                rArrival = randomGenerator.nextInt(2);
            for(int i =0 ; i < rArrival; i++)
            {
                waitandGo(1000L);
                q.add(new Node<Car>( new Car(idGenerator(),nameGenerator(),new Time( System.currentTimeMillis() ) ) ) );
            }
            //waitandGo(500L);
            if(System.currentTimeMillis() < stopTime && System.currentTimeMillis() > startTime)
            {
                System.out.println("Lane " + lane +": \n"+ q);
                Node<Car> temp1 = q.dequeue();
                Node<Car> temp2 = q.dequeue();
                long currentTime = System.currentTimeMillis();
                long timewatied,timewatied2;
                if(temp1 != null)
                {
                    timewatied= currentTime - temp1.getData().getTimeArival().getTime();
                    temp1.getData().setTimeWaited(new Time(timewatied));
                    temp1.getData().setTimePassed(new Time(currentTime));
                    qResult.add( temp1 );
                }
                if(temp2 != null)
                {
                    timewatied2 = currentTime - temp2.getData().getTimeArival().getTime();
                    temp2.getData().setTimeWaited(new Time(timewatied2));
                    temp2.getData().setTimePassed(new Time(currentTime));
                    qResult.add( temp2 );
                }
            }
            else
            {
                pre = timeToOpenShow();
                System.out.println("Lane " + lane +": "+ q);
                waitandGo(500L);
            }
        }
    }
    private long pre;
    private long timeToOpenShow()
    {
        long now = System.currentTimeMillis();
        long period = traficPeriod - now;
        long remainToOpen;

        if(!( System.currentTimeMillis() < stopTime && System.currentTimeMillis() > startTime ) )
        {
            if(now > startTime)
                remainToOpen = traficPeriod - now;
            else
                remainToOpen = startTime - now;
            if(now >= pre + 1000L )
            {
                pre = now;
                System.out.println("Lane " + lane + " will open in " + remainToOpen/1000 );
                //System.out.println("Lane " + lane + " open in " + open );
            }
        }

        return pre;
    }

    @Override
    public synchronized void run()
    {
        Time t = new Time(startTime);
        System.out.println("lane " + lane + "\t" + t + "\t" + new Time(stopTime) + "\t" + new Time(traficPeriod) + "\t"
                + duration + "\t" + q.getLength());
        addCarToIntersection();

    }
    public void print()
    {
        while(!( q.isEmpty() )){ qResult.add( q.dequeue() );}
        System.out.println("Lane " + lane + " overview" + ": \n" + qResult);

    }
}
