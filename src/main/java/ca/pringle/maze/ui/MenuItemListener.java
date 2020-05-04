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
            case MazeDrawer.SAVE:
                mazeDrawer.save();
                break;
            case MazeDrawer.EXIT:
                System.exit(0);
            case MazeDrawer.INFO:
                mazeDrawer.displayInfo();
                break;
            case MazeDrawer.ABOUT:
                mazeDrawer.displayHelp();
                break;
        }
    }
}
