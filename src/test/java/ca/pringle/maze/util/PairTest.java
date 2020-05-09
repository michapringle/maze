package ca.pringle.maze.util;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class PairTest {

    @Test
    void PairShouldWork() {
        assertThrows(NullPointerException.class, () -> new Pair<String, String>(null, "left"));
        assertThrows(NullPointerException.class, () -> new Pair<String, String>("left", null));
        assertThrows(NullPointerException.class, () -> new Pair<String, String>(null, null));
        assertDoesNotThrow(() -> new Pair<>("left", "right"));
    }

    @Test
    void equalsAndHashcodeShouldObeyContract() {
        EqualsVerifier
                .forClass(Pair.class)
                .verify();
    }
}
