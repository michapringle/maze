package ca.pringle.maze.ui;

import static ca.pringle.maze.util.Preconditions.check;

/**
 * rows are the y dimension, columns are the x dimension
 */
final class PanelDimensions {
    final int rows, columns, pathWidth, borderWidth, panelWidth, panelHeight;
    final MazeDimensions mazeDimensions;

    public PanelDimensions(final int rows,
                           final int columns,
                           final int pathWidth,
                           final int borderWidth) {

        this.rows = check(rows).argument(rows > 0, "Rows must >= 0").get();
        this.columns = check(columns).argument(columns > 0, "Columns must >= 0").get();
        this.pathWidth = check(pathWidth).argument(pathWidth > 0, "Path Width must >= 0").get();
        this.borderWidth = check(borderWidth).argument(borderWidth > 0, "Border Width must >= 0").get();

        this.panelWidth = 2 * borderWidth + columns * pathWidth;
        this.panelHeight = 2 * borderWidth + rows * pathWidth;

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
