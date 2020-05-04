package ca.pringle.maze.logic;

import ca.pringle.maze.util.Pair;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

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

    @Test
    void findLongestPathShouldWork() {
        assertEquals(new Pair<>(10, 0), new PathFinder().findLongestPath(maze3x4()));
    }

//    @Test
//    void maxShouldReturnMax() {
//        final Pair<Integer, Edge> pair1 = new Pair<>(0, new Edge(0, 0));
//        final Pair<Integer, Edge> pair2 = new Pair<>(1, new Edge(1, 1));
//        final Pair<Integer, Edge> pair3 = new Pair<>(2, new Edge(2, 2));
//
//        assertEquals(new Pair<>(0, new Edge(0, 0)), new PathFinder().max(pair1));
//        assertEquals(new Pair<>(1, new Edge(1, 1)), new PathFinder().max(pair1, pair2));
//        assertEquals(new Pair<>(2, new Edge(2, 2)), new PathFinder().max(pair1, pair2, pair3));
//    }
}
