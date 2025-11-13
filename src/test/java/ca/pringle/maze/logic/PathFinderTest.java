package ca.pringle.maze.logic;

import org.junit.jupiter.api.Test;

import static ca.pringle.maze.TestData.MAZE_2X2_S0;
import static ca.pringle.maze.TestData.MAZE_3X4_S0;
import static ca.pringle.maze.TestData.MAZE_6X6_S0;
import static ca.pringle.maze.TestData.MAZE_6X6_S1;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

final class PathFinderTest {

    @Test
    void findLongestPathShouldWork2x2s0() {
        assertEquals(MAZE_2X2_S0.longestPath(), new PathFinder().findLongestPath(MAZE_2X2_S0.graph()));
    }

    @Test
    void findLongestPathShouldWork3x4s0() {
        assertEquals(MAZE_3X4_S0.longestPath(), new PathFinder().findLongestPath(MAZE_3X4_S0.graph()));
    }

    @Test
    void findLongestPathShouldWork6x6s0() {
        assertEquals(MAZE_6X6_S0.longestPath(), new PathFinder().findLongestPath(MAZE_6X6_S0.graph()));
    }

    @Test
    void findLongestPathShouldWork6x6s1() {
        assertEquals(MAZE_6X6_S1.longestPath(), new PathFinder().findLongestPath(MAZE_6X6_S1.graph()));
    }

    @Test
    void findSolutionShouldWork2x2s0() {
        assertArrayEquals(MAZE_2X2_S0.solution(), new PathFinder().findSolution(MAZE_2X2_S0.graph(), MAZE_2X2_S0.longestPath().fromNode, MAZE_2X2_S0.longestPath().toNode));
    }

    @Test
    void findSolutionShouldWork3x4s0() {
        assertArrayEquals(MAZE_3X4_S0.solution(), new PathFinder().findSolution(MAZE_3X4_S0.graph(), MAZE_3X4_S0.longestPath().fromNode, MAZE_3X4_S0.longestPath().toNode));
    }

    @Test
    void findSolutionShouldWork6x6() {
        assertArrayEquals(MAZE_6X6_S0.solution(), new PathFinder().findSolution(MAZE_6X6_S0.graph(), MAZE_6X6_S0.longestPath().fromNode, MAZE_6X6_S0.longestPath().toNode));
    }

    @Test
    void findSolutionShouldWork6x6Second() {
        assertArrayEquals(MAZE_6X6_S1.solution(), new PathFinder().findSolution(MAZE_6X6_S1.graph(), MAZE_6X6_S1.longestPath().fromNode, MAZE_6X6_S1.longestPath().toNode));
    }
}
