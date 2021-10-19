package ca.pringle.maze.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

final class MenuItemListener implements ActionListener {

    private final MazeDrawer mazeDrawer;

    public MenuItemListener(final MazeDrawer mazeDrawer) {
        this.mazeDrawer = mazeDrawer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case MazeDrawer.NEW:
                mazeDrawer.generateNewMaze();
                break;
            case MazeDrawer.TOGGLE_SOLUTION:
                mazeDrawer.toggleMazeSolution();
                break;
            case MazeDrawer.SAVE:
                mazeDrawer.saveMaze();
                break;
            case MazeDrawer.EXIT:
                System.exit(0);
            case MazeDrawer.INFO:
                mazeDrawer.displayMazeDetails();
                break;
            case MazeDrawer.ABOUT:
                mazeDrawer.displayAboutMessage();
                break;
        }
    }
}
