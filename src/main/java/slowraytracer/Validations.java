package slowraytracer;

import java.util.Objects;

final class Validations {

    private Validations() {
    }

    static int requirePositive(final int value, final String valueName) {
        assertPositive(value > 0, valueName);
        return value;
    }

    static float requirePositive(final float value, final String valueName) {
        assertPositive(value > 0, valueName);
        return value;
    }

    static boolean checkRange(final int value, final int startInclusive, final int endExclusive) {
        return value >= startInclusive && value < endExclusive;
    }

    static void requireRange(
            final int value,
            final int startInclusive,
            final int endExclusive,
            final String valueName) {
        Objects.requireNonNull(valueName);
        if (!checkRange(value, startInclusive, endExclusive)) {
            throw new IllegalArgumentException(String.format(
                    "The %s value of %d is not in the range [%d, %d).",
                    valueName,
                    value,
                    startInclusive,
                    endExclusive));
        }
    }

    private static void assertPositive(final boolean condition, final String valueName) {
        Objects.requireNonNull(valueName);
        if (!condition) {
            throw new IllegalArgumentException("The " + valueName + " must be positive.");
        }
    }
}
