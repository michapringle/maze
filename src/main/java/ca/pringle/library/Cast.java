package ca.pringle.library;


public final class Cast {

    public static int toInt(String s) {
        Integer i = new Integer(s);
        return i.intValue();
    }

}