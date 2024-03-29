package ca.pringle.maze.ui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import ca.pringle.maze.logic.MazeConfig;
import ca.pringle.maze.logic.MazeMaker;
import ca.pringle.maze.logic.Path;
import ca.pringle.maze.logic.PathFinder;
import ca.pringle.maze.logic.SpecializedGraph;

public final class MazeDrawer extends JFrame {

    static final String NEW = "New...";
    static final String SAVE = "Save";
    static final String EXIT = "Exit";
    static final String TOGGLE_SOLUTION = "Toggle Solution";
    static final String INFO = "Maze Details";
    static final String ABOUT = "About";

    private final MazePanel mazePanel;
    private JMenuItem newMenuItem;
    private JMenuItem saveMenuItem;
    private JMenuItem solveMenuItem;
    private JMenuItem infoMenuItem;
    private MazeConfig mazeConfig;
    private int[] solution;

    public MazeDrawer() {

        mazePanel = new MazePanel();
    }

    public void init() {
        final JScrollPane scrollPane = new JScrollPane(mazePanel);
        final MenuItemListener menuListener = new MenuItemListener(this);
        final JMenuBar menuBar = createMenuBar(menuListener);
        setJMenuBar(menuBar);

        getContentPane().setLayout(new GridLayout());
        getContentPane().add(scrollPane);

        setSize(400, 400);
        // center on the screen
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenSize.width / 2 - 200, screenSize.height / 2 - 200);
        setVisible(true);

        addWindowListener(new EventHandler());
        generateNewMaze();
        repaint();
    }

    void generateNewMaze() {
        mazeConfig = getNewMazeConfigFromUser();
        if (mazeConfig == null) {
            return;
        }

        SwingUtilities.invokeLater(() -> {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            setAllMenuOptions(false);
        });

        new Thread(() -> {
            final MazeMaker mazeMaker = new MazeMaker(mazeConfig);
            final PanelDimensions panelDimensions = PanelDimensions.calculateMazeDimensionsFrom(mazeConfig.getRows(), mazeConfig.getColumns());

            final SpecializedGraph dag = mazeMaker.generateDag();
            final Path startAndEndNodes = new PathFinder().findLongestPath(dag, mazeConfig);
            solution = new PathFinder().findSolution(dag, startAndEndNodes.fromNode, startAndEndNodes.toNode, mazeConfig);
            // do not show the solution until it is requested, make sure the old one is not displayed
            mazePanel.addSolution(new int[0]);
            mazePanel.update(dag, startAndEndNodes, panelDimensions, mazeConfig);
            mazePanel.setPreferredSize(panelDimensions.toJavaAwtDimension());
            mazePanel.repaint();

            setSize(
                    Math.min(1200, panelDimensions.getPanelWidth() + 5),
                    Math.min(700, panelDimensions.getPanelHeight() + getInsets().top + getJMenuBar().getHeight() + 5)
            );

            SwingUtilities.invokeLater(() -> {
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                setAllMenuOptions(true);
            });
        }).start();
    }

    void toggleMazeSolution() {

        mazePanel.addSolution(mazePanel.hasSolution() ? new int[0] : solution);
        mazePanel.repaint();
    }

    void saveMaze() {
        if (mazeConfig == null) {
            return;
        }

        final String path = "mazes/" + mazeConfig.getSaveFileName();
        final File file = new File(path);
        final boolean isDirMade = file.mkdirs();

        if (!isDirMade && !file.exists()) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error saving file to " + path,
                    "Maze generator",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        SwingUtilities.invokeLater(() -> {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            setAllMenuOptions(false);
            infoMenuItem.setEnabled(true);
        });

        new Thread(() -> {
            try {
                mazePanel.getGraphics().dispose();
                final BufferedImage image = new BufferedImage(
                        mazePanel.getPanelDimensions().getPanelWidth(),
                        mazePanel.getPanelDimensions().getPanelHeight(),
                        BufferedImage.TYPE_INT_ARGB
                );
                final Graphics2D graphics = image.createGraphics();
                mazePanel.paintAll(graphics);
                ImageIO.write(image, mazeConfig.getSaveFileExtension(), file);

                JOptionPane.showMessageDialog(
                        null,
                        "File saved to " + path,
                        "Maze generator",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } catch (IOException | RuntimeException handled) {
                JOptionPane.showMessageDialog(
                        null,
                        "Error saving file to " + path,
                        "Maze generator",
                        JOptionPane.ERROR_MESSAGE
                );
            } finally {
                SwingUtilities.invokeLater(() -> {
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    setAllMenuOptions(true);
                });
            }
        }).start();
    }

    void displayMazeDetails() {
        if (mazeConfig == null) {
            return;
        }

        JOptionPane.showMessageDialog(
                null,
                getInfoMessage(mazeConfig, solution.length),
                "Maze Details",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    void displayAboutMessage() {
        JOptionPane.showMessageDialog(
                null,
                getAboutMessage(),
                "Maze generator",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void setAllMenuOptions(final boolean value) {

        newMenuItem.setEnabled(value);
        saveMenuItem.setEnabled(value);
        solveMenuItem.setEnabled(value);
        infoMenuItem.setEnabled(value);
    }

    private JMenuBar createMenuBar(final MenuItemListener menuListener) {

        final JMenuBar bar = new JMenuBar();

        final JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        newMenuItem = new JMenuItem(NEW, KeyEvent.VK_N);
        newMenuItem.setEnabled(true);
        newMenuItem.addActionListener(menuListener);
        fileMenu.add(newMenuItem);

        solveMenuItem = new JMenuItem(TOGGLE_SOLUTION, KeyEvent.VK_S);
        solveMenuItem.setEnabled(false);
        solveMenuItem.addActionListener(menuListener);
        fileMenu.add(solveMenuItem);

        saveMenuItem = new JMenuItem(SAVE, KeyEvent.VK_A);
        saveMenuItem.setEnabled(false);
        saveMenuItem.addActionListener(menuListener);
        fileMenu.add(saveMenuItem);

        fileMenu.addSeparator();

        final JMenuItem exitMenuItem = new JMenuItem(EXIT, KeyEvent.VK_X);
        exitMenuItem.addActionListener(menuListener);
        fileMenu.add(exitMenuItem);

        bar.add(fileMenu);

        final JMenuItem helpMenu = new JMenu("Help");
        helpMenu.setMnemonic(KeyEvent.VK_H);

        infoMenuItem = new JMenuItem(INFO, KeyEvent.VK_I);
        infoMenuItem.setEnabled(false);
        infoMenuItem.addActionListener(menuListener);
        helpMenu.add(infoMenuItem);

        final JMenuItem aboutMenuItem = new JMenuItem(ABOUT, KeyEvent.VK_A);
        aboutMenuItem.addActionListener(menuListener);
        helpMenu.add(aboutMenuItem);

        bar.add(helpMenu);

        return bar;
    }

    private MazeConfig getNewMazeConfigFromUser() {
        final JTextField rowField = new JTextField("110", 3);
        final JTextField columnField = new JTextField("85", 3);
        final JTextField seedField = new JTextField(Long.toString(new Random().nextLong()), 14);

        final JPanel dimensions = new JPanel();
        dimensions.add(new JLabel("rows"));
        dimensions.add(rowField);
        dimensions.add(new JLabel("columns"));
        dimensions.add(columnField);

        final JPanel seed = new JPanel();
        seed.add(new JLabel("seed"));
        seed.add(seedField);

        final JPanel fullPanel = new JPanel(new GridLayout(0, 1));
        fullPanel.add(dimensions);
        fullPanel.add(seed);

        final int confirmation = JOptionPane.showConfirmDialog(
                null,
                fullPanel,
                "Enter new maze parameters",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (confirmation == JOptionPane.CANCEL_OPTION || confirmation == JOptionPane.CLOSED_OPTION) {
            return mazeConfig;
        }

        try {
            return new MazeConfig(
                    rowField.getText(),
                    columnField.getText(),
                    seedField.getText()
            );
        } catch (RuntimeException handled) {
            JOptionPane.showMessageDialog(
                    null,
                    MazeConfig.ROWS_MESSAGE + "\n" + MazeConfig.COLUMNS_MESSAGE,
                    "Bad Input",
                    JOptionPane.ERROR_MESSAGE
            );

            return getNewMazeConfigFromUser();
        }
    }

    private String getInfoMessage(final MazeConfig mazeConfig,
                                  final int solutionLength) {

        return String.format(
                "A %s row x %s column maze, made with seed %s.\n" +
                        "Maze generated in %s milliseconds, " +
                        "longest path of length %s found in %s milliseconds, " +
                        "solution found in %s milliseconds",
                mazeConfig.getRows(),
                mazeConfig.getColumns(),
                mazeConfig.getSeed(),
                mazeConfig.getMazeGenerationTimer().getElapsedTimeInMillis(),
                solutionLength,
                mazeConfig.getPathGenerationTimer().getElapsedTimeInMillis(),
                mazeConfig.getSolutionGenerationTimer().getElapsedTimeInMillis()
        );
    }

    private String getAboutMessage() {
        return "Find your way from one red square to the other.\n\n" +
                "Written by Micha Pringle.\n" +
                "Theoretical help provided by F. Warren Burton.\n" +
                "Graphics help provided By Sean Bridges.";
    }

    private static class EventHandler extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }
}
