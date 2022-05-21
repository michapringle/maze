package ca.pringle.maze.ui;

import java.awt.Dimension;

import static ca.pringle.maze.util.Checks.check;

/**
 * Panel dimensions stores the dimensions of the maze panel, and the dimensions of the maze.
 * <p/>
 * The panel dimensions are (0,0) to (getPanelWidth(), getPanelHeight())
 * <p/>
 * The maze dimensions are (getMazeXMin(), getMazeYMin()) to (getMazeXMax, getMazeYMax())
 * <p/>
 * The difference between the panel and maze dimensions is the empty border space surrounding the maze.
 */
final class PanelDimensions {

    /*
     * Required conditions
     * NODE_INSET > 0
     * NODE_WIDTH > 2*(NODE_INSET+1)
     * BORDER_WIDTH >= 0
     */
    public static final int NODE_WIDTH = 15;
    public static final int NODE_INSET = 2;
    private static final int BORDER_WIDTH = 15;

    private final int mazeXMax;
    private final int mazeYMax;

    private PanelDimensions(final int mazeXMax,
                            final int mazeYMax) {

        this.mazeXMax = mazeXMax;
        this.mazeYMax = mazeYMax;
    }

    /**
     * @param rows    - determine the y dimension of the maze (vertical)
     * @param columns - determine the x dimension of the maze (horizontal)
     */
    public static PanelDimensions calculateMazeDimensionsFrom(final int rows,
                                                              final int columns) {

        check(rows).isTrue(rows > 0, "Rows must >= 0");
        check(columns).isTrue(columns > 0, "Columns must >= 0");

        return new PanelDimensions(BORDER_WIDTH + columns * NODE_WIDTH, BORDER_WIDTH + rows * NODE_WIDTH);
    }

    public int getMazeXMin() {

        return BORDER_WIDTH;
    }

    public int getMazeXMax() {

        return mazeXMax;
    }

    public int getMazeYMin() {

        return BORDER_WIDTH;
    }

    public int getMazeYMax() {

        return mazeYMax;
    }

    public int getPanelWidth() {

        return getMazeXMin() + getMazeXMax();
    }

    public int getPanelHeight() {

        return getMazeYMin() + getMazeYMax();
    }

    public int nodeLeft(final int node,
                        final int columns) {

        return getMazeXMin() + (node % columns) * NODE_WIDTH;
    }

    public int nodeTop(final int node,
                       final int columns) {

        return getMazeYMin() + (node / columns) * NODE_WIDTH;
    }

    public Dimension toJavaAwtDimension() {

        return new Dimension(getPanelWidth(), getPanelHeight());
    }
}
