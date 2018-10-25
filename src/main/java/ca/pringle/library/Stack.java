package ca.pringle.library;


/**
 * Stack
 * a size of -1 is a stack of infinite size
 * otherwise the stack is of the size given,
 * provided size > 0. if size <= 0, size = -1.
 */

public final class Stack {
    public final int maxSize;
    private LinkedList list = new LinkedList();


    /**
     * create a new dynamic stack.
     */
    public Stack() {
        maxSize = -1;
    }

    /**
     * create a new stack of given size. If size <= 0, stack is dynamic.
     */
    public Stack(int size) {
        if (size > 0) maxSize = size;
        else maxSize = -1;
    }

    /**
     * put an object on the stack. Returns true if successful, false otherwise.
     */
    public boolean push(Object o) {
        if (maxSize == -1 || list.size() < maxSize) {
            list.insert(o);
            return true;
        }
        return false;
    }

    /* get an object off the stack. If none, returns null. */
    public Object pop() {
        Object o = null;

        if (!isEmpty()) {
            o = list.get();
            list.delete();
        }

        return o;
    }

    /* look at the top of the stack without removing the element. */
    public Object peek() {
        Object o = null;

        if (!isEmpty()) o = list.get();

        return o;
    }

    /**
     * returns the size of the stack. If dynamic, returns -1.
     */
    public int size() {
        return maxSize;
    }

    /**
     * returns true if the stack is empty.
     */
    public boolean isEmpty() {
        if (list.size() == 0) return true;

        return false;
    }

    /**
     * returns true if the stack is full.
     */
    public boolean isFull() {
        if (maxSize != -1 && list.size() == maxSize) return true;
        return false;
    }


    /**
     * standard toString method.
     */
    public String toString() {
        return list.toString();
    }


}    //End of class Stack