package ca.pringle.maze.util;

import java.lang.reflect.Array;

public final class Arrays {

    private Arrays() {
    }

    @SuppressWarnings("unchecked")
    public static <E> E[] newInstance(int length) {
        return (E[]) newInstanceBody(length);
    }

    @SafeVarargs
    private static <E> Object newInstanceBody(int length, E... array) {
        return Array.newInstance(array.getClass().getComponentType(), length);
    }
}
