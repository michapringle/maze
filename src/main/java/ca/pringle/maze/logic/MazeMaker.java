package ca.pringle.maze.logic;

import ca.pringle.maze.util.DisjointSet;

import java.util.List;
import java.util.Random;

import static ca.pringle.maze.util.Preconditions.check;

/**
 * Creates a maze using a graph that is rows x columns in size. Each
 * node is given a number in ascending order, starting from the top
 * left corner, working down to the bottom right corner.
 * <p>
 *        c   c   c
 *        o   o   o
 *        l   l   l
 * row  | 0 | 1 | 2 |
 *      |---+---+---|
 * row  | 3 | 4 | 5 |
 *      |---+---+---|
 * row  | 6 | 7 | 8 |
 */
public final class MazeMaker {

    private final MazeConfig mazeConfig;
    private final DisjointSet<Edge> disjointSet;

    public MazeMaker(final MazeConfig mazeConfig,
                     final DisjointSet<Edge> disjointSet) {

        this.mazeConfig = check(mazeConfig).notNull().get();
        this.disjointSet = check(disjointSet).notNull().get();
    }

    public MazeMaker(final MazeConfig mazeConfig) {

        this(mazeConfig, new DisjointSet<>(mazeConfig.getRowsTimesColumns()));
    }

    public List<Edge> generateUndirectedMazeEdges() {

        mazeConfig.getMazeGenerationTimer().start();

        // create edges O(ROWS*COLS)
        final Edge[] edges = initializeMaze();

        // randomize edges O(ROWS*COLS)
        randomizeEdges(edges);

        // join nodes via edges O(ROWS*COLS*k), k is some constant <= 5
        for (final Edge edge : edges) {
            disjointSet.merge(edge.node1, edge.node2, edge);
        }

        final List<Edge> result = disjointSet.getCombinedSubsets();

        mazeConfig.getMazeGenerationTimer().stop();

        return result;
    }

    /**
     * creates a list of all possible paths between cells of length 1
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
     *         | C | x
     *      ---+---+---
     *         | x |
     *
     * C corresponds to cellNumber in this method. The x's represent
     * possible paths from C.
     */
    int[] getAdjacent(final int cellNumber) {

        final boolean isCellBelow = cellNumber < (mazeConfig.getRows() - 1) * mazeConfig.getColumns();
        final boolean isCellToTheRight = (cellNumber + 1) % mazeConfig.getColumns() != 0;

        return isCellBelow && isCellToTheRight ? new int[] { cellNumber + mazeConfig.getColumns(), cellNumber + 1 } :
               isCellBelow ? new int[] { cellNumber + mazeConfig.getColumns() } :
               isCellToTheRight ? new int[] { cellNumber + 1 } :
               new int[] {};
    }
}
