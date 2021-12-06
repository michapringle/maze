package ca.pringle.maze.logic;

import org.junit.jupiter.api.Test;

import static ca.pringle.maze.TestData.MAZE_2X2_S0;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class DisjointEdgeSetTest {

    @Test
    void constructorShouldWork() {

        assertThrows(IllegalArgumentException.class, () -> new DisjointEdgeSet(-1));
        assertThrows(IllegalArgumentException.class, () -> new DisjointEdgeSet(0));
        assertDoesNotThrow(() -> new DisjointEdgeSet(1));
    }

    @Test
    void mergeShouldDelegate() {

        final DisjointEdgeSet sut = new DisjointEdgeSet(5);
        sut.merge(0, 1, new Edge(1, 2));
        sut.merge(2, 3, new Edge(1, 2));
        sut.merge(0, 4, new Edge(1, 2));
        sut.merge(3, 1, new Edge(1, 2));

        assertEquals(1, sut.getNumSets());
    }

    @Test
    void getEdgesAsDagShouldReturnDag() {

        final DisjointEdgeSet sut = new DisjointEdgeSet(4);
        sut.merge(0, 2, new Edge(0, 2));
        sut.merge(0, 1, new Edge(0, 1));
        sut.merge(2, 3, new Edge(2, 3));

        final SpecializedGraph actual = sut.getEdgesAsDag();

        assertEquals(MAZE_2X2_S0.graph(), actual);
    }

    @Test
    void getNumSetsShouldReturnCorrectSets() {

        final DisjointEdgeSet sut = new DisjointEdgeSet(5);
        sut.merge(0, 1, new Edge(0, 1));
        sut.merge(2, 3, new Edge(2, 3));
        sut.merge(0, 4, new Edge(0, 4));
        sut.merge(3, 1, new Edge(3, 1));

        assertEquals(1, sut.getNumSets());
    }

    @Test
    void mergeShouldMergeSets() {

        final DisjointEdgeSet sut = new DisjointEdgeSet(5);
        sut.merge(0, 1, new Edge(0, 1));
        sut.merge(2, 3, new Edge(2, 3));
        sut.merge(0, 4, new Edge(0, 4));
        sut.merge(3, 1, new Edge(3, 1));

        assertEquals(1, sut.getNumSets());
    }

    @Test
    void mergeShouldRequirePathCompression() {

        final DisjointEdgeSet sut = new DisjointEdgeSet(13);
        sut.merge(1, 2, new Edge(0, 1));
        sut.merge(3, 4, new Edge(0, 1));
        sut.merge(3, 5, new Edge(0, 1));
        sut.merge(1, 7, new Edge(0, 1));
        sut.merge(3, 12, new Edge(0, 1));
        sut.merge(0, 9, new Edge(0, 1));
        sut.merge(8, 10, new Edge(0, 1));
        sut.merge(8, 9, new Edge(0, 1));
        sut.merge(7, 4, new Edge(0, 1));
        sut.merge(2, 9, new Edge(0, 1));

        assertEquals(3, sut.getNumSets());
    }

    @Test
    void mergeShouldThrowExceptionWhenKeyTooLarge() {

        final DisjointEdgeSet sut = new DisjointEdgeSet(5);
        assertThrows(IndexOutOfBoundsException.class, () -> sut.merge(0, 5, new Edge(0, 1)));
    }

    @Test
    void mergeShouldThrowExceptionWhenKeyTooSmall() {

        final DisjointEdgeSet sut = new DisjointEdgeSet(5);
        assertThrows(IndexOutOfBoundsException.class, () -> sut.merge(-1, 5, new Edge(0, 1)));
    }
}
