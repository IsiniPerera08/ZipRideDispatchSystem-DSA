//Name: Isini Ayansa Perera
//Student Id: 23601321
//File: ListNode.java
//Purpose: Represents a node within the linked list,stores an object value and a reference to the next node.
//reference: lecture slides
//cited from the practical submission

public class ListNode {

    private Object value;
    private ListNode next;

    public ListNode(Object inValue) {
        value = inValue;
        next = null;
    }

    // ------------------------------------------------------------------ //
    //  Accessors
    // ------------------------------------------------------------------ //
    public Object getValue() {
        return value;
    }

    public ListNode getNext() {
        return next;
    }

    // ------------------------------------------------------------------ //
    //  Mutators
    // ------------------------------------------------------------------ //
    public void setValue(Object inValue) {
        value = inValue;
    }

    public void setNext(ListNode newNext) {
        next = newNext;
    }
}