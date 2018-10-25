package ca.pringle.library;

/**
 * Linked list.
 */

public final class LinkedList {
    private int size;
    private Node current = null;
    private Node iter = null;
    private Node sentinel = null;


    public LinkedList() {
        size = 0;
        sentinel = new Node(null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        current = sentinel;
        iter = sentinel;
    }


    /**
     * Add data to the end of the list.
     */
    public void insert(Object o) {
        insert(o, size);
    }


    /**
     * Add data to the beginning of the list.
     */
    public void insertFirst(Object o) {
        insert(o, 0);
    }


    /**
     * inserts the object at the given index.
     * throws an exception if index is not in range [0,length of list].
     */
    public void insert(Object o, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index received: " + index + ", valid range: [0, " + size + "]");
        }

        findIndex(index);
        privateInsert(o);
    }


    /**
     * inserts at the current element in the list.
     */
    private void privateInsert(Object o) {
        Node n = new Node(o);
        n.next = current;
        n.prev = current.prev;

        current.prev.next = n;
        current.prev = n;


        size = size + 1;
    }


    /**
     * deletes the last element in the list.
     */
    public void delete() {
        delete(size - 1);
    }


    /**
     * deletes the first element in the list.
     */
    public void deleteFirst() {
        delete(0);
    }


    /**
     * deletes the index'thed element in the list.
     */
    public void delete(int index) {
        if (index > (size - 1) || index < 0) {
            throw new IndexOutOfBoundsException("Index received: " + index + ", valid range: [0, " + (size - 1) + "]");
        }

        findIndex(index);
        privateDelete();
    }


    /**
     * deletes the current element in the list.
     */
    private void privateDelete() {
        Node t = current;
        current = current.prev;

        t.prev.next = t.next;
        t.next.prev = t.prev;
        t.next = null;
        t.prev = null;

        size = size - 1;
    }


    private void findIndex(int index) {
        if (size - index < index) {
            current = sentinel;
            for (int i = 0; i < size - index; i++) {
                current = current.prev;
            }
        } else {
            current = sentinel.next;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        }
    }


    /**
     * resets the iterator.
     */
    public void iter() {
        iter = sentinel;
    }

    public boolean hasNext() {
        if (iter.next != sentinel) return true;
        return false;
    }

    public Object next() {
        iter = iter.next;
        return iter.data;
    }


    /**
     * get the size of the list.
     */
    public int size() {
        return size;
    }


    /**
     * gets current element in list.
     */
    public Object get() {
        return current.data;
    }


    /**
     * standard toString method.
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        StringBuffer s = new StringBuffer();
        boolean first = true;
        Node walker = sentinel.next;

        while (walker != sentinel) {
            if (first) {
                s.append(walker);
                first = false;
            } else {
                s.append(", ");
                s.append(walker);
            }

            walker = walker.next;
        }

        sb.append("[|S|=");
        sb.append(size);
        sb.append(", S={");
        sb.append(s);
        sb.append("}");

        return sb.toString();
    }


    private final class Node {
        private Object data = null;
        private Node next = null;
        private Node prev = null;


        public Node(Object o) {
            data = o;
        }


        public String toString() {
            StringBuffer s = new StringBuffer();

            s.append("[");
            s.append(data);
            s.append("]");

            return s.toString();
        }
    }    //End of class Node

}    //End of class LinkedList
