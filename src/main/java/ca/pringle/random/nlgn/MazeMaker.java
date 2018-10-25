package ca.pringle.random.nlgn;

import java.util.Date;
import java.util.Random;
import java.util.Set;

public final class MazeMaker {
    public final int ROWS;
    public final int COLS;

    private DisjointSet dj = null;
    private Representative[] representative = null;
    private Random random = null;


    public MazeMaker(int rows, int cols) {
        if (rows < 2) ROWS = 2;
        else ROWS = rows;
        if (cols < 2) COLS = 2;
        else COLS = cols;

        dj = new DisjointSet(ROWS * COLS);
        representative = new Representative[ROWS * COLS];
        random = new Random();
    }


    public Set makeMaze() {
        Point[] allPaths = new Point[2 * ROWS * COLS - ROWS - COLS];
        Point newPath = null, temp = null;
        int[] adjacentPaths = null;
        int count, rand1, rand2;
        Representative r1 = null, r2 = null;


        Date s = new Date();
        System.out.println("Starting maze contruction " + s);
        long start = s.getTime();


        //initialization O(ROWS*COLS) time
        for (int i = 0; i < ROWS * COLS; i++) {
            representative[i] = new Representative(i);
            dj.add(representative[i], null);
        }

        count = 0;
        for (int i = 0; i < ROWS * COLS; i++) {
            adjacentPaths = getAdjacents(i);

            for (int j = 0; j < adjacentPaths.length; j++) {
                allPaths[count] = new Point(i, adjacentPaths[j]);
                count = count + 1;
            }
        }

        //randomize allPaths O(ROWS*COLS) time
        for (int i = 0; i < 2 * ROWS * COLS - ROWS - COLS; i++) {
            rand1 = random.nextInt(allPaths.length);
            rand2 = random.nextInt(allPaths.length);

            temp = allPaths[rand1];
            allPaths[rand1] = allPaths[rand2];
            allPaths[rand2] = temp;
        }


        //main loop O(ROWS*COLS*lg(ROWS*COLS))
        count = 0;
        while (dj.size() > 1) {
            newPath = (Point) allPaths[count];
            r1 = representative[newPath.x];
            r2 = representative[newPath.y];

            //merge should take at most the time of the shorter list,
            //contributing an overall time of O(lg(ROWS*COLS)) to this loop
            dj.merge(r1, r2, newPath);
            count = count + 1;
        }

        Date e = new Date();
        long end = e.getTime();

        System.out.println("Finishing maze construction " + e);
        System.out.println("Took " + (end - start) + " milliseconds.");

        //O(ROWS*COLS) clean up of set
        //System.out.println(dj.values());
        return dj.values();
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

        //see if the cell is not on the very right
        if ((cellNumber + 1) % COLS != 0) {
            list[size] = cellNumber + 1;
            size = size + 1;
        }

        //see if the cell is not on the very bottom
        if (cellNumber < (ROWS - 1) * COLS) {
            list[size] = cellNumber + COLS;
            size = size + 1;
        }

        int[] returnList = new int[size];
        for (int i = 0; i < size; i++)
            returnList[i] = list[i];

        return returnList;
    }

}