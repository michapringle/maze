package ca.pringle.maze.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    void gettersShouldWork() {

        final MazeConfig sut = new MazeConfig(2, 4, 6L);
        assertEquals(2, sut.getRows());
        assertEquals(4, sut.getColumns());
        assertEquals(8, sut.getRowsTimesColumns());
        assertEquals(6L, sut.getSeed());
        assertNotNull(sut.getNewSeededRandom());
    }

    @Test
    void timersShouldWork() throws InterruptedException {

        final MazeConfig sut = new MazeConfig(2, 4, 6L);
        final MazeConfig.Timer mg = sut.getMazeGenerationTimer();
        final MazeConfig.Timer pg = sut.getPathGenerationTimer();
        final MazeConfig.Timer sg = sut.getSolutionGenerationTimer();

        mg.start();
        Thread.sleep(5L);
        pg.start();
        Thread.sleep(5L);
        sg.start();
        Thread.sleep(5L);
        sg.stop();
        pg.stop();
        mg.stop();

        assertTrue(mg.getElapsedTimeInMillis() > pg.getElapsedTimeInMillis());
        assertTrue(pg.getElapsedTimeInMillis() > sg.getElapsedTimeInMillis());
        assertTrue(sg.getElapsedTimeInMillis() > 0L);
    }
}
