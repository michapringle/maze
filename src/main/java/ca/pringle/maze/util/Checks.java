package ca.pringle.maze.util;

import java.util.Arrays;
import java.util.List;

public final class Checks<T> {

    private final T reference;

    private Checks(final T reference) {

        this.reference = reference;
    }

    public static <T> Checks<T> check(final T reference) {

        return new Checks<>(reference);
    }

    public T isNotNull() {

        return isNotNullAnd().get();
    }

    public T isNotNull(final String errorMessageTemplate,
                       final Object... errorMessageArgs) {

        return isNotNullAnd(errorMessageTemplate, errorMessageArgs).get();
    }

    public Checks<T> isNotNullAnd() {

        return isNotNullAnd("reference must not be null.");
    }

    public Checks<T> isNotNullAnd(final String errorMessageTemplate,
                                  final Object... errorMessageArgs) {

        if (reference != null) {
            return this;
        }

        throw new NullPointerException(String.format(errorMessageTemplate, errorMessageArgs));
    }

    public T isTrue(final boolean condition,
                    final String errorMessageTemplate,
                    final Object... errorMessageArgs) {

        return isTrueAnd(condition, errorMessageTemplate, errorMessageArgs).get();
    }


    public Checks<T> isTrueAnd(final boolean expression,
                               final String errorMessageTemplate,
                               final Object... errorMessageArgs) {

        if (expression) {
            return this;
        }

        throw new IllegalArgumentException(String.format(errorMessageTemplate, errorMessageArgs));
    }


    public T isGreaterThan(final long number,
                           final String errorMessageTemplate,
                           final Object... errorMessageArgs) {

        return isGreaterThanAnd(number, errorMessageTemplate, errorMessageArgs).get();
    }


    public Checks<T> isGreaterThanAnd(final long number,
                                      final String errorMessageTemplate,
                                      final Object... errorMessageArgs) {

        if (reference instanceof Long && (long) reference > number) {
            return this;
        }

        if (reference instanceof Integer && (int) reference > number) {
            return this;
        }

        throw new IllegalArgumentException(String.format(errorMessageTemplate, errorMessageArgs));
    }


    public T isAnyOf(final List<T> validValues) {

        return isAnyOfAnd(validValues).get();
    }

    public T isAnyOf(final List<T> validValues,
                     final String errorMessageTemplate,
                     final Object... errorMessageArgs) {

        return isAnyOfAnd(validValues, errorMessageTemplate, errorMessageArgs).get();
    }


    public Checks<T> isAnyOfAnd(final List<T> validValues) {

        return isAnyOfAnd(validValues, "Invalid value: %s, accepted values: %s", reference, Arrays.toString(validValues.toArray()));
    }


    public Checks<T> isAnyOfAnd(final List<T> validValues,
                                final String errorMessageTemplate,
                                final Object... errorMessageArgs) {

        if (validValues.contains(reference)) {
            return this;
        }

        throw new IllegalArgumentException(String.format(errorMessageTemplate, errorMessageArgs));
    }

    T get() {

        return reference;
    }
}