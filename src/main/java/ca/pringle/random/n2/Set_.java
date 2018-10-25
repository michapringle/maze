package ca.pringle.random.n2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Set_ {
    private HashSet elements = null;
    private ArrayList edges = null;


    Set_(Object element) {
        elements = new HashSet();
        elements.add(element);
        edges = new ArrayList();
    }


    public void merge(Set_ s) {
        Iterator i = s.elements.iterator();
        while (i.hasNext())
            elements.add(i.next());
        edges.addAll(s.edges);
    }


    public int size() {
        return edges.size();
    }


    public Object getElement(int i) {
        return edges.get(i);
    }


    public boolean containsEl(Object o) {
        return elements.contains(o);
    }


    public Set getAll() {
        HashSet s = new HashSet();

        for (int i = 0; i < edges.size(); i++)
            s.add(edges.get(i));

        return s;
    }


    public void addEdge(Object o) {
        edges.add(o);
    }

    public String toString() {
        return elements.toString() + " = " + edges.toString() + "\n";
    }
}
