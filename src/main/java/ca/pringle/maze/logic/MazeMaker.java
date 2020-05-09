package ca.pringle.maze.logic;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import ca.pringle.maze.util.DisjointSet;
import ca.pringle.maze.util.Preconditions;

/**
 * Creates a maze using a graph that is rows x columns in size. Each
 * node is given a number in ascending order, starting from the top
 * left corner, working down to the bottom right corner.
 * <p>
 * c   c   c
 * o   o   o
 * l   l   l
 * row  | 0 | 1 | 2 |
 * |---+---+---|
 * row  | 3 | 4 | 5 |
 * |---+---+---|
 * row  | 6 | 7 | 8 |
 */
public final class MazeMaker {
    private final MazeConfig mazeConfig;
    private final DisjointSet<Edge> disjointSet;

    public MazeMaker(final MazeConfig mazeConfig,
                     final DisjointSet<Edge> disjointSet) {

        this.mazeConfig = mazeConfig;
        this.disjointSet = disjointSet;

        Preconditions.checkNotNull(this.mazeConfig);
        Preconditions.checkNotNull(this.disjointSet);
    }

    public MazeMaker(final MazeConfig mazeConfig) {
        this(mazeConfig, new DisjointSet<>(mazeConfig.getRowsTimesColumns()));
    }

    public Set<Edge> generateUndirectedMazeEdges() {

        mazeConfig.getMazeGenerationTimer().start();

        //initialization O(ROWS*COLS) time
        final Edge[] edges = initializeMaze();

        //randomize mostPaths O(ROWS*COLS) time
        randomizeEdges(edges);

        // O(ROWS*COLS*k) work, k is some constant <= 5
        for (final Edge edge : edges) {
            disjointSet.merge(edge.node1, edge.node2, edge);
        }

        final Set<Edge> result = disjointSet.getCombinedSubsets();

        mazeConfig.getMazeGenerationTimer().stop();

        return result;
    }

    /**
     * creates a list of all possible paths between cells of length 1
     */
    Edge[] initializeMaze() {

        final List<Edge> edges = new LinkedList<>();

        for (int i = 0; i < mazeConfig.getRowsTimesColumns(); i++) {
            for (final int adjacentPath : getAdjacent(i)) {
                edges.add(new Edge(i, adjacentPath));
            }
        }

        return edges.toArray(new Edge[] {});
    }

    void randomizeEdges(final Edge[] edges) {
        final Random random = mazeConfig.getNewSeededRandom();

        for (int i = 0; i < edges.length; i++) {
            final int rand = random.nextInt(edges.length);

            final Edge temp = edges[i];
            edges[i] = edges[rand];
            edges[rand] = temp;
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
