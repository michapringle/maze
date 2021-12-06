package ca.pringle.maze.logic;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class PathTest {

    @Test
    void constructorShouldWork() {
        assertThrows(IllegalArgumentException.class, () -> new Path(-1, 0));
        assertThrows(IllegalArgumentException.class, () -> new Path(0, -1));
        assertDoesNotThrow(() -> new Path(0, 0));
    }

    @Test
    void equalsAndHashcodeShouldObeyContract() {
        EqualsVerifier
                .forClass(Path.class)
                .verify();
    }

    @Test
    void toStringShouldHaveCustomImplementation() {

        final Path sut = new Path(1, 2);
        assertFalse(sut.toString().contains(Integer.toHexString(sut.hashCode())));
    }
}
