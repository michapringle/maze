package ca.pringle.maze.logic;

import java.util.ArrayList;
import java.util.List;

import static ca.pringle.maze.util.Checks.check;

public final class DisjointEdgeSet {

    private final Subset[] subsets;
    private final int initialNumSets;
    private int numSets;

    public DisjointEdgeSet(final int numSets) {

        this.initialNumSets = check(numSets).isGreaterThan(0, "size > 0");
        this.numSets = numSets;

        subsets = new Subset[numSets];

        for (int i = 0; i < numSets; i++) {
            subsets[i] = new Subset(i);
        }
    }

    public Subset[] getSubsets() {
        return subsets;
    }

    public SpecializedGraph getEdgesAsDag() {

        final SpecializedGraph graph = new SpecializedGraph(initialNumSets);

        for (final Subset subset : getSubsets()) {
            for (final Edge edge : subset.getValues()) {
                graph.put(edge.node1, edge.node2);
                graph.put(edge.node2, edge.node1);
            }
        }

        return graph;
    }

    public void merge(final int key1,
                      final int key2,
                      final Edge value) {

        final int keyA = pathCompress(subsets[key1]);
        final int keyB = pathCompress(subsets[key2]);

        if (keyA != keyB) {
            subsets[keyA].values.add(value);
            subsets[keyB].pointer = keyA;
            numSets = numSets - 1;
        }
    }

    public int getNumSets() {
        return numSets;
    }

    // O(c) work - not really, defined by Ackerman's function
    private int pathCompress(final Subset subset) {

        int tempPointer = subset.pointer;

        while (tempPointer != subsets[tempPointer].pointer) {
            int temp = tempPointer;
            tempPointer = subsets[tempPointer].pointer;
            subsets[temp].pointer = subsets[tempPointer].pointer;
        }

        return tempPointer;
    }

    public static class Subset {

        private final List<Edge> values;
        private int pointer;

        public Subset(final int pointer,
                      final List<Edge> values) {

            this.pointer = pointer;
            this.values = values;
        }

        public Subset(int pointer) {

            this(pointer, new ArrayList<>());
        }

        public List<Edge> getValues() {
            return values;
        }
    }
}
