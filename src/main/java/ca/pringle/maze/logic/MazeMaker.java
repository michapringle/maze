package ca.pringle.maze.logic;

import java.util.Random;

import static ca.pringle.maze.util.Checks.check;

/**
 * Creates a maze using a graph that is rows x columns in size. Each
 * node is given a number in ascending order, starting from the top
 * left corner, working down to the bottom right corner.
 * <p>
 * <pre>
 *        c   c   c
 *        o   o   o
 *        l   l   l
 * row  | 0 | 1 | 2 |
 *      |---+---+---|
 * row  | 3 | 4 | 5 |
 *      |---+---+---|
 * row  | 6 | 7 | 8 |
 * </pre>
 */
public final class MazeMaker {

    private final MazeConfig mazeConfig;
    private final DisjointEdgeSet disjointSet;

    public MazeMaker(final MazeConfig mazeConfig,
                     final DisjointEdgeSet disjointSet) {

        this.mazeConfig = check(mazeConfig).isNotNull();
        this.disjointSet = check(disjointSet).isNotNull();
    }

    public MazeMaker(final MazeConfig mazeConfig) {

        this(mazeConfig, new DisjointEdgeSet(mazeConfig.getRowsTimesColumns()));
    }

    public SpecializedGraph generateDag() {

        mazeConfig.getMazeGenerationTimer().start();

        // create edges O(ROWS*COLS)
        final Edge[] edges = initializeMaze();

        // randomize edges O(ROWS*COLS)
        randomizeEdges(edges);

        // join nodes via edges O(ROWS*COLS*k), k is some constant <= 5
        for (final Edge edge : edges) {
            disjointSet.merge(edge.node1, edge.node2, edge);
        }

        final SpecializedGraph result = disjointSet.getEdgesAsDag();

        mazeConfig.getMazeGenerationTimer().stop();

        return result;
    }

    /**
     * creates a list of all possible paths between nodes of length 1
     */
    Edge[] initializeMaze() {

        final int size = 2 * mazeConfig.getRowsTimesColumns() - mazeConfig.getRows() - mazeConfig.getColumns();
        final Edge[] edges = new Edge[size];

        int count = 0;
        for (int i = 0; i < mazeConfig.getRowsTimesColumns(); i++) {
            for (final int adjacentPath : getAdjacent(i)) {
                edges[count] = new Edge(i, adjacentPath);
                count++;
            }
        }

        return edges;
    }

    void randomizeEdges(final Edge[] edges) {

        final Random random = mazeConfig.getNewSeededRandom();

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
    int[] getAdjacent(final int node) {

        final boolean isNodeBelow = node < (mazeConfig.getRows() - 1) * mazeConfig.getColumns();
        final boolean isNodeToTheRight = (node + 1) % mazeConfig.getColumns() != 0;

        return isNodeBelow && isNodeToTheRight ? new int[] { node + mazeConfig.getColumns(), node + 1 } :
               isNodeBelow ? new int[] { node + mazeConfig.getColumns() } :
               isNodeToTheRight ? new int[] { node + 1 } :
               new int[] {};
    }
}
