package ca.pringle.random.n2;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Set;

public final class MazeMaker {
    public final int ROWS;
    public final int COLS;

    private ArrayList edgeSets = null;
    private Random random = null;


    public MazeMaker(int rows, int cols) {
        if (rows < 2) ROWS = 2;
        else ROWS = rows;
        if (cols < 2) COLS = 2;
        else COLS = cols;

        edgeSets = new ArrayList(ROWS * COLS);
        random = new Random();
    }


    public Set makeMaze() {
        int adjoiningCell = 0;
        int cell = 0;
        int edgeSetIndex = 0;
        Set_ set1 = null;
        Set_ set2 = null;

        Date s = new Date();
        System.out.println("Initializing maze contruction " + s);
        long start = s.getTime();

        ArrayList allPaths = initializeGrid();
        Point newPath = null;


        while (edgeSets.size() > 1) {
            cell = random.nextInt(allPaths.size());
            newPath = (Point) allPaths.get(cell);
            allPaths.remove(cell);

            edgeSetIndex = findSet(newPath.x);
            set1 = (Set_) edgeSets.get(edgeSetIndex);
            edgeSets.remove(edgeSetIndex);

            edgeSetIndex = findSet(newPath.y);

            //if the edgeSetIndex is -1, then that means that set1 and set2
            //are now contained in the same set and we want to do nothing.
            if (edgeSetIndex != -1) {
                set2 = (Set_) edgeSets.get(edgeSetIndex);
                edgeSets.remove(edgeSetIndex);
                set1.merge(set2);
                set1.addEdge(newPath);
            }

            edgeSets.add(set1);
        }

        Date e = new Date();
        long end = e.getTime();

        System.out.println("Finishing maze construction " + e);
        System.out.println("Took " + (end - start) + " milliseconds.");

        return ((Set_) edgeSets.get(0)).getAll();
    }


    //---------------------------------------------------------------------------
    //Each grid location is a set of edges connected to it
    //initially, there are no edges,
    private ArrayList initializeGrid() {
        ArrayList allPaths = new ArrayList(ROWS * COLS * 3);
        int[] adjacentPaths = null;

        for (int i = 0; i < ROWS * COLS; i++) {
            Set_ s = new Set_("" + i);
            edgeSets.add(s);

            adjacentPaths = getAdjacents(i);
            for (int j = 0; j < adjacentPaths.length; j++)
                allPaths.add(new Point(i, adjacentPaths[j]));
        }
        return allPaths;
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


    //returns the index in edgeSets to the set containing elem
    private int findSet(int elem) {
        Set_ s = null;

        for (int i = 0; i < edgeSets.size(); i++) {
            s = (Set_) edgeSets.get(i);
            if (s.containsEl("" + elem)) return i;
        }
        return -1;
    }


}