package sample;

/**
 * Created by shawn on 10/12/2015.
 */
public class Queue<T>
{
    private Node<T> first, rear;
    private int length;

    public Queue()
    {
        first = null;
        rear = null;
        length = 0;
    }
    private boolean isEmpty(){
        if(first == null)
            return  true;
        else
           return false;
    }
    public void add(Node<T> element)
    {
        if(isEmpty())
            first = element;
        else
            rear.setNext(element);
        rear = element;
        length++;
    }
    public boolean remove(T data)
    {
        try
        {
            Node<T> current = first.getNext();
            Node<T> prev = first;
            if(first.getData().equals(data))
            {
                first = first.getNext();
                length--;
            }
            else
            {
                while(current != null)
                {
                    if(current.getData().equals(data))
                    {
                        prev.setNext(current.getNext());
                        length--;
                        return true;
                    }
                    prev = current;
                    current = current.getNext();
                }
            }

        }
        catch (Exception e) {}
        return false;
    }
    public boolean contain(T data)
    {
        try
        {
            Node<T> current = first;
            while(current != null)
            {
                if(current.getData().equals(data))
                    return true;
                current = current.getNext();
            }
        }
        catch (Exception e) {}
        return false;
    }
    public void enqueue(){}
    public Node<T> dequeue()
    {
        Node<T> result = null;
        try
        {
           if(first != null)
           {
               result = first;
               first = first.getNext();
           }
        }
        catch (Exception e) {}
        return result;
    }

    @Override
    public String toString() {
        String result ="";
        Node current = first;
        if(isEmpty())
            try {
                throw new EmptyException();
            } catch (EmptyException e) {
                e.printStackTrace();
            }
        while(current != null)
        {
            result += current.getData();
            result += "\n";
            current = current.getNext();
        }
        return result;
    }
}
