package ca.pringle.maze.util;

import ca.pringle.maze.Preconditions;

import java.util.Objects;

public final class Pair<T1, T2> {
    public final T1 left;
    public final T2 right;

    public Pair(final T1 left,
                final T2 right) {

        this.left = left;
        this.right = right;

        Preconditions.checkNotNull(this.left);
        Preconditions.checkNotNull(this.right);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Pair) {

            final Pair<?, ?> pair = (Pair<?, ?>) o;
            return Objects.equals(left, pair.left) &&
                    Objects.equals(right, pair.right);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)", left == null ? "-" : left, right == null ? "-" : right);
    }
}
