package ca.pringle.maze.util;

public final class Preconditions<T> {

    private final T reference;

    private Preconditions(final T reference) {
        this.reference = reference;
    }

    public static <T> Preconditions<T> check(final T reference) {

        return new Preconditions<>(reference);
    }

    public Preconditions<T> notNull() {

        if (reference != null) {
            return this;
        }

        throw new NullPointerException();
    }

    public Preconditions<T> argument(final boolean expression,
                                     final Object errorMessage) {

        if (expression) {
            return this;
        }

        throw new IllegalArgumentException(String.valueOf(errorMessage));
    }

    public T get() {
        return reference;
    }
}
