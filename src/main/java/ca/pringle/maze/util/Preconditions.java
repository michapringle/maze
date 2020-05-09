package ca.pringle.maze.util;

public final class Preconditions {
    private Preconditions() {
    }

    public static <T> void checkNotNull(final T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
    }

    public static void checkArgument(final boolean expression,
                                     final Object errorMessage) {
        if (!expression) {
            throw new IllegalArgumentException(String.valueOf(errorMessage));
        }
    }
}
