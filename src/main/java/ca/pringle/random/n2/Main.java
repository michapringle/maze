package ca.pringle.random.n2;

import java.util.Set;

class Main {

    public static void test01(String args[]) {
        if (args.length != 4) {
            System.out.println("Error. Expected rows, cols, mode as parameters.");
            System.out.println("Rows - number of rows in the maze. Rows > 2.");
            System.out.println("Cols - number of columns in the maze. Cols > 2.");
            System.out.println("X resolution - The width of the maze window in pixels. 100 <= x <= 1280");
            System.out.println("Y resolution - The height of the maze window in pixels. 100 <= y <= 960");
            System.exit(1);
        }

        Integer rows = new Integer(args[0]);
        Integer cols = new Integer(args[1]);
        Integer x = new Integer(args[2]);
        Integer y = new Integer(args[3]);

        if (x.intValue() < 100 || x.intValue() > 1280) {
            System.out.println("X resolution - The width of the maze window in pixels. 100 <= x <= 1280");
            System.exit(1);
        }

        if (y.intValue() < 100 || y.intValue() > 960) {
            System.out.println("Y resolution - The height of the maze window in pixels. 100 <= y <= 960");
            System.exit(1);
        }

        MazeMaker mm = new MazeMaker(rows.intValue(), cols.intValue());
        Set s = mm.makeMaze();
        MazeDrawer md = new MazeDrawer(mm.ROWS, mm.COLS, x.intValue(), y.intValue(), s);

//		System.out.println(s);

        md.setVisible(true);
        md.repaint();
    }


    public static void main(String args[]) {
        test01(args);
    }

}