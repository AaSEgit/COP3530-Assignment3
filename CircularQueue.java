import java.util.Arrays;

/* This program implements a circular queue using an array. */

public class CircularQueue<E> {
    private int currentSize; //Current Circular Queue Size
    private E[] circularQueueElements;
    public int capacity; //Circular Queue maximum size

    private int rear;
    private int front;
    private boolean lastOperationIsPush;

    // constructor
    public CircularQueue (int maxSize) {
        capacity = maxSize;
        circularQueueElements = (E[]) new Object[capacity];
        currentSize = 0;
        front = rear = -1;
        lastOperationIsPush = false;
    }

    // Returns the number of non-null elements in the array
    public int size() {
        int numElements = currentSize;
        if (front < rear) {
            numElements = rear - front;
        }
        else if (front > rear) {
            numElements = capacity - (front - rear);
        }
        else {
            numElements = 0;
        }
        return numElements;
    }

    // adds element to the rear
    public E enqueue(E element) throws QueueFullException {
        if (isFull()) {
            throw new QueueFullException("Circular Queue is full. Element cannot be added.");
        }
        else {
            rear = (rear + 1) % capacity;
            circularQueueElements[rear] = element;

            if (currentSize < capacity)
                currentSize++;
            else
                currentSize = capacity;

            lastOperationIsPush = true;
        }
        return element;
    }

    // removes element from the front
    public E dequeue () throws QueueEmptyException {
        E deQueuedElement;

        if (isEmpty()) {
            throw new QueueEmptyException("Circular Queue is empty. Element cannot be retrieved.");
        }
        else {
            if(front == capacity - 1)
                front = -1;
            
            deQueuedElement = circularQueueElements[front + 1];
            circularQueueElements[front + 1] = null;
            front = (front + 1) % capacity;
            currentSize--;
            lastOperationIsPush = false;
        }
        return deQueuedElement;
    }

    // remove kth element from the queue, shift elements accordingly
    public void delete (int k) {
        if (isEmpty()) {
            throw new QueueEmptyException();
        }
        else {
            for (int i = (front + k) % capacity; i < capacity; i++) {
                circularQueueElements[i] = circularQueueElements[(i + 1) % capacity];
            }
            currentSize--;
            circularQueueElements[rear] = null;
            rear = (rear - 1) % capacity;
        }
    }

    // returns true if queue is empty
    public boolean isEmpty() {
        return ((currentSize == 0) && (front == rear) && !lastOperationIsPush);
    }

    // returns true if queue is full
    public boolean isFull () {
        return ((currentSize == capacity) && (front == rear) && lastOperationIsPush);
    }

    // prints elements of the queue
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Circular Queue: [ ");
        for (int i = 0; i < capacity; i++) {
            sb.append(circularQueueElements[i] + " ");
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main (String args[]) {
        CircularQueue<Integer> q = new CircularQueue(5);

        // Create a full circular queue
        q.enqueue(15);
        q.enqueue(16);
        q.enqueue(17);
        q.enqueue(18);
        q.enqueue(19);
        System.out.println("\nFull Circular Queue: " + q);
        System.out.println("Current size: " + q.size());

        q.delete(3);
        System.out.println("\nDeleted the 3rd element in the circular queue");
        System.out.println(q);
        System.out.println("Current size: " + q.size());

        // Overwrite 2 elements of full queue
        System.out.println("\nEnqueued element: " + q.enqueue(20));
        System.out.println(q);
        System.out.println("Current size: " + q.size());

        // Dequeue 2 elements
        System.out.println("\nDequeued element: " + q.dequeue());
        System.out.println("Dequeued element: " + q.dequeue());
        System.out.println(q);
        System.out.println("Current size: " + q.size());

        // Enqueue 1 element
        System.out.println("\nEnqueued element: " + q.enqueue(22));
        System.out.println(q);
        System.out.println("Current size: " + q.size());

        System.out.println("\nDequeued element: " + q.dequeue());
        System.out.println(q);
        System.out.println("Current size: " + q.size());
    }
}