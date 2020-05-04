package ca.pringle.maze.logic;

import ca.pringle.maze.Preconditions;

import java.util.Random;

public final class MazeConfig {
    public static final String ROWS_MESSAGE = "rows must be an integer in the range 2..9999";
    public static final String COLUMNS_MESSAGE = "columns must be an integer in the range 2..9999";

    private final int rows;
    private final int columns;
    private final long seed;

    public MazeConfig(final int rows,
                      final int columns,
                      final long seed) {

        this.rows = rows;
        this.columns = columns;
        this.seed = seed;

        Preconditions.checkArgument(this.rows >= 2 && this.rows <= 9999, ROWS_MESSAGE);
        Preconditions.checkArgument(this.columns >= 2 && this.columns <= 9999, COLUMNS_MESSAGE);
    }

    public MazeConfig(final String rows,
                      final String columns,
                      final String seed) {

        this(Integer.parseInt(rows), Integer.parseInt(columns), Long.parseLong(seed));
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getRowsTimesColumns() {
        return rows * columns;
    }

    public long getSeed() {
        return seed;
    }

    public String getSaveFileName() {
        return String.format("%sx%s_s%s.%s", rows, columns, seed, getSaveFileExtension());
    }

    public String getSaveFileExtension() {
        return "png";
    }

    public Random getNewSeededRandom() {
        return new Random(seed);
    }
}
