package ca.pringle.maze.util;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

final class ChecksTest {

    @Test
    void isNotNullShouldThrowExceptionWhenReferenceNameIsNull() {

        assertThatThrownBy(() -> Checks.check(null).isNotNull())
                .isInstanceOf(NullPointerException.class)
                .hasMessage("reference must not be null.");

        assertThatThrownBy(() -> Checks.check(null).isNotNull("some message %s", "bob"))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("some message bob");

        assertThatThrownBy(() -> Checks.check(null).isNotNullAnd())
                .isInstanceOf(NullPointerException.class)
                .hasMessage("reference must not be null.");

        assertThatThrownBy(() -> Checks.check(null).isNotNullAnd("some message %s", "bob"))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("some message bob");
    }

    @Test
    void isNotNullShouldNotThrowWhenReferenceIsNotNull() {

        assertDoesNotThrow(() -> Checks.check("foo").isNotNull());
        assertDoesNotThrow(() -> Checks.check("foo").isNotNull("some message {}", "bob"));
        assertDoesNotThrow(() -> Checks.check("foo").isNotNullAnd().get());
        assertDoesNotThrow(() -> Checks.check("foo").isNotNullAnd("some message {}", "bob").get());
    }

    @Test
    void isTrueShouldThrowExceptionWhenConditionIFalse() {

        assertThatThrownBy(() -> Checks.check("foo").isTrue(false, "some message %s", "bob"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("some message bob");

        assertThatThrownBy(() -> Checks.check("foo").isTrueAnd(false, "some message %s", "bob"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("some message bob");
    }

    @Test
    void isTrueShouldNotThrowWhenConditionIsTrue() {

        assertDoesNotThrow(() -> Checks.check("foo").isTrue(true, "some message {}", "bob"));
        assertDoesNotThrow(() -> Checks.check("foo").isTrueAnd(true, "some message {}", "bob").get());
    }

    @Test
    void isGreaterThanShouldThrowExceptionWhenConditionIFalse() {

        assertThatThrownBy(() -> Checks.check(0).isGreaterThan(5, "some message %s", "bob"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("some message bob");

        assertThatThrownBy(() -> Checks.check(0).isGreaterThan(5L, "some message %s", "bob"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("some message bob");

        assertThatThrownBy(() -> Checks.check(0L).isGreaterThan(5, "some message %s", "bob"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("some message bob");

        assertThatThrownBy(() -> Checks.check(0L).isGreaterThan(5L, "some message %s", "bob"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("some message bob");

        assertThatThrownBy(() -> Checks.check(0).isGreaterThanAnd(5, "some message %s", "bob"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("some message bob");

        assertThatThrownBy(() -> Checks.check(0).isGreaterThanAnd(5L, "some message %s", "bob"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("some message bob");

        assertThatThrownBy(() -> Checks.check(0L).isGreaterThanAnd(5, "some message %s", "bob"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("some message bob");

        assertThatThrownBy(() -> Checks.check(0L).isGreaterThanAnd(5L, "some message %s", "bob"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("some message bob");
    }

    @Test
    void isGreaterThanShouldNotThrowWhenConditionIsTrue() {

        assertDoesNotThrow(() -> Checks.check(5).isGreaterThan(0, "some message %s", "bob"));
        assertDoesNotThrow(() -> Checks.check(5).isGreaterThan(0L, "some message %s", "bob"));
        assertDoesNotThrow(() -> Checks.check(5L).isGreaterThan(0, "some message %s", "bob"));
        assertDoesNotThrow(() -> Checks.check(5L).isGreaterThan(0L, "some message %s", "bob"));

        assertDoesNotThrow(() -> Checks.check(5).isGreaterThanAnd(0, "some message %s", "bob"));
        assertDoesNotThrow(() -> Checks.check(5).isGreaterThanAnd(0L, "some message %s", "bob"));
        assertDoesNotThrow(() -> Checks.check(5L).isGreaterThanAnd(0, "some message %s", "bob"));
        assertDoesNotThrow(() -> Checks.check(5L).isGreaterThanAnd(0L, "some message %s", "bob"));
    }

    @Test
    void isAnyOfShouldThrowExceptionWhenThereIsNoMatch() {

        assertThatThrownBy(() -> Checks.check("d").isAnyOf(Arrays.asList("a", "b", "c")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid value: d, accepted values: [a, b, c]");

        assertThatThrownBy(() -> Checks.check("d").isAnyOf(Arrays.asList("a", "b", "c"), "some message %s", "bob"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("some message bob");

        assertThatThrownBy(() -> Checks.check("d").isAnyOfAnd(Arrays.asList("a", "b", "c")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid value: d, accepted values: [a, b, c]");

        assertThatThrownBy(() -> Checks.check("d").isAnyOfAnd(Arrays.asList("a", "b", "c"), "some message %s", "bob"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("some message bob");
    }

    @Test
    void isAnyOfShouldNotThrowWhenThereIsNoMatch() {

        assertDoesNotThrow(() -> Checks.check("a").isAnyOf(Arrays.asList("a", "b", "c")));
        assertDoesNotThrow(() -> Checks.check("a").isAnyOf(Arrays.asList("a", "b", "c"), "some message %s", "bob"));

        assertDoesNotThrow(() -> Checks.check("a").isAnyOfAnd(Arrays.asList("a", "b", "c")));
        assertDoesNotThrow(() -> Checks.check("a").isAnyOfAnd(Arrays.asList("a", "b", "c"), "some message %s", "bob"));
    }
}