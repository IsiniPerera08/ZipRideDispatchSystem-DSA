// Name: Isini Ayansa Perera
// Student ID:23601321
// File: Stack.java 
// Purpose: Implements a stack using a linked list, provides LIFO (Last-In-First-Out) functionality
//          for graph traversal and other system operations.
// Cited from Practical 2 submission — refactored to use DSALinkedList and lec slides

import java.util.NoSuchElementException;

public class Stack {
    private LinkedList list;

    public Stack() {
        list = new LinkedList();
    }

    // ------------------------------------------------------------------ //
    //  Mutators
    // ------------------------------------------------------------------ //

    //  Pushes a value onto the top of the stack (insertFirst). 
    public void push(Object value) {
        list.insertFirst(value);
    }
    //  Removes and returns the top value (peekFirst + removeFirst).
    public Object pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty — cannot pop...");
        }
        return list.removeFirst();
    }

    // ------------------------------------------------------------------ //
    //  Accessors
    // ------------------------------------------------------------------ //

    //  Returns the top value without removing it.
    public Object top() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty — cannot peek...");
        }
        return list.peekFirst();
    }

    //==============UTILITIES==============
    
    //  Returns true if the stack has no elements.
    public boolean isEmpty() {
        return list.isEmpty();
    }

    //  Returns the number of elements in the stack.    
    public int getCount() {
        return list.getCount();
    }

    @Override
    public String toString() {
        return "Stack (top -> bottom): " + list.toString();
    }
}
