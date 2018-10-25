/**
 * Disjoint set implementation
 * Author: Micha Pringle
 * Start:  3/21/1 10:06PM
 * Seems to have turned out well in the end.
 */

package ca.pringle.random.nlgn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


public final class DisjointSet {
    private HashMap set = null;


    public DisjointSet(int size) {
        set = new HashMap(size);
    }


    /**
     * adds a new set to the set of disjoint sets
     */
    public void add(Representative key, Object o) {
        List l = new List(key, o);
        set.put("" + key.representative, l);
    }


    /**
     * returns a collection view of the values contained in this map
     * Should only used when the size of the disjoint set is 1
     */
    public Set values() {
        ArrayList al = new ArrayList(set.values());
        List l = (List) al.get(0);
        return l.values();
    }


    /**
     * merge one disjoint set with another, adds the object to the merged sets
     * if the sets are not disjoint, no merge occurs
     */
    public void merge(Representative key1, Representative key2, Object o) {
        if (key1.representative == key2.representative) return;

        //gets the actual hash value stored in the hash table
        List l = (List) set.get("" + key1.representative);
        List l2 = (List) set.get("" + key2.representative);

        //smaller list is l, so we shorten work by merging l into l2
        if (l.size() < l2.size()) {
            set.remove("" + key1.representative);
            l2.merge(l);
            l2.add(o);
        } else {
            set.remove("" + key2.representative);
            l.merge(l2);
            l.add(o);
        }
    }


    /**
     * return the number of disjoint sets
     */
    public int size() {
        return set.size();
    }


    public String toString() {
        return "{|S|=" + set.size() + ", " + set.toString() + "}";
    }


    public final class List {

        private boolean empty;

        private Node head = null;
        private Node tail = null;
        private Representative representative = null;
        private int size;


        List(Representative rep, Object data) {
            head = new Node(rep, data);
            tail = head;
            representative = rep;
            size = 1;
        }


        /**
         * add data to the end of the list.
         */
        public void add(Object data) {
            //add data to end of list and keep track of end of list
            tail.add(representative, data);
            tail = tail.next();
            size = size + 1;
        }


        /**
         * appends the given list onto the end of the current list.
         * values are not copied in order to maintain pointers into the list.
         */
        public void merge(List l) {
            //adjust the value contained in the representative WITHOUT changing the
            //representative pointer
            Node walker = l.head;

            while (walker != null) {
                walker.representative(representative);
                walker = walker.next();
            }

            //super fast merge does not copy the data, just appends one list to another
            tail.add(l.head);
            tail = l.tail;
            size = size + l.size;
        }


        /**
         * get the size of the list
         */
        public int size() {
            return size;
        }


        /**
         * return all the values contained in the list as a Set
         */
        public Set values() {
            ArrayList al = new ArrayList();
            Node walker = head;

            while (walker != null) {
                Object o = (Object) walker.get();
                al.add(o);
                walker = walker.next();
            }
            HashSet hs = new HashSet(al);
            return hs;
        }


        public String toString() {
            String s = new String("");
            Node walker = head;
            boolean first = true;

            while (walker != null) {
                if (first) {
                    s = s + walker.toString() + "}";
                    first = false;
                } else
                    s = walker.toString() + "," + s;
                walker = walker.next();
            }
            return ("[|S|=" + size + ", " + representative + "={" + s + "]");
        }


        private final class Node {
            private Representative representative = null;
            private Object data = null;
            private Node next = null;


            public Node(Representative r, Object d) {
                representative = r;
                data = d;
            }

            public void add(Representative r, Object d) {
                next = new Node(r, d);
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

            public void representative(Representative rep) {
                representative.representative = rep.representative;
            }

            public String toString() {
                return "[" + representative.toString() + ", " + data.toString() + "]";
            }
        } //End of class node

    } //End of class List


}	
