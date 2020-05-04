package ca.pringle.maze.ui;

import ca.pringle.maze.Preconditions;

/**
 * rows are the y dimension, columns are the x dimension
 */
final class PanelDimensions {
    final int rows, columns, pathWidth, borderWidth, panelWidth, panelHeight;
    final MazeDimensions mazeDimensions;

    public PanelDimensions(int rows, int columns, int pathWidth, int borderWidth) {
        this.rows = rows;
        this.columns = columns;
        this.pathWidth = pathWidth;
        this.borderWidth = borderWidth;
        this.panelWidth = 2 * borderWidth + columns * pathWidth;
        this.panelHeight = 2 * borderWidth + rows * pathWidth;

        Preconditions.checkArgument(this.rows > 0, "Rows must >= 0");
        Preconditions.checkArgument(this.columns > 0, "Columns must >= 0");
        Preconditions.checkArgument(this.pathWidth > 0, "Path Width must >= 0");
        Preconditions.checkArgument(this.borderWidth > 0, "Border Width must >= 0");

        this.mazeDimensions = new MazeDimensions(
                borderWidth,
                borderWidth + columns * pathWidth,
                borderWidth,
                borderWidth + rows * pathWidth
        );
    }

    public static class MazeDimensions {
        public final int xMin, xMax;
        public final int yMin, yMax;

        private MazeDimensions(final int xMin,
                               final int xMax,
                               final int yMin,
                               final int yMax) {

            this.xMin = xMin;
            this.xMax = xMax;
            this.yMin = yMin;
            this.yMax = yMax;
        }
    }
}
