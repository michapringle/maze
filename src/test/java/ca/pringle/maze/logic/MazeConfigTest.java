package ca.pringle.maze.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class MazeConfigTest {

    @Test
    void MazeConfig1ShouldValidateInput() {
        assertThrows(IllegalArgumentException.class, () -> new MazeConfig(1, 2, 0L));
        assertThrows(IllegalArgumentException.class, () -> new MazeConfig(10_000, 2, 0L));
        assertThrows(IllegalArgumentException.class, () -> new MazeConfig(2, 1, 0L));
        assertThrows(IllegalArgumentException.class, () -> new MazeConfig(2, 10_000, 0L));

        assertDoesNotThrow(() -> new MazeConfig(2, 2, Long.MIN_VALUE));
        assertDoesNotThrow(() -> new MazeConfig(9999, 9999, Long.MAX_VALUE));
    }

    @Test
    void MazeConfig2ShouldValidateInput() {
        assertThrows(IllegalArgumentException.class, () -> new MazeConfig("a", "2", "0"));
        assertThrows(IllegalArgumentException.class, () -> new MazeConfig("1", "2", "0"));
        assertThrows(IllegalArgumentException.class, () -> new MazeConfig("10_000", "2", "0"));
        assertThrows(IllegalArgumentException.class, () -> new MazeConfig("2", "a", "0"));
        assertThrows(IllegalArgumentException.class, () -> new MazeConfig("2", "1", "0"));
        assertThrows(IllegalArgumentException.class, () -> new MazeConfig("2", "10_000", "0"));
        assertThrows(IllegalArgumentException.class, () -> new MazeConfig("2", "2", "a"));

        assertDoesNotThrow(() -> new MazeConfig("2", "2", Long.toString(Long.MIN_VALUE)));
        assertDoesNotThrow(() -> new MazeConfig("9999", "9999", Long.toString(Long.MAX_VALUE)));
    }

    @Test
    void getSaveFileNameShouldWork() {
        assertEquals("2x4_s6.png", new MazeConfig("2", "4", "6").getSaveFileName());
        assertEquals("6x4_s2.png", new MazeConfig("6", "4", "2").getSaveFileName());
    }
}
