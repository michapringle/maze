package ca.pringle.maze.logic;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import ca.pringle.maze.util.Pair;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class PathFinderTest {

    private Set<Edge> maze3x4() {
        Set<Edge> s = new HashSet<>();

        s.add(new Edge(0, 1));
        s.add(new Edge(2, 3));
        s.add(new Edge(4, 5));
        s.add(new Edge(5, 6));
        s.add(new Edge(6, 7));
        s.add(new Edge(8, 9));
        s.add(new Edge(10, 11));
        s.add(new Edge(1, 5));
        s.add(new Edge(2, 6));
        s.add(new Edge(5, 9));
        s.add(new Edge(7, 11));

        return s;
    }

    private Set<Edge> maze6x6() {
        Set<Edge> s = new HashSet<>();

        s.add(new Edge(0, 1));
        s.add(new Edge(0, 6));
        s.add(new Edge(6, 7));
        s.add(new Edge(7, 13));
        s.add(new Edge(12, 13));

        s.add(new Edge(13, 14));
        s.add(new Edge(14, 15));
        s.add(new Edge(9, 15));
        s.add(new Edge(8, 9));

        s.add(new Edge(9, 10));
        s.add(new Edge(10, 16));
        s.add(new Edge(16, 17));
        s.add(new Edge(11, 17));
        s.add(new Edge(5, 11));
        s.add(new Edge(4, 5));
        s.add(new Edge(3, 4));
        s.add(new Edge(2, 3));

        s.add(new Edge(15, 21));
        s.add(new Edge(21, 22));

        s.add(new Edge(21, 27));
        s.add(new Edge(27, 28));
        s.add(new Edge(28, 29));
        s.add(new Edge(29, 35));

        s.add(new Edge(23, 29));
        s.add(new Edge(28, 34));
        s.add(new Edge(27, 33));

        s.add(new Edge(13, 19));
        s.add(new Edge(19, 20));
        s.add(new Edge(20, 26));
        s.add(new Edge(25, 26));
        s.add(new Edge(24, 25));
        s.add(new Edge(18, 24));

        s.add(new Edge(24, 30));

        s.add(new Edge(25, 31));
        s.add(new Edge(31, 32));

        return s;
    }

    private Set<Edge> maze6x6Second() {
        Set<Edge> s = new HashSet<>();

        s.add(new Edge(1, 2));
        s.add(new Edge(0, 6));
        s.add(new Edge(6, 7));
        s.add(new Edge(7, 13));
        s.add(new Edge(12, 13));

        s.add(new Edge(13, 14));
        s.add(new Edge(14, 15));
        s.add(new Edge(9, 15));
        s.add(new Edge(8, 9));

        s.add(new Edge(9, 10));
        s.add(new Edge(10, 16));
        s.add(new Edge(16, 17));
        s.add(new Edge(11, 17));
        s.add(new Edge(5, 11));
        s.add(new Edge(4, 5));
        s.add(new Edge(3, 4));
        s.add(new Edge(2, 3));

        s.add(new Edge(15, 21));
        s.add(new Edge(21, 22));

        s.add(new Edge(21, 27));
        s.add(new Edge(27, 28));
        s.add(new Edge(28, 29));
        s.add(new Edge(29, 35));

        s.add(new Edge(23, 29));
        s.add(new Edge(28, 34));
        s.add(new Edge(27, 33));

        s.add(new Edge(13, 19));
        s.add(new Edge(19, 20));
        s.add(new Edge(20, 26));
        s.add(new Edge(25, 26));
        s.add(new Edge(24, 25));
        s.add(new Edge(18, 24));

        s.add(new Edge(24, 30));

        s.add(new Edge(25, 31));
        s.add(new Edge(31, 32));

        return s;
    }

    @Test
    void findLongestPathShouldWork3x6() {
        assertEquals(new Pair<>(10, 0), new PathFinder().findLongestPath(maze3x4(), new MazeConfig(3, 4, 0)));
    }

    @Test
    void findLongestPathShouldWork6x6() {
        assertEquals(new Pair<>(2, 18), new PathFinder().findLongestPath(maze6x6(), new MazeConfig(6, 6, 0)));
    }

    @Test
    void findLongestPathShouldWork6x6Second() {
        assertEquals(new Pair<>(1, 18), new PathFinder().findLongestPath(maze6x6Second(), new MazeConfig(6, 6, 0)));
    }
}
