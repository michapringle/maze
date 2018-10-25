package ca.pringle.pattern;

import ca.pringle.library.Cast;

import java.util.Set;

public final class Main {

    public static void test01(String args[]) {
        final String rows;
        final String cols;
        final String rowDiv;
        final String colDiv;

        if (args.length == 2) {
            rows = args[0];
            cols = args[1];
            rowDiv = new String("1");
            colDiv = new String("1");
        } else if (args.length == 4) {
            rows = args[0];
            cols = args[1];
            rowDiv = args[2];
            colDiv = args[3];
        } else {
            /* dumb compiler. */
            rows = null;
            cols = null;
            rowDiv = null;
            colDiv = null;

            System.out.println("Error. Expected rows, cols as paramaters, with RowDiv, ColDiv as optional parameters.");
            System.out.println("Rows - number of rows in the maze. Rows >= 2.");
            System.out.println("Cols - number of columns in the maze. Cols >= 2.");
            System.out.println("RowDiv - how many submazes over the number of rows. Rows >= RowDiv >= 1.");
            System.out.println("ColDiv - how many submazes over the number of columns. Cols >= ColDiv >= 1.");
            System.exit(1);
        }


        MazeMaker mazeMaker = new MazeMaker(Cast.toInt(rows), Cast.toInt(cols), Cast.toInt(rowDiv), Cast.toInt(colDiv));
        Set maze = mazeMaker.makeMaze();

        String title = "A " + rows + " row x " + cols + " column maze. Generated using seed " + mazeMaker.getSeed() + ".";
        MazeDrawer md = new MazeDrawer(title, mazeMaker.ROWS, mazeMaker.COLS, maze);

//		System.out.println(s);

        md.setVisible(true);
        md.repaint();
    }


    public static void main(String args[]) {
        test01(args);
    }

}