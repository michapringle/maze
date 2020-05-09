package ca.pringle.maze.logic;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

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
    public Pair<Integer, Integer> findLongestPath(final Set<Edge> undirectedMazeEdges,
                                                  final MazeConfig mazeConfig) {

        mazeConfig.getPathGenerationTimer().start();

        // each call below takes O(n) time
        final ListBackedMap<Integer, Edge> dag1 = convertToDirectedGraph(undirectedMazeEdges);
        final Pair<Integer, Integer> firstNode = longestPath(0, dag1);
        final ListBackedMap<Integer, Edge> dag2 = convertToDirectedGraph(undirectedMazeEdges);
        final Pair<Integer, Integer> secondNode = longestPath(firstNode.right, dag2);

        mazeConfig.getPathGenerationTimer().stop();

        return new Pair<>(firstNode.right, secondNode.right);
    }

    private ListBackedMap<Integer, Edge> convertToDirectedGraph(final Set<Edge> undirectedMazeEdges) {
        final ListBackedMap<Integer, Edge> nodeLookup = new ListBackedMap<>();

        undirectedMazeEdges.forEach(edge -> {
            nodeLookup.put(edge.node1, edge);
            nodeLookup.put(edge.node2, edge.reverseNodes());
        });

        return nodeLookup;
    }

    /*
     * this is much more easily understood as a recursive algorithm, but that requires
     * setting the stack size for the jvm, and that has to be passed to the jvm when you
     * run the jar. I want to avoid having to pass settings to the jvm to make this
     * program work, hence this iterative approach.
     */
    private Pair<Integer, Integer> longestPath(final int node,
                                               final ListBackedMap<Integer, Edge> nodeLookup) {

        final Stack<Pair<Integer, Integer>> stack = new Stack<>();
        Pair<Integer, Integer> maxPair = new Pair<>(0, node);

        stack.push(maxPair);

        while (!stack.empty()) {
            final Pair<Integer, Integer> topPair = stack.pop();

            final List<Edge> followNodeEdges = nodeLookup.get(topPair.right);

            // end condition, choose the path that is longest
            if (followNodeEdges.isEmpty()) {
                maxPair = topPair.left >= maxPair.left ? topPair : maxPair;
            }

            // follow each edge
            for (final Edge edge : new LinkedList<>(followNodeEdges)) {
                // remove visited nodes, both to and from paths
                nodeLookup.remove(node, edge);
                nodeLookup.remove(edge.node2, edge.reverseNodes());
                stack.push(new Pair<>(topPair.left + 1, edge.node2));
            }
        }

        return maxPair;
    }
}
