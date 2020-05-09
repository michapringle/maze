package ca.pringle.maze.util;

import java.util.HashMap;
import java.util.TreeMap;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class ListBackedMapTest {

    @Test
    void constructorShouldValidateInput() {
        assertThrows(NullPointerException.class, () -> new ListBackedMap<>(null));
        assertDoesNotThrow(() -> new ListBackedMap<>(new HashMap<>()));
        assertDoesNotThrow(() -> new ListBackedMap<>(new TreeMap<>()));
        assertDoesNotThrow(() -> new ListBackedMap<>());
    }

    @Test
    void putShouldAddNullKeyAndNullValue() {
        final ListBackedMap<?, ?> sut = new ListBackedMap<>();
        assertDoesNotThrow(() -> sut.put(null, null));
        assertEquals(1, sut.size());
        assertEquals(1, sut.get(null).size());
    }

    @Test
    void putShouldAddNullKey() {
        final ListBackedMap<?, String> sut = new ListBackedMap<>();
        assertDoesNotThrow(() -> sut.put(null, "a"));
        assertEquals(1, sut.size());
        assertEquals(1, sut.get(null).size());
    }

    @Test
    void putShouldAddNullValue() {
        final ListBackedMap<String, ?> sut = new ListBackedMap<>();
        assertDoesNotThrow(() -> sut.put("1", null));
        assertEquals(1, sut.size());
        assertEquals(1, sut.get("1").size());
    }

    @Test
    void putShouldAdd() {
        final ListBackedMap<String, String> sut = new ListBackedMap<>();
        assertDoesNotThrow(() -> sut.put("1", "a"));
        assertDoesNotThrow(() -> sut.put("1", "b"));
        assertEquals(1, sut.size());
        assertEquals(2, sut.get("1").size());
    }

    @Test
    void putShouldAddInOrder() {
        final ListBackedMap<String, String> sut = new ListBackedMap<>();
        assertDoesNotThrow(() -> sut.put("1", "a"));
        assertDoesNotThrow(() -> sut.put("1", "b"));
        assertDoesNotThrow(() -> sut.put("1", "c"));
        assertEquals(1, sut.size());
        assertEquals(3, sut.get("1").size());
        assertEquals("a", sut.get("1").get(0));
        assertEquals("b", sut.get("1").get(1));
        assertEquals("c", sut.get("1").get(2));
    }

    @Test
    void removeShouldNotConfuseKeyAndValue() {
        final ListBackedMap<String, Integer> sut = new ListBackedMap<>();
        sut.put("1", 2);
        sut.put("1", 1);
        sut.put("1", 0);

        // ask to remove the value  2, not element 2 in the list
        sut.remove("1", 2);
        assertEquals(2, sut.get("1").size());
        assertEquals(1, sut.get("1").get(0));
        assertEquals(0, sut.get("1").get(1));
    }

    @Test
    void removeShouldDoNothingWhenKeyNotPresent() {
        final ListBackedMap<String, Integer> sut = new ListBackedMap<>();
        sut.put("1", 2);
        sut.put("1", 1);
        sut.put("1", 0);

        // ask to remove the value  2, not element 2 in the list
        sut.remove(null, 2);
        sut.remove("2", 2);
        assertEquals(3, sut.get("1").size());
    }
}
