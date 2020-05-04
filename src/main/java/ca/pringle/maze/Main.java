package ca.pringle.maze;

import ca.pringle.maze.ui.MazeDrawer;

public final class Main {

    public static void main(final String... args) {
        final MazeDrawer mazeDrawer = new MazeDrawer();
        mazeDrawer.setVisible(true);
        mazeDrawer.repaint();
    }
}