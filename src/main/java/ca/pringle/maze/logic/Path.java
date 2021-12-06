package ca.pringle.maze.logic;

import java.util.Objects;

import static ca.pringle.maze.util.Checks.check;

public final class Path {

    public final int fromNode;
    public final int toNode;

    public Path(final int fromNode,
                final int toNode) {

        this.fromNode = check(fromNode).isGreaterThan(-1, "fromNode must be >= 0");
        this.toNode = check(toNode).isGreaterThan(-1, "toNode must be >= 0");
    }

    @Override
    public boolean equals(Object o) {

        if (o instanceof Path) {

            final Path that = (Path) o;
            return this.fromNode == that.fromNode && this.toNode == that.toNode;
        }
        return false;
    }

    @Override
    public int hashCode() {

        return Objects.hash(fromNode, toNode);
    }

    @Override
    public String toString() {

        return String.format("(%s, %s)", fromNode, toNode);
    }
}
