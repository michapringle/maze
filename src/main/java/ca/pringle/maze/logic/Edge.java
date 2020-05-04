package ca.pringle.maze.logic;

import ca.pringle.maze.Preconditions;

import java.util.Objects;

/**
 * Represents an undirected edge between two nodes on a graph
 */
public final class Edge {
    public final int node1;
    public final int node2;

    public Edge(final int node1,
                final int node2) {
        this.node1 = node1;
        this.node2 = node2;

        Preconditions.checkArgument(node1 >= 0, "Node 1 must >= 0");
        Preconditions.checkArgument(node2 >= 0, "Node 2 must >= 0");
    }

    /**
     * @return the edge with the nodes reversed. Useful if you want to
     * make a DAG, then edge and edge.reverseNodes can be used to
     * represent the unidirected edge.
     */
    public Edge reverseNodes() {
        return new Edge(node2, node1);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Edge) {

            final Edge edge = (Edge) o;
            return node1 == edge.node1 &&
                    node2 == edge.node2;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(node1, node2);
    }

    @Override
    public String toString() {
        return String.format("(%s<->%s)", node1, node2);
    }
}
