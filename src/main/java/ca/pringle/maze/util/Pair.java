package ca.pringle.maze.util;

import java.util.Objects;

import static ca.pringle.maze.util.Preconditions.check;

public final class Pair<T1, T2> {

    public final T1 left;
    public final T2 right;

    public Pair(final T1 left,
                final T2 right) {

        this.left = check(left).notNull().get();
        this.right = check(right).notNull().get();
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
