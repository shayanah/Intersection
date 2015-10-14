package sample;

import java.sql.Time;

/**
 * Created by shawn on 10/13/2015.
 */
public class Car
{
    private long id;
    private String name;
    private Time timeArival;
    private Time timePassed;
    private Time timeWaited;

    public Car(long id, String name, Time timeArival)
    {
        this.id = id;
        this.name = name;
        this.timeArival = timeArival;
    }
    public Car(long id)
    {
        this.id = id;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Time getTimeArival() {
        return timeArival;
    }

    public void setTimeArival(Time timeArival) {
        this.timeArival = timeArival;
    }

    public Time getTimePassed() {
        return timePassed;
    }

    public void setTimePassed(Time timePassed) {
        this.timePassed = timePassed;
    }

    public Time getTimeWaited() {
        return timeWaited;
    }

    public void setTimeWaited(Time timeWaited) {
        this.timeWaited = timeWaited;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car)) return false;

        Car car = (Car) o;

        return id == car.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", timeArival=" + timeArival +
                ", timePassed=" + timePassed +
                ", timeWaited=" + timeWaited +
                '}';
    }
}
