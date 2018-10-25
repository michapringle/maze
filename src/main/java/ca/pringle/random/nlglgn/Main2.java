package ca.pringle.random.nlglgn;

import java.util.Set;

class Main2 {

    public static void test01(String args[]) {
        if (args.length != 4) {
            System.out.println("Error. Expected rows, cols, mode as parameters.");
            System.out.println("Rows - number of rows in the maze. Rows >= 2.");
            System.out.println("Cols - number of columns in the maze. Cols >= 2.");
            System.out.println("RowDiv - how many submazes over the number of rows. Rows >= RowDiv >= 2.");
            System.out.println("ColDiv - how many submazes over the number of columns. Cols >= ColDiv >= 2.");
            System.exit(1);
        }

        Integer rows = new Integer(args[0]);
        Integer cols = new Integer(args[1]);
        Integer rowDiv = new Integer(args[2]);
        Integer colDiv = new Integer(args[3]);

        MazeMaker2 mm = new MazeMaker2(rows.intValue(), cols.intValue(), rowDiv.intValue(), colDiv.intValue());
        Set s = mm.makeMaze();
        MazeDrawer md = new MazeDrawer(mm.ROWS, mm.COLS, (int) 700, (int) 700, s);


        md.setVisible(true);
        md.repaint();
    }


    public static void main(String args[]) {
        test01(args);
    }

}