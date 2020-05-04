package ca.pringle.maze.logic;

import ca.pringle.maze.Preconditions;
import ca.pringle.maze.util.DisjointSet;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

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

        this.mazeConfig = mazeConfig;
        this.disjointSet = disjointSet;

        Preconditions.checkNotNull(this.mazeConfig);
        Preconditions.checkNotNull(this.disjointSet);
    }

    public MazeMaker(final MazeConfig mazeConfig) {
        this(mazeConfig, new DisjointSet<>(mazeConfig.getRowsTimesColumns()));
    }

    public Set<Edge> generateUndirectedMazeEdges() {

        final long start = Instant.now().toEpochMilli();

        //initialization O(ROWS*COLS) time
        final Edge[] edges = initializeMaze();

        //randomize mostPaths O(ROWS*COLS) time
        final Edge[] randomizedEdges = randomizeEdges(edges);

        // O(ROWS*COLS*k) work, k is some constant <= 5
        for (final Edge edge : randomizedEdges) {
            disjointSet.merge(edge.node1, edge.node2, edge);
        }

        final long end = Instant.now().toEpochMilli() - start;
        System.out.println("Maze created in " + end + " milliseconds.");

        return disjointSet.getCombinedSubsets();
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

    Edge[] randomizeEdges(final Edge[] edges) {
        final Edge[] randomizedEdges = edges.clone();
        final Random random = mazeConfig.getNewSeededRandom();

        for (int i = 0; i < randomizedEdges.length; i++) {
            final int rand = random.nextInt(randomizedEdges.length);

            final Edge temp = randomizedEdges[i];
            randomizedEdges[i] = randomizedEdges[rand];
            randomizedEdges[rand] = temp;
        }

        return randomizedEdges;
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
        final int[] list = new int[2];
        int size = 0;

        //see if the cell is not on the very bottom
        if (cellNumber < (mazeConfig.getRows() - 1) * mazeConfig.getColumns()) {
            list[size] = cellNumber + mazeConfig.getColumns();
            size = size + 1;
        }

        //see if the cell is not on the very right
        if ((cellNumber + 1) % mazeConfig.getColumns() != 0) {
            list[size] = cellNumber + 1;
            size = size + 1;
        }

        final int[] returnList = new int[size];
        System.arraycopy(list, 0, returnList, 0, size);

        return returnList;
    }
}
