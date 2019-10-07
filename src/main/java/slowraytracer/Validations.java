package slowraytracer;

import java.util.Objects;

final class Validations {

    private Validations() {
    }

    static int requirePositive(final int value, final String valueName) {
        assertCondition(value > 0, valueName, "positive");
        return value;
    }

    static float requirePositive(final float value, final String valueName) {
        assertCondition(value > 0, valueName, "positive");
        return value;
    }

    static float requireNonNegative(final float value, final String valueName) {
        assertCondition(value >= 0, valueName, "non-negative");
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

    private static void assertCondition(final boolean condition, final String valueName, final String conditionName) {
        Objects.requireNonNull(valueName);
        Objects.requireNonNull(conditionName);
        if (!condition) {
            throw new IllegalArgumentException(String.format("The %s must be %s.", valueName, conditionName));
        }
    }
}
