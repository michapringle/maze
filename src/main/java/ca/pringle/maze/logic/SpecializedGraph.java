package ca.pringle.maze.logic;

import java.util.Arrays;
import java.util.Objects;

import static ca.pringle.maze.util.Checks.check;

public final class SpecializedGraph {

    private final SpecializedList[] nodes;

    private SpecializedGraph(final SpecializedList[] nodes) {

        this.nodes = check(nodes).isNotNull();
    }

    public SpecializedGraph(final int maxNodes) {

        this(new SpecializedList[maxNodes]);
    }

    public static SpecializedGraph copy(final SpecializedGraph graph) {

        final SpecializedGraph copy = new SpecializedGraph(graph.numberOfNodes());

        for (int fromNode = 0; fromNode < graph.numberOfNodes(); fromNode++) {
            copy.put(fromNode, graph.get(fromNode).toArray());
        }

        return copy;
    }

    public void put(final int fromNode,
                    final int... toNodes) {

        for (final int toNode : toNodes) {
            put(fromNode, toNode);
        }
    }

    private void put(final int fromNode,
                     final int toNode) {

        if (nodes[fromNode] == null) {
            nodes[fromNode] = new SpecializedList();
        }

        nodes[fromNode].append(toNode);
    }

    public SpecializedList get(final int node) {

        return nodes[node];
    }

    public int numberOfNodes() {

        return nodes.length;
    }

    public void remove(final int fromNode,
                       final int toNode) {

        nodes[fromNode].removeValue(toNode);
    }

    @Override
    public boolean equals(final Object o) {

        if (o instanceof SpecializedGraph) {
            final SpecializedGraph that = (SpecializedGraph) o;

            if (this.numberOfNodes() != that.numberOfNodes()) {
                return false;
            }

            for (int i = 0; i < numberOfNodes(); i++) {
                if (!Objects.equals(get(i), that.get(i))) {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(nodes);
    }

    @Override
    public String toString() {

        return Arrays.asList(nodes).toString();
    }

    public static class SpecializedList {

        private int[] list;
        private int length;

        public SpecializedList() {
            this.list = new int[1];
            this.length = 0;
        }

        public void append(int value) {

            if (length == list.length) {
                list = Arrays.copyOf(list, 4);
            }

            list[length] = value;
            length++;
        }

        public int get(int index) {
            return list[index];
        }

        public int size() {
            return length;
        }

        public boolean isEmpty() {
            return length <= 0;
        }

        public boolean contains(final int value) {

            for (int i = 0; i < Math.min(list.length, length); i++) {
                if (list[i] == value) {
                    return true;
                }
            }
            return false;
        }

        public int[] toArray() {
            return Arrays.copyOf(list, length);
        }

        public void removeValue(int value) {

            boolean found = false;
            for (int i = 0; i < length; i++) {
                if (list[i] == value) {
                    found = true;
                }
                if (found && i != length - 1) {
                    list[i] = list[i + 1];
                }
            }
            if (found) {
                length--;
            }
        }

        @Override
        public final boolean equals(final Object o) {

            if (o instanceof SpecializedList) {
                final SpecializedList that = (SpecializedList) o;
                return Arrays.equals(this.toArray(), that.toArray());
            }

            return false;
        }

        @Override
        public final int hashCode() {

            return Arrays.hashCode(toArray());
        }

        @Override
        public String toString() {

            return length + ":" + Arrays.toString(list);
        }
    }
}
