/**
 * Disjoint set implementation
 * Author: Micha Pringle
 * Start:  10/24/01 3:45PM
 */
package ca.pringle.random.nlglgn;


import java.util.ArrayList;

public final class DisjointSet {
    Cell[] disjointSet = null;
    int numSets;


    //O(n2) work
    public DisjointSet(int size) {
        disjointSet = new Cell[size];

        for (int i = 0; i < size; i++) {
            disjointSet[i] = new Cell();
            disjointSet[i].pointer = i;
        }
        numSets = size;
    }


    // O(n2) work
    public ArrayList values() {
        ArrayList al = new ArrayList();

        for (int i = 0; i < disjointSet.length; i++)
            if (disjointSet[i].edgeSet != null) al.addAll(disjointSet[i].edgeSet.values());

        return al;
    }

    // debugging method
    public void cellValues() {
        for (int i = 0; i < disjointSet.length; i++)
            System.out.println(disjointSet[i]);
    }


    // O(pathCompression) work
    public void merge(int key1, int key2, Object o) {
        key1 = pathCompress(disjointSet[key1]);
        key2 = pathCompress(disjointSet[key2]);

        if (key1 != key2) {
            if (disjointSet[key1].edgeSet == null)
                disjointSet[key1].edgeSet = new List(o);
            else
                disjointSet[key1].edgeSet.add(o);

            disjointSet[key2].pointer = key1;

            numSets = numSets - 1;
        }
    }


    // return the number of disjoint sets
    public int size() {
        return numSets;
    }


    public String toString() {
        String s = new String();

        for (int i = 0; i < disjointSet.length; i++)
            s = s + "Set " + i + ", " + disjointSet[i].toString() + "\n";

        return s;
    }


    // O(c) work - not really, defined by Ackerman's function
    private int pathCompress(Cell c) {
        int tempPointer = c.pointer;
        int temp = tempPointer;

        while (tempPointer != disjointSet[tempPointer].pointer) {
            temp = tempPointer;
            tempPointer = disjointSet[tempPointer].pointer;
            disjointSet[temp].pointer = disjointSet[tempPointer].pointer;
        }

        return tempPointer;
    }


    private final class Cell {
        public int pointer;
        public List edgeSet = null;


        public String toString() {
            if (edgeSet == null)
                return ("->" + pointer + ", null");
            else
                return ("->" + pointer + ", " + edgeSet.toString());
        }

    } //End of class Cell


    private final class List {
        private Node head = null;
        private Node tail = null;
        private int size;


        List(Object data) {
            head = new Node(data);
            tail = head;
            size = 1;
        }


        /**
         * add data to the end of the list.
         */
        public void add(Object data) {
            tail.add(data);
            tail = tail.next();
            size = size + 1;
        }


        /**
         * get the size of the list
         */
        public int size() {
            return size;
        }


        /**
         * return all the values contained in the list as an arrayList
         */
        public ArrayList values() {
            ArrayList al = new ArrayList();
            Node walker = head;

            while (walker != null) {
                Point p = (Point) walker.get();
                al.add(p);
                walker = walker.next();
            }

            return al;
        }


        public String toString() {
            String s = new String("");
            Node walker = head;
            boolean first = true;

            while (walker != null) {
                if (first) {
                    s = s + walker.toString();
                    first = false;
                } else
                    s = walker.toString() + "," + s;
                walker = walker.next();
            }
            return ("[|S|=" + size + ", {" + s + "}]");
        }


        private final class Node {
            private Object data = null;
            private Node next = null;


            public Node(Object d) {
                data = d;
            }

            public void add(Object d) {
                next = new Node(d);
            }

            public void add(Node n) {
                next = n;
            }

            public Node next() {
                return next;
            }

            public Object get() {
                return data;
            }


            public String toString() {
                return data.toString();
            }

        } //End of class Node

    } //End of class List

} //End of class DisjointSet
