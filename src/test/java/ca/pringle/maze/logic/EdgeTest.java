package ca.pringle.maze.logic;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class EdgeTest {

    @Test
    void constructorShouldWork() {
        assertThrows(IllegalArgumentException.class, () -> new Edge(-1, 0));
        assertThrows(IllegalArgumentException.class, () -> new Edge(0, -1));
        assertDoesNotThrow(() -> new Edge(0, 0));
    }

    @Test
    void equalsAndHashcodeShouldObeyContract() {
        EqualsVerifier
                .forClass(Edge.class)
                .verify();
    }
}
