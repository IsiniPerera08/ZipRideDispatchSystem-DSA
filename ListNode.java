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
