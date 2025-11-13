package ca.pringle.maze.logic;

import java.util.Random;

public final class DisjointSetAlgorithm implements MazeAlgorithm {

    @Override
    public SpecializedGraph generateMaze(final MazeConfig mazeConfig) {

        final DisjointEdgeSet disjointSet = new DisjointEdgeSet(mazeConfig.getRowsTimesColumns());

        // create edges O(ROWS*COLS)
        final Edge[] edges = initializeMaze(mazeConfig);

        // randomize edges O(ROWS*COLS)
        randomizeEdges(mazeConfig.getNewSeededRandom(), edges);

        // join nodes via edges O(ROWS*COLS*k), k is some constant <= 5
        for (final Edge edge : edges) {
            disjointSet.merge(edge.node1, edge.node2, edge);
        }

        return disjointSet.getEdgesAsDag();
    }

    /**
     * creates a list of all possible paths between nodes of length 1
     */
    Edge[] initializeMaze(final MazeConfig mazeConfig) {

        final int size = 2 * mazeConfig.getRowsTimesColumns() - mazeConfig.getRows() - mazeConfig.getColumns();
        final Edge[] edges = new Edge[size];

        int count = 0;
        for (int i = 0; i < mazeConfig.getRowsTimesColumns(); i++) {
            for (final int adjacentPath : getAdjacent(i, mazeConfig.getRows(), mazeConfig.getColumns())) {
                edges[count] = new Edge(i, adjacentPath);
                count++;
            }
        }

        return edges;
    }

    void randomizeEdges(final Random random, final Edge[] edges) {

        // https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle
        for (int i = edges.length - 1; i >= 0; i--) {

            final int randomIndex = random.nextInt(i + 1);

            final Edge temp = edges[i];
            edges[i] = edges[randomIndex];
            edges[randomIndex] = temp;
        }
    }

    /* A maze can never have diagonal paths.
     *
     *         |   |
     *      ---+---+---
     *         | N | x
     *      ---+---+---
     *         | x |
     *
     * N corresponds to node in this method. The x's represent
     * possible paths from N.
     */
    int[] getAdjacent(final int node, final int rows, final int columns) {

        final boolean isNodeBelow = node < (rows - 1) * columns;
        final boolean isNodeToTheRight = (node + 1) % columns != 0;

        return isNodeBelow && isNodeToTheRight ? new int[]{node + columns, node + 1} :
                isNodeBelow ? new int[]{node + columns} :
                        isNodeToTheRight ? new int[]{node + 1} :
                                new int[]{};
    }
}
