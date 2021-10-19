package ca.pringle.maze.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class DisjointSetTest {

    @Test
    void constructorShouldValidateInput() {
        assertThrows(IllegalArgumentException.class, () -> new DisjointSet<Integer>(0));
        assertDoesNotThrow(() -> new DisjointSet<Integer>(1));
    }

    @Test
    void getCombinedSubsetsShouldReturnNoValuesWhenNoneAdded() {
        final DisjointSet<String> sut = new DisjointSet<>(5);
        assertEquals(newList(), sut.getCombinedSubsets());
    }

    @Test
    void getCombinedSubsetsShouldReturnValuesWhenAdded1() {
        final DisjointSet<String> sut = new DisjointSet<>(5);
        sut.merge(0, 1, "a");
        sut.merge(2, 3, "b");

        assertEquals(newList("a", "b"), sut.getCombinedSubsets());
    }

    @Test
    void getCombinedSubsetsShouldReturnValuesWhenAdded2() {
        final DisjointSet<String> sut = new DisjointSet<>(5);
        sut.merge(0, 1, "a");
        sut.merge(2, 3, "b");
        sut.merge(0, 4, "c");
        sut.merge(3, 1, "d");

        assertEquals(newList("a", "c", "b", "d"), sut.getCombinedSubsets());
    }

    @Test
    void getNumsetsShouldReturnCorrectSets1() {
        final DisjointSet<String> sut = new DisjointSet<>(5);

        assertEquals(5, sut.getNumSets());
    }

    @Test
    void getNumsetsShouldReturnCorrectSets2() {
        final DisjointSet<String> sut = new DisjointSet<>(5);
        sut.merge(0, 1, "a");
        sut.merge(2, 3, "b");
        sut.merge(0, 4, "c");
        sut.merge(3, 1, "d");

        assertEquals(1, sut.getNumSets());
    }

    @Test
    void mergeShouldMergeSets() {
        final DisjointSet<String> sut = new DisjointSet<>(5);
        sut.merge(0, 1, "a");
        sut.merge(2, 3, "b");
        sut.merge(0, 4, "c");
        sut.merge(3, 1, "d");

        assertEquals(1, sut.getNumSets());
    }

    @Test
    void mergeShouldRequirePathCompression() {
        final DisjointSet<String> sut = new DisjointSet<>(13);
        sut.merge(1, 2, "a");
        sut.merge(3, 4, "b");
        sut.merge(3, 5, "c");
        sut.merge(1, 7, "d");
        sut.merge(3, 12, "e");
        sut.merge(0, 9, "f");
        sut.merge(8, 10, "g");
        sut.merge(8, 9, "h");
        sut.merge(7, 4, "i");
        sut.merge(2, 9, "j");

        assertEquals(3, sut.getNumSets());
    }

    @Test
    void mergeShouldThrowExceptionWhenKeyTooLarge() {
        final DisjointSet<String> sut = new DisjointSet<>(5);
        assertThrows(IndexOutOfBoundsException.class, () -> sut.merge(0, 5, "a"));
    }

    @Test
    void mergeShouldThrowExceptionWhenKeyTooSmall() {
        final DisjointSet<String> sut = new DisjointSet<>(5);
        assertThrows(IndexOutOfBoundsException.class, () -> sut.merge(-1, 5, "a"));
    }

    @Test
    void mergeShouldNotMergeSameSetMoreThanOnce() {
        final DisjointSet<String> sut = new DisjointSet<>(5);
        sut.merge(0, 4, "a");
        sut.merge(0, 4, "b");

        assertEquals(newList("a"), sut.getCombinedSubsets());
    }

    @SafeVarargs
    private static <T> List<T> newList(final T... elements) {
        return Arrays
                .stream(elements)
                .collect(Collectors.toList());
    }
}
