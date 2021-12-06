package ca.pringle.maze.logic;

import java.util.Objects;

import static ca.pringle.maze.util.Checks.check;

/**
 * Represents an undirected edge between two nodes on a graph
 */
public final class Edge {
    public final int node1;
    public final int node2;

    public Edge(final int node1,
                final int node2) {

        this.node1 = check(node1).isGreaterThan(-1, "Node 1 must >= 0");
        this.node2 = check(node2).isGreaterThan(-1, "Node 2 must >= 0");
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
