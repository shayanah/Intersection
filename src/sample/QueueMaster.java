package sample;

/**
 * Created by shawn on 10/19/2015.
 */
public interface QueueMaster<T>
{
    public void add(Node<T> element);
    public Node<T> dequeue();
}
