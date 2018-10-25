package ca.pringle.random.n2;

public final class Point {
    public final int x;
    public final int y;


    Point(int px, int py) {
        x = px;
        y = py;
    }


    public String toString() {
        return new String("(" + x + ", " + y + ")");
    }

}