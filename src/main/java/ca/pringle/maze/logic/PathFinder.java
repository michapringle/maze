package ca.pringle.maze.logic;

import java.time.Instant;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import ca.pringle.maze.util.ListBackedMap;
import ca.pringle.maze.util.Pair;

public final class PathFinder {

    /**
     * Finds the longest path by
     * 1. Convert the edges to a directed acyclic graph
     * 2. Find the longest path from a random point
     * 3. Find the longest path from the point in step 2.
     * <p>
     * Runs in O(n) time
     */
    public Pair<Integer, Integer> findLongestPath(final Set<Edge> undirectedMazeEdges) {

        final long start = Instant.now().toEpochMilli();

        // O(n)
        final ListBackedMap<Integer, Edge> dag1 = convertToDirectedGraph(undirectedMazeEdges);
        // O(n)
        final Pair<Integer, Edge> firstNode = longestPath(1, dag1.get(0).get(0), dag1);
        // O(n)
        final ListBackedMap<Integer, Edge> dag2 = convertToDirectedGraph(undirectedMazeEdges);
        // O(n)
        final Pair<Integer, Edge> secondNode = longestPath(1, firstNode.right.reverseNodes(), dag2);

        final long end = Instant.now().toEpochMilli() - start;
        System.out.println("Longest path found in " + end + " milliseconds.");

        return new Pair<>(firstNode.right.node2, secondNode.right.node2);
    }

    private ListBackedMap<Integer, Edge> convertToDirectedGraph(final Set<Edge> undirectedMazeEdges) {
        final ListBackedMap<Integer, Edge> nodeLookup = new ListBackedMap<>();

        undirectedMazeEdges.forEach(edge -> {
            nodeLookup.put(edge.node1, edge);
            nodeLookup.put(edge.node2, edge.reverseNodes());
        });

        return nodeLookup;
    }

    private Pair<Integer, Edge> longestPath(final int length,
                                            final Edge edge,
                                            final ListBackedMap<Integer, Edge> nodeLookup) {

        // follow both nodes for the edge
        final List<Edge> followNodeEdges = nodeLookup.get(edge.node2);

        // remove the edge that was followed so it is not followed again
        nodeLookup.remove(edge.node1, edge);
        nodeLookup.remove(edge.node2, edge.reverseNodes());

        if (followNodeEdges.isEmpty()) {
            return new Pair<>(length, edge);
        }

        return new LinkedList<>(followNodeEdges)
                .stream()
                .map(e -> longestPath(length + 1, e, nodeLookup))
                .max(Comparator.comparing(p -> p.left))
                .orElseThrow();
    }
}
