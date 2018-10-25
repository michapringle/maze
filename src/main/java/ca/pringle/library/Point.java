package ca.pringle.library;


public final class Point {
    public final int x;
    public final int y;


    public Point(int px, int py) {
        x = px;
        y = py;
    }


    public String toString() {
        return new String("(" + x + ", " + y + ")");
    }

}