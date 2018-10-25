package ca.pringle.random.n2;

import java.awt.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MazeDrawer extends Frame {
    public final int ROWS;
    public final int COLS;

    private final int width, xmin, xmax, ymin, ymax;
    private HashSet pointSet = null;


    MazeDrawer(int rows, int cols, int XMAX, int YMAX, Set s) {
        setSize(XMAX, YMAX);

        ROWS = rows;
        COLS = cols;

        width = min((XMAX - 11) / COLS, (YMAX - 31) / ROWS);

        xmin = 10;
        ymin = 30;
        xmax = COLS * width + xmin;
        ymax = ROWS * width + ymin;

        pointSet = (HashSet) s;
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
        g.drawLine(xmin, ymin + width, xmin, ymax);
        g.drawLine(COLS * width + xmin, ymin, COLS * width + xmin, ymax - width);

        for (int x = 1; x < COLS; x++)
            g.drawLine(x * width + xmin, ymin, x * width + xmin, ymax);

        for (int y = 0; y <= ROWS; y++)
            g.drawLine(xmin, y * width + ymin, xmax, y * width + ymin);
    }


    private void eraseEdge(Graphics g, Point edge) {
        if (edge == null) return;

        int cellXMin = xmin + (edge.x % COLS) * width;
        int cellYMin = ymin + (edge.x / COLS) * width;

        int cellXMax = cellXMin + width;
        int cellYMax = cellYMin + width;

        int x1, y1, x2, y2;

        //x is left of y
        if (edge.x == (edge.y - 1)) {
            x1 = cellXMax;
            y1 = cellYMin + 1;
            x2 = cellXMax;
            y2 = cellYMax - 1;
        }

        //x is below y
        else //if(edge.x == (edge.y-COLS))
        {
            x1 = cellXMin + 1;
            y1 = cellYMax;
            x2 = cellXMax - 1;
            y2 = cellYMax;
        }

        g.drawLine(x1, y1, x2, y2);
    }


    private int min(int a, int b) {
        if (a > b) return b;
        return a;
    }

}	

