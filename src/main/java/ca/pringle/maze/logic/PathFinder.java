package ca.pringle.maze.logic;

import java.util.ArrayDeque;
import java.util.Deque;

public final class PathFinder {

    public int[] findSolution(final SpecializedGraph graph,
                              final int startNode,
                              final int endNode) {


        final SpecializedGraph graphCopy = SpecializedGraph.copy(graph);
        final Deque<Integer> stack = new ArrayDeque<>();

        stack.push(startNode);
        while (true) {
            final int node = stack.peek();

            final SpecializedGraph.SpecializedList followNodeEdges = graphCopy.get(node);

            // backtrack condition
            if (followNodeEdges.isEmpty()) {
                stack.pop();
                continue;
            }

            // end condition
            if (followNodeEdges.contains(endNode)) {
                stack.push(endNode);
                return stack.stream().mapToInt(i -> i).toArray();
            }

            // follow next edge
            final int toNode = followNodeEdges.get(0);
            graphCopy.remove(node, toNode);
            graphCopy.remove(toNode, node);
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
    public Path findLongestPath(final SpecializedGraph graph) {

        // each call below takes O(n) time
        final int firstNode = longestPath(graph, 0);
        final int secondNode = longestPath(graph, firstNode);

        return new Path(firstNode, secondNode);
    }

    /*
     * this is much more easily understood as a recursive algorithm, but that requires
     * setting the stack size for the jvm, and that has to be passed to the jvm when you
     * run the jar. I want to avoid having to pass settings to the jvm to make this
     * program work, hence this iterative approach.
     */
    private int longestPath(final SpecializedGraph graph,
                            final int startNode) {

        final SpecializedGraph graphCopy = SpecializedGraph.copy(graph);
        final Deque<Path> stack = new ArrayDeque<>();
        Path maxPair = new Path(0, startNode);

        stack.push(maxPair);

        while (!stack.isEmpty()) {
            final Path topPair = stack.pop();

            final SpecializedGraph.SpecializedList followNodeEdges = graphCopy.get(topPair.toNode);

            // end condition, choose the path that is longest
            if (followNodeEdges.isEmpty()) {
                maxPair = topPair.fromNode >= maxPair.fromNode ? topPair : maxPair;
            }

            // follow each edge
            for (final int toNode : followNodeEdges.toArray()) {
                graphCopy.remove(topPair.toNode, toNode);
                graphCopy.remove(toNode, topPair.toNode);
                stack.push(new Path(topPair.fromNode + 1, toNode));
            }
        }

        return maxPair.toNode;
    }
}
