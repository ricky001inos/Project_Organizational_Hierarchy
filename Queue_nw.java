public class Queue_nw<E> {

    // instance variables
    private E[] data; // generic array used for storing elements
    private int f = 0; // index of the front element
    private int sz = 0; // size of queue

    public Queue_nw() {
        this(100);
    } // constructor

    public Queue_nw(int size) { // constructs queue with given capacity
        data = (E[]) new Object[size];
    }

    // Tests whether the queue is empty. ∗/
    public boolean isEmpty() {
        return (sz == 0);
    }

    //Inserts an element at the rear of the queue. ∗/
    public void enqueue(E e) throws IllegalStateException {
        if (sz == data.length) throw new IllegalStateException("Queue is full");
        int avail = (f + sz) % data.length;
        data[avail] = e;
        sz++;
    }

    // Removes and returns the first element of the queue (null if empty). ∗/
    public E dequeue() {
        if (isEmpty()) return null;
        E answer = data[f];
        data[f] = null; // dereference to help garbage collection
        f = (f + 1) % data.length;
        sz--;
        return answer;
    }
}
