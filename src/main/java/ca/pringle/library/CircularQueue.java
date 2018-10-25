package ca.pringle.library;


import java.util.HashMap;


public final class CircularQueue implements Cloneable, Testable {

    Object[] queue = null;
    int front;
    int size;


    public CircularQueue(int sizeParam)
            throws SizeException {
        if (sizeParam < 2)
            throw new SizeException("Circular queue expects a sizeParam of 2 or greater.");

        queue = new Object[sizeParam];
        front = 0;
        size = 0;
    }


    /**
     * add object to rear of queue
     */
    public void add(Object o) {
        queue[map(front + size)] = o;

        if (size == queue.length)
            front = map(front + 1);

        if (size < queue.length) size = size + 1;
    }

    /**
     * remove the object at the front of the queue
     */
    public void remove() {
        if (size > 0) {
            size = size - 1;
            front = map(front + 1);
        }
    }

    /**
     * get object at the front of the queue
     */
    public Object get() {
        return queue[front];
    }

    /**
     * get object indexed 'index' places from the front of the queue
     */
    public Object get(int index) {
        return queue[map(front + index)];
    }

    /**
     * total number of elements that this queue will store
     */
    public int size() {
        return queue.length;
    }

    /**
     * the number of elements currently stored in the queue
     */
    public int numElements() {
        return size;
    }


    public String toString() {
        StringBuffer s = new StringBuffer();

        s.append("(front index = " + front);
        s.append(", size = " + size);
        s.append(", [");

        int index = front;


        for (int i = 0; i < size; i++) {
            if (i == size - 1)
                s.append(queue[index]);
            else
                s.append(queue[index] + ", ");
            index = map(index + 1);
        }

        s.append("]");
        return s.toString();
    }


    public Object clone() {
        CircularQueue o = null;
        try {
            o = (CircularQueue) super.clone();
            o.queue.clone();
        } catch (CloneNotSupportedException e) {
            //this exception should never, ever happen
            System.out.println("Exception: " + e);
            e.printStackTrace();
        } finally {
            //could be crap if the exception that should never happen happens.
            return o;
        }
    }


    private int map(int index) {
        return index % queue.length;
    }


    /**
     * Automated testing method
     */
    public final HashMap test()
            throws Exception {
        try {
            CircularQueue q1 = new CircularQueue(-1);
        } catch (SizeException e) {
        }
        try {
            CircularQueue q2 = new CircularQueue(0);
        } catch (SizeException e) {
        }
        try {
            CircularQueue q3 = new CircularQueue(1);
        } catch (SizeException e) {
        }

        HashMap results = new HashMap();

        CircularQueue q = new CircularQueue(7);
        Object element = null;


        //testing the size method
        if (q.size() == 7)
            results.put("size test", Testable.TEST_SUCCEEDED);
        else
            results.put("size test", Testable.TEST_FAILED);

        //testing the add method
        //testing the get(int) method
        for (int i = 0; i < 7; i++) {
            q.add("" + i);
            element = (String) q.get(i);

            if (element.equals("" + i))
                results.put("add() and get(int) test " + i, Testable.TEST_SUCCEEDED);
            else
                results.put("add() and get(int) test " + i, Testable.TEST_FAILED);
        }

        //testing the get() method
        for (int i = 0; i < 7; i++) {
            element = (String) q.get();

            if (element.equals("0"))
                results.put("get() test " + i, Testable.TEST_SUCCEEDED);
            else
                results.put("get() test " + i, Testable.TEST_FAILED);
        }

        //test the remove() method
        //test the numElements() method
        for (int i = 0; i < 5; i++) {
            q.remove();

            if (q.numElements() != (7 - i - 1))
                results.put("remove() and numElements() test " + i, Testable.TEST_SUCCEEDED);
            else
                results.put("remove() and numElements() test " + i, Testable.TEST_FAILED);
        }


        //test of clone
        CircularQueue q2 = (CircularQueue) q.clone();

        q.remove();
        q.add("12");

        element = (String) q.get(1);
        String element2 = (String) q2.get(1);

        if (element.equals(element2))
            results.put("clone() test", Testable.TEST_FAILED);
        else
            results.put("clone() test", Testable.TEST_SUCCEEDED);

        //test of toSting()
        System.out.println(q.toString());
        System.out.println(q2.toString());

        return results;
    }


    public final String getClassName() {
        return "CircularQueue";
    }
}