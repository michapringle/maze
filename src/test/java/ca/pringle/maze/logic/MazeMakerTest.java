package ca.pringle.maze.logic;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static ca.pringle.maze.TestData.MAZE_2X2_S0;
import static ca.pringle.maze.TestData.MAZE_3X4_S0;
import static ca.pringle.maze.TestData.MAZE_6X6_S0;
import static ca.pringle.maze.TestData.MAZE_6X6_S1;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class MazeMakerTest {

    @Test
    void getAdjacentShouldReturnNodesToTheLeftAndBelow() {
        final MazeConfig mazeConfig = new MazeConfig(4, 3, 0);
        final MazeMaker sut = new MazeMaker(mazeConfig);

        assertArrayEquals(new int[] { 3, 1 }, sut.getAdjacent(0));
        assertArrayEquals(new int[] { 4, 2 }, sut.getAdjacent(1));
        assertArrayEquals(new int[] { 5 }, sut.getAdjacent(2));
        assertArrayEquals(new int[] { 6, 4 }, sut.getAdjacent(3));
        assertArrayEquals(new int[] { 7, 5 }, sut.getAdjacent(4));
        assertArrayEquals(new int[] { 8 }, sut.getAdjacent(5));
        assertArrayEquals(new int[] { 9, 7 }, sut.getAdjacent(6));
        assertArrayEquals(new int[] { 10, 8 }, sut.getAdjacent(7));
        assertArrayEquals(new int[] { 11 }, sut.getAdjacent(8));
        assertArrayEquals(new int[] { 10 }, sut.getAdjacent(9));
        assertArrayEquals(new int[] { 11 }, sut.getAdjacent(10));
        assertArrayEquals(new int[] {}, sut.getAdjacent(11));
    }

    @Test
    void initializeMazeShouldWork() {
        final MazeConfig mazeConfig = new MazeConfig(4, 3, 0);
        final MazeMaker sut = new MazeMaker(mazeConfig);

        final Edge[] actual = sut.initializeMaze();

        assertEquals(17, actual.length);
        assertEquals(new Edge(0, 3), actual[0]);
        assertEquals(new Edge(0, 1), actual[1]);
        assertEquals(new Edge(1, 4), actual[2]);
        assertEquals(new Edge(1, 2), actual[3]);
        assertEquals(new Edge(2, 5), actual[4]);
        assertEquals(new Edge(3, 6), actual[5]);
        assertEquals(new Edge(3, 4), actual[6]);
        assertEquals(new Edge(4, 7), actual[7]);
        assertEquals(new Edge(4, 5), actual[8]);
        assertEquals(new Edge(5, 8), actual[9]);
        assertEquals(new Edge(6, 9), actual[10]);
        assertEquals(new Edge(6, 7), actual[11]);
        assertEquals(new Edge(7, 10), actual[12]);
        assertEquals(new Edge(7, 8), actual[13]);
        assertEquals(new Edge(8, 11), actual[14]);
        assertEquals(new Edge(9, 10), actual[15]);
        assertEquals(new Edge(10, 11), actual[16]);
    }

    @Test
    void randomizeEdgesShouldRandomizeAndNotAlterEdges() {
        final MazeConfig mazeConfig = new MazeConfig(4, 3, 0);
        final MazeMaker sut = new MazeMaker(mazeConfig);

        final Edge[] edges = sut.initializeMaze();
        final Edge[] actual = edges.clone();

        sut.randomizeEdges(actual);

        // verify the edges are not altered
        assertEquals(17, edges.length);
        assertEquals(17, actual.length);

        final List<Edge> edgesList = Arrays.asList(edges);
        final List<Edge> actualList = Arrays.asList(actual);

        edgesList.forEach(point -> assertTrue(actualList.contains(point)));
        actualList.forEach(point -> assertTrue(edgesList.contains(point)));
        // verify the edges are randomized
        assertNotEquals(edgesList, actualList);
    }

    @Test
    void generateDagShouldCreateValidDag() {

        {
            final SpecializedGraph actual = new MazeMaker(MAZE_2X2_S0.config()).generateDag();
            assertEquals(MAZE_2X2_S0.graph(), actual);
        }
        {
            final SpecializedGraph actual = new MazeMaker(MAZE_3X4_S0.config()).generateDag();
            assertEquals(MAZE_3X4_S0.graph(), actual);
        }
        {
            final SpecializedGraph actual = new MazeMaker(MAZE_6X6_S0.config()).generateDag();
            assertEquals(MAZE_6X6_S0.graph(), actual);
        }
        {
            final SpecializedGraph actual = new MazeMaker(MAZE_6X6_S1.config()).generateDag();
            assertEquals(MAZE_6X6_S1.graph(), actual);
        }
    }
}
