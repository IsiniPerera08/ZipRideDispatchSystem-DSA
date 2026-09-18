//Name: Isini Ayansa Perera
//Student Id: 23601321
//File: LinkedList.java
//Purpose: Implements a singly linked list data structure, provides insertion,
//       removal, traversal, and utility operations used throughout the ZipRide system.
//reference: lecture slides

import java.util.NoSuchElementException;

public class LinkedList {
    private ListNode head;
    private ListNode tail;
    private int count;

    public LinkedList() {
        head = null;
        tail = null;
        count= 0;
    }

    //INSERT FIRST
    public void insertFirst(Object newValue){
        ListNode newNode= new ListNode(newValue);
        if(isEmpty()){
            head= newNode;
            tail= newNode;
        }else{
            newNode.setNext(head);
            head= newNode;
        }
        count++;
    }

    //INSERT LAST
    public void insertLast(Object newValue) {
        ListNode newNode = new ListNode(newValue);

        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            tail.setNext(newNode);
            tail = newNode;
        }
        count++;
    }

    //PEEKING FIRST
    public Object peekFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty — cannot peekFirst.");
        }
        return head.getValue();
    }

    //PEEKING LAST
    public Object peekLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty — cannot peekLast.");
        }
        return tail.getValue();
    }

    //REMOVE FIRST
    public Object removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty — cannot removeFirst.");
        }

        Object removeVal = head.getValue();

        if (head == tail){
            head = null;
            tail = null;
        } else {
            head = head.getNext();
        }
        count--;
        return removeVal;
    }

    //REMOVE LAST
    public Object removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty — cannot removeLast.");
        }

        Object removeVal = tail.getValue();

        if (head == tail) {
            head = null;
            tail = null;
        } else {
            ListNode curr= head;
            while(curr.getNext()!= tail){
                curr= curr.getNext();
            }
            tail= curr;
            tail.setNext(null);
        }
        count--;
        return removeVal;
    }

    // ------------------------------------------------------------------ //
    //  UTILITY
    // ------------------------------------------------------------------ //

    //  * Returns true if the list has no nodes.
    public boolean isEmpty(){
        return head==null;
    }

    public int getCount(){
        return count;
    }

    public ListNode getHead(){
        return head;
    }

    //   Displays the list from head to tail in the format: head <-> node1 <->
    //   node2 <-> ... <-> tail
    @Override
    public String toString() {
        if (isEmpty()) {
            return "[ List empty ]";
        }
        StringBuilder sb = new StringBuilder("HEAD [ ");
        ListNode curr = head;
        while (curr != null) {
            sb.append(curr.getValue());
            if (curr.getNext() != null) {
                sb.append(" <-> ");
            }
            curr = curr.getNext();
        }
        sb.append(" ] TAIL");
        return sb.toString();
    }
}