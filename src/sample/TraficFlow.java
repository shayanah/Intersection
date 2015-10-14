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
    private long id;
    private Random randomGenerator;
    private char[] alfa = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p',
    'q','r','s','t','u','v','w','x','y','z'};
    private boolean intersectionOpen;

    public TraficFlow(Queue<Car> q)
    {
        id = 0;
        this.q = q;
        randomGenerator = new Random();
    }
    public TraficFlow()
    {
        id = 0;
        this.q = new Queue<Car>();
        randomGenerator = new Random();
        intersectionOpen = true;
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
                while(running)
                {
                    long now = System.currentTimeMillis();

                    if(now == pre + 1000L)
                    {
                        time = new Time(now);
                        currentTime = time.toString();
                        pre = System.currentTimeMillis();
                    }

                }
            }
        }.start();
    }
    public synchronized void addCarToIntersection()
    {
        int rArrival;
        long duration = 1000 * 60 * 1/4;
        long stopSign = System.currentTimeMillis() + duration/2;
        long end = System.currentTimeMillis() + duration ;

        while(System.currentTimeMillis() < end )
        {
            rArrival = randomGenerator.nextInt(2);
            for(int i =0 ; i < rArrival; i++)
            {
                q.add(new Node<Car>( new Car(idGenerator(),nameGenerator(),new Time( System.currentTimeMillis() ) ) ) );

            }

            System.out.println(q);
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(intersectionOpen)
            {
                q.dequeue();
                q.dequeue();
            }
            if(System.currentTimeMillis() > stopSign )
            {
                intersectionOpen = false;
                System.out.println("Stop Sign");
            }
        }
    }
    @Override
    public synchronized void run()
    {
        intersectionOpen = true;
        addCarToIntersection();

    }
}
