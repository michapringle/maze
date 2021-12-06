package ca.pringle.maze.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class SpecializedStackTest {

    @Test
    void constructorShouldWork() {

        assertThrows(IllegalArgumentException.class, () -> new SpecializedStack<Integer>(-1));
        assertThrows(IllegalArgumentException.class, () -> new SpecializedStack<Integer>(0));
        assertDoesNotThrow(() -> new SpecializedStack<Integer>(1));
    }

    @Test
    void stackShouldWork() {

        for (int i = 1; i <= 5; i++) {
            final SpecializedStack<Integer> sut = new SpecializedStack<>(i);
            test(sut);
        }
    }

    private void test(final SpecializedStack<Integer> sut) {

        assertEquals(0, sut.size());
        assertArrayEquals(new Integer[]{}, sut.toArray());
        assertTrue(sut.isEmpty());
        assertArrayEquals(new Integer[]{}, sut.toArray());

        sut.push(10);
        assertEquals(1, sut.size());
        assertFalse(sut.isEmpty());
        assertEquals(10, sut.peek());
        assertArrayEquals(new Integer[]{10}, sut.toArray());

        sut.push(11);
        assertEquals(2, sut.size());
        assertFalse(sut.isEmpty());
        assertEquals(11, sut.peek());
        assertArrayEquals(new Integer[]{10, 11}, sut.toArray());

        sut.push(12);
        assertEquals(3, sut.size());
        assertFalse(sut.isEmpty());
        assertEquals(12, sut.peek());
        assertArrayEquals(new Integer[]{10, 11, 12}, sut.toArray());

        sut.push(13);
        assertEquals(4, sut.size());
        assertFalse(sut.isEmpty());
        assertEquals(13, sut.peek());
        assertArrayEquals(new Integer[]{10, 11, 12, 13}, sut.toArray());


        assertEquals(13, sut.pop());
        assertEquals(3, sut.size());
        assertFalse(sut.isEmpty());
        assertArrayEquals(new Integer[]{10, 11, 12}, sut.toArray());

        assertEquals(12, sut.pop());
        assertEquals(2, sut.size());
        assertFalse(sut.isEmpty());
        assertArrayEquals(new Integer[]{10, 11}, sut.toArray());

        assertEquals(11, sut.pop());
        assertEquals(1, sut.size());
        assertFalse(sut.isEmpty());
        assertArrayEquals(new Integer[]{10}, sut.toArray());

        assertEquals(10, sut.pop());
        assertEquals(0, sut.size());
        assertTrue(sut.isEmpty());
        assertArrayEquals(new Integer[]{}, sut.toArray());
    }
}
