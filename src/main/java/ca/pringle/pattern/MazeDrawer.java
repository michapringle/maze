package ca.pringle.pattern;


import ca.pringle.library.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class MazeDrawer extends JFrame {
    public static final String SAVE_AS = "Save As...";
    public static final String EXIT = "Exit";
    public static final String ABOUT = "About...";
    private final HashSet pointSet;
    private final Dimensions d;
    private final BufferedImage image;
    private Graphics graphics;


    public MazeDrawer(String title, int rowParam, int colParam, Set s) {
        super(title);

        pointSet = (HashSet) s;
        d = new Dimensions(colParam, rowParam);

        setJMenuBar(createMenuBar());


        image = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_ARGB);
        graphics = image.getGraphics();


        JPanel p = new MazePanel();
        p.setPreferredSize(new Dimension(d.width, d.height));
        JScrollPane sp = new JScrollPane(p);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(sp, BorderLayout.CENTER);


        //let's add some offsets to counter the JMenuBar and such - poorly done
        setSize(d.width + 11, d.height + 53);
        setVisible(true);
        addWindowListener(new EventHandler());
    }


    private JMenuBar createMenuBar() {
        JMenuBar bar = null;
        JMenu menu = null;
        JMenuItem item = null;
        MenuItemListener menuListener = new MenuItemListener();

        bar = new JMenuBar();

        menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_F);

        item = new JMenuItem(SAVE_AS, KeyEvent.VK_A);
        item.addActionListener(menuListener);
        menu.add(item);

        menu.addSeparator();

        item = new JMenuItem(EXIT, KeyEvent.VK_X);
        item.addActionListener(menuListener);
        menu.add(item);

        bar.add(menu);

        menu = new JMenu("Help");
        menu.setMnemonic(KeyEvent.VK_H);

        item = new JMenuItem(ABOUT, KeyEvent.VK_A);
        item.addActionListener(menuListener);
        menu.add(item);

        bar.add(menu);

        return bar;
    }

    public void paint(Graphics g) {
        g.setColor(Color.black);
        drawFullGrid(g);

        setBackground(Color.white);
        g.setColor(Color.white);

        Iterator i = pointSet.iterator();
        while (i.hasNext())
            eraseEdge(g, (Point) i.next());

    }

    private void drawFullGrid(Graphics g) {
        //leave out the line for the start and end of the maze
        g.drawLine(d.maze.xMin, d.maze.yMin + d.WIDTH, d.maze.xMin, d.maze.yMax);
        g.drawLine(d.cols * d.WIDTH + d.maze.xMin, d.maze.yMin, d.cols * d.WIDTH + d.maze.xMin, d.maze.yMax - d.WIDTH);

        //the two drawlines above cover the x = 0 and x = d.cols case
        for (int x = 1; x < d.cols; x++)
            g.drawLine(x * d.WIDTH + d.maze.xMin, d.maze.yMin, x * d.WIDTH + d.maze.xMin, d.maze.yMax);

        for (int y = 0; y <= d.rows; y++)
            g.drawLine(d.maze.xMin, y * d.WIDTH + d.maze.yMin, d.maze.xMax, y * d.WIDTH + d.maze.yMin);
    }

    private void eraseEdge(Graphics g, Point edge) {
        if (edge == null) return;

        int cellXMin = d.maze.xMin + (edge.x % d.cols) * d.WIDTH;
        int cellYMin = d.maze.yMin + (edge.x / d.cols) * d.WIDTH;

        int cellXMax = cellXMin + d.WIDTH;
        int cellYMax = cellYMin + d.WIDTH;

        int x1, y1, x2, y2;

        //x is left of y
        if (edge.x == (edge.y - 1)) {
            x1 = cellXMax;
            y1 = cellYMin + 1;
            x2 = cellXMax;
            y2 = cellYMax - 1;
        }

        //x is below y
        else //if(edge.x == (edge.y-cols))
        {
            x1 = cellXMin + 1;
            y1 = cellYMax;
            x2 = cellXMax - 1;
            y2 = cellYMax;
        }

        g.drawLine(x1, y1, x2, y2);
    }


    private class MazePanel extends JPanel {

        public MazePanel() {
        }

        public void paint(Graphics g) {
            g.drawImage(image, 0, 0, this);

            g.setColor(Color.black);
            drawFullGrid(g);

            setBackground(Color.white);
            g.setColor(Color.white);


            Iterator i = pointSet.iterator();
            while (i.hasNext())
                eraseEdge(g, (Point) i.next());
        }
    }

    private final class MenuItemListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals(SAVE_AS)) {
                System.out.println("saved..");
            } else if (e.getActionCommand().equals(EXIT)) {
                System.exit(0);
            } else if (e.getActionCommand().equals(ABOUT)) {
                JOptionPane.showMessageDialog(null,
                        "Written by Micha J. Pringle.\nTheoretical help provided by F. Warren Burton.\nGraphics (Swing) advice provided By Sean G. Bridges.\nhttp://www.sfu.ca/~mpringle/",
                        "Maze generator.",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                System.out.println(e.paramString());
                System.out.println(e.getActionCommand());
            }

        }
    }

    private final class EventHandler extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }

    private final class Dimensions {
        public static final int WIDTH = 15, BORDER_WIDTH = 15;
        public final int rows, cols, width, height;
        public final Maze maze;


        public Dimensions(int colParam, int rowParam) {
            cols = colParam;
            rows = rowParam;
            maze = new Maze(colParam, rowParam);
            width = maze.xMax + BORDER_WIDTH;
            height = maze.yMax + BORDER_WIDTH;
        }


        public final class Maze {
            public final int xMin, xMax;
            public final int yMin, yMax;

            public Maze(int colParam, int rowParam) {
                xMin = BORDER_WIDTH;
                yMin = BORDER_WIDTH;
                xMax = xMin + colParam * WIDTH;
                yMax = yMin + rowParam * WIDTH;
            }
        }
    }    //End of class Dimensions

}    //End of class MazeDrawer
