package ca.pringle.maze.logic;

import ca.pringle.maze.util.ListBackedMap;
import ca.pringle.maze.util.Pair;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

public final class PathFinder {

    public List<Integer> findSolution(final List<Edge> undirectedMazeEdges,
                                      final Integer startNode,
                                      final Integer endNode,
                                      final MazeConfig mazeConfig) {

        mazeConfig.getSolutionGenerationTimer().start();

        final ListBackedMap<Integer, Integer> nodeLookup = convertToDirectedGraph(undirectedMazeEdges);
        final Deque<Integer> stack = new ArrayDeque<>();

        stack.push(startNode);
        while (true) {
            final Integer node = stack.peek();

            final List<Integer> followNodeEdges = nodeLookup.get(node);

            // end condition
            if (followNodeEdges.contains(endNode)) {
                stack.push(endNode);
                final List<Integer> solution = Arrays.asList(stack.toArray(new Integer[0]));
                mazeConfig.getSolutionGenerationTimer().stop();
                return solution;
            }

            // backtrack condition
            if (followNodeEdges.isEmpty()) {
                stack.pop();
                continue;
            }

            // follow next edge
            final Integer toNode = followNodeEdges.get(0);
            nodeLookup.remove(node, toNode);
            nodeLookup.remove(toNode, node);
            stack.push(toNode);
        }
    }

    /**
     * Finds the longest path by
     * 1. Convert the edges to a directed acyclic graph
     * 2. Find the longest path from a random point
     * 3. Find the longest path from the point in step 2.
     * <p>
     * Runs in O(n) time
     */
    public Pair<Integer, Integer> findLongestPath(final List<Edge> undirectedMazeEdges,
                                                  final MazeConfig mazeConfig) {

        mazeConfig.getPathGenerationTimer().start();

        // each call below takes O(n) time
        final ListBackedMap<Integer, Integer> dag1 = convertToDirectedGraph(undirectedMazeEdges);
        final Integer firstNode = longestPath(0, dag1);
        final ListBackedMap<Integer, Integer> dag2 = convertToDirectedGraph(undirectedMazeEdges);
        final Integer secondNode = longestPath(firstNode, dag2);

        mazeConfig.getPathGenerationTimer().stop();

        return new Pair<>(firstNode, secondNode);
    }

    private ListBackedMap<Integer, Integer> convertToDirectedGraph(final List<Edge> undirectedMazeEdges) {

        final ListBackedMap<Integer, Integer> nodeLookup = new ListBackedMap<>();

        for (final Edge edge : undirectedMazeEdges) {
            nodeLookup.put(edge.node1, edge.node2);
            nodeLookup.put(edge.node2, edge.node1);
        }

        return nodeLookup;
    }

    /*
     * this is much more easily understood as a recursive algorithm, but that requires
     * setting the stack size for the jvm, and that has to be passed to the jvm when you
     * run the jar. I want to avoid having to pass settings to the jvm to make this
     * program work, hence this iterative approach.
     */
    private Integer longestPath(final int startNode,
                                final ListBackedMap<Integer, Integer> nodeLookup) {

        final Deque<Pair<Integer, Integer>> stack = new ArrayDeque<>();
        Pair<Integer, Integer> maxPair = new Pair<>(0, startNode);

        stack.push(maxPair);

        while (!stack.isEmpty()) {
            final Pair<Integer, Integer> topPair = stack.pop();

            final List<Integer> followNodeEdges = nodeLookup.get(topPair.right);

            // end condition, choose the path that is longest
            if (followNodeEdges.isEmpty()) {
                maxPair = topPair.left >= maxPair.left ? topPair : maxPair;
            }

            // follow each edge
            for (final Integer toNode : followNodeEdges.toArray(new Integer[0])) {
                nodeLookup.remove(topPair.right, toNode);
                nodeLookup.remove(toNode, topPair.right);
                stack.push(new Pair<>(topPair.left + 1, toNode));
            }
        }

        return maxPair.right;
    }
}
