package ca.pringle.pattern;


import ca.pringle.library.DisjointSet;
import ca.pringle.library.Point;
import ca.pringle.library.Random;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public final class MazeMaker {
    public final int ROWS;
    public final int COLS;

    private final int divRows;
    private final int divCols;
    private final DisjointSet dj;
    private final Random random;


    public MazeMaker(int rows, int cols, int rowBreak, int colBreak) {
        if (rows < 2) ROWS = 2;
        else ROWS = rows;
        if (cols < 2) COLS = 2;
        else COLS = cols;

        dj = new DisjointSet(ROWS * COLS);
        random = new Random();

        if (rowBreak > rows) divRows = rows;
        else if (rowBreak < 1) divRows = 1;
        else divRows = rowBreak;

        if (colBreak > cols) divCols = cols;
        else if (colBreak < 1) divCols = 1;
        else divCols = colBreak;
    }


    public Set makeMaze() {
        ArrayList mostPaths = new ArrayList();
        ArrayList lastPaths = new ArrayList();
        Point newPath = null, p = null;
        Object temp = null;
        int[] adjacentPaths = null;
        int pick, rand1, rand2;
        int rowCount = 1, colCount = 0;


        Date si = new Date();
        System.out.println("Initializing maze contruction " + si);
        long start = si.getTime();


        //initialization O(ROWS*COLS) time
        for (int i = 0; i < ROWS * COLS; i++) {
            //create a list of all possible paths of length 1
            //these will be chosen at ca.pringle.random later
            adjacentPaths = getAdjacents(i);

            for (int j = 0; j < adjacentPaths.length; j++) {
                p = new Point(i, adjacentPaths[j]);

                if (colCount == COLS * 2 - 1) {
                    rowCount = rowCount + 1;
                    colCount = 1;
                } else
                    colCount = colCount + 1;


                if (colCount % ((COLS / divCols) * 2) == 0 && rowCount != ROWS)
                    lastPaths.add(p);
                else if (colCount % (COLS / divCols) == 0 && rowCount == ROWS)
                    lastPaths.add(p);
                else if (rowCount % (ROWS / divRows) == 0 & p.x != (p.y - 1))
                    lastPaths.add(p);
                else
                    mostPaths.add(p);

            }
        }

        //convert to arrays
        Object[] mostPathsA = mostPaths.toArray();
        Object[] lastPathsA = lastPaths.toArray();


        //randomize mostPaths O(ROWS*COLS) time
        for (int i = 0; i < mostPathsA.length; i++) {
            rand1 = random.discrete.nextInt(mostPathsA.length);
            rand2 = random.discrete.nextInt(mostPathsA.length);

            temp = mostPathsA[rand1];
            mostPathsA[rand1] = mostPathsA[rand2];
            mostPathsA[rand2] = temp;
        }

        //randomize lastPaths O(ROWS*COLS) time
        for (int i = 0; i < lastPathsA.length; i++) {
            rand1 = random.discrete.nextInt(lastPathsA.length);
            rand2 = random.discrete.nextInt(lastPathsA.length);

            temp = lastPathsA[rand1];
            lastPathsA[rand1] = lastPathsA[rand2];
            lastPathsA[rand2] = temp;
        }


        // O(ROWS*COLS*k) work, k is some constant <= 5
        for (int count = 0; count < mostPathsA.length; count++) {
            newPath = (Point) mostPathsA[count];
            dj.merge(newPath.x, newPath.y, newPath);
        }

        // O(ROWS*COLS*k) work, k is some constant <= 5
        for (int count = 0; count < lastPathsA.length; count++) {
            newPath = (Point) lastPathsA[count];
            dj.merge(newPath.x, newPath.y, newPath);
        }

        Date e = new Date();
        long end = e.getTime();

        System.out.println("Finishing maze construction " + e);
        System.out.println("Took " + (end - start) + " milliseconds.");

        HashSet s = new HashSet(dj.values());
        return s;
    }


    public int getSeed() {
        return random.seed;
    }


    /* A maze can never have diagonal paths.
     *
     *         |   |
     *      ---|---|---
     *         | C | x
     *      ---|---|---
     *         | x |
     *
     * C corresponds to cellNumber in this method. The x's represent
     * possible paths from C.
     */
    private int[] getAdjacents(int cellNumber) {
        int[] list = new int[2];
        int size = 0;


        //see if the cell is not on the very bottom
        if (cellNumber < (ROWS - 1) * COLS) {
            list[size] = cellNumber + COLS;
            size = size + 1;
        }

        //see if the cell is not on the very right
        if ((cellNumber + 1) % COLS != 0) {
            list[size] = cellNumber + 1;
            size = size + 1;
        }

        int[] returnList = new int[size];
        for (int i = 0; i < size; i++)
            returnList[i] = list[i];

        return returnList;
    }

}