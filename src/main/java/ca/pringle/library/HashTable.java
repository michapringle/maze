package ca.pringle.library;


/**
 * Simple hash table. No dynamic resizing.
 */

public final class HashTable {


    private Object[] table = null;

    /**
     * creates a hash table of size specified.
     */
    public HashTable(int size) {
        table = new Object[size];
    }


    /**
     * stores the object using the given key. Returns true if the key is
     * a new key. Returns false if the key is already in the table.
     */
    public boolean put(Object key, Object o) {
        int hash = getHash(key);
        LinkedList ll;
        Data d;
        boolean inTable = false;


        if (table[hash] == null) {
            ll = new LinkedList();
            ll.insert(new Data(key, o));
            table[hash] = ll;
        } else {
            ll = (LinkedList) table[hash];

            ll.iter();
            while (ll.hasNext() && !inTable) {
                d = (Data) ll.next();
                if (d.key.equals(key)) inTable = true;
            }

            if (!inTable) ll.insert(new Data(key, o));
        }
        return !inTable;
    }


    /**
     * retrieves the object associated with key. If none, returns null.
     */
    public Object get(Object key) {
        LinkedList ll = null;
        Data d = null;
        int hash = getHash(key);

        ll = (LinkedList) table[hash];
        if (ll == null) return null;

        ll.iter();
        while (ll.hasNext()) {
            d = (Data) ll.next();
            if (d.key.equals(key)) return d.o;
        }

        return null;
    }


    /**
     * removes the object associated with the key. If no removal is done, returns false.
     */
    public boolean remove(Object key) {
        LinkedList ll = null;
        Data d = null;
        int hash = getHash(key);

        ll = (LinkedList) table[hash];
        if (ll == null) return false;

        ll.iter();
        while (ll.hasNext()) {
            d = (Data) ll.next();
            if (d.key.equals(key)) {
                ll.delete();
                return true;
            }
        }

        return false;
    }


    private int getHash(Object o) {
        int i = (o.hashCode() % table.length);
        if (i < 0) return -i;
        return i;
    }


    public String toString() {
        StringBuffer s = new StringBuffer();
        LinkedList ll = null;

        for (int i = 0; i < table.length; i++) {
            ll = (LinkedList) table[i];
            if (ll != null) {
                s.append(", ");
                s.append(ll);
            }
        }

        return s.toString();
    }


    private final class Data {
        public final Object key;
        public final Object o;

        public Data(Object keyParam, Object obj) {
            key = keyParam;
            o = obj;
        }

        public String toString() {
            StringBuffer s = new StringBuffer();

            s.append("[");
            s.append(key);
            s.append(", ");
            s.append(o);
            s.append("]");

            return s.toString();
        }
    }    //End of class Data

}    //End of class HashTable