package ca.pringle.maze.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Disjoint set implementation
 * Author: Micha Pringle
 * Start:  10/24/01 3:45PM
 */
public final class DisjointSet<T> {
    private final List<Subset<T>> subsets;
    private int numSets;

    //O(n) work
    public DisjointSet(final int numSets) {
        this.numSets = numSets;
        Preconditions.checkArgument(numSets > 0, "size > 0");

        subsets = new ArrayList<>();

        for (int i = 0; i < numSets; i++) {
            subsets.add(new Subset<>(i));
        }
    }

    // O(n) work
    public Set<T> getCombinedSubsets() {
        final List<T> results = new ArrayList<>();

        for (final Subset<T> subset : subsets) {
            results.addAll(subset.values);
        }

        return new HashSet<>(results);
    }

    // O(pathCompression) work
    public void merge(final int key1,
                      final int key2,
                      T value) {

        final int keyA = pathCompress(subsets.get(key1));
        final int keyB = pathCompress(subsets.get(key2));

        if (keyA != keyB) {
            subsets.get(keyA).values.add(value);
            subsets.get(keyB).pointer = keyA;
            numSets = numSets - 1;
        }
    }

    public int getNumSets() {
        return numSets;
    }

    // O(c) work - not really, defined by Ackerman's function
    private int pathCompress(final Subset<T> subset) {
        int tempPointer = subset.pointer;

        while (tempPointer != subsets.get(tempPointer).pointer) {
            int temp = tempPointer;
            tempPointer = subsets.get(tempPointer).pointer;
            subsets.get(temp).pointer = subsets.get(tempPointer).pointer;
        }

        return tempPointer;
    }

    private static class Subset<T> {
        private final List<T> values;
        private int pointer;

        public Subset(final int pointer,
                      final List<T> values) {
            this.pointer = pointer;
            this.values = values;
        }

        public Subset(int pointer) {
            this(pointer, new ArrayList<>());
        }
    }
}
