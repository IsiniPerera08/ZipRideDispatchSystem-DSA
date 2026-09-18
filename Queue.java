//Name: Isini Ayansa Perera
//Student Id: 23601321
//File: Queue.java
//Purpose: Implements a queue Abstract Data Type (ADT) using a linked list, supports FIFO operations
//       and is used by graph traversal algorithms such as Breadth First Search (BFS).
// Cited from Practical 2 submission — refactored to use DSALinkedList. and lecture slides

import java.util.NoSuchElementException;

public class Queue {

    private LinkedList list;

    public Queue() {
        list = new LinkedList();
    }

    // ------------------------------------------------------------------ //
    //  Mutators
    // ------------------------------------------------------------------ //
    //  Adds a value to the rear of the queue (insertLast).
    public void enqueue(Object value) {
        list.insertLast(value);
    }

    //  Removes and returns the front value (peekFirst + removeFirst).
    public Object dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty — cannot dequeue...");
        }
        return list.removeFirst();
    }

    // ------------------------------------------------------------------ //
    //  Accessors
    // ------------------------------------------------------------------ //
    //  Returns the front value without removing it.
    public Object peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty — cannot peek...");
        }
        return list.peekFirst();
    }

    //  UTILITIES
    public boolean isEmpty() {
        return list.isEmpty();
    }

    //  Returns the number of elements in the queue.
    public int getCount() {
        return list.getCount();
    }

    @Override
    public String toString() {
        return "Queue (front -> rear): " + list.toString();
    }
}
