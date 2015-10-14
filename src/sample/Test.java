package sample;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;

import static java.lang.String.*;

/**
 * Created by shawn on 10/13/2015.
 */
public class Test
{
    public static void main(String []args) throws InterruptedException {
        Queue<Car> q = new Queue<>();
        q.add(new Node<>(new Car(3,"c1",new Time(System.currentTimeMillis()))));
        q.add(new Node<>(new Car(4,"c2",new Time(System.currentTimeMillis()))));
        q.add(new Node<>(new Car(5,"c3",new Time(System.currentTimeMillis()))));
        System.out.println(q);
        q.dequeue();
        System.out.println( q.contain(new Car(4, "c2", new Time(5))) );
        System.out.println( q.contain(new Car(5)) );
        System.out.println(q);
        System.out.println(System.currentTimeMillis());
        System.out.println(LocalTime.now());
        Random r = new Random();
        int a = 97;
        TraficFlow f = new TraficFlow();
        String s = f.nameGenerator();
        System.out.println(s);
        Thread t = new Thread(f);
        t.start();
        f.runClock();
        t.join();

    }
}
