package slowraytracer;

import java.util.Objects;

public final class Vector3 {

    public static final Vector3 ZERO = new Vector3(0, 0, 0);
    private final float x;
    private final float y;
    private final float z;

    public Vector3(final float x, final float y, final float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3 multiply(final float constant) {
        return new Vector3(x * constant, y * constant, z * constant);
    }

    public Vector3 add(final Vector3 other) {
        return new Vector3(x + other.x, y + other.y, z + other.z);
    }

    public Vector3 subtract(final Vector3 other) {
        return new Vector3(x - other.x, y - other.y, z - other.z);
    }

    public float dotProduct(final Vector3 other) {
        return x * other.x + y * other.y + z * other.z;
    }

    public float norm() {
        return (float) Math.sqrt(dotProduct(this));
    }

    public Vector3 normalize() {
        return multiply(1 / norm());
    }

    public float x() {
        return x;
    }

    public float y() {
        return y;
    }

    public float z() {
        return z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        } else if (other != null && getClass() == other.getClass()) {
            final var otherVector = (Vector3) other;
            return Float.compare(x, otherVector.x) == 0
                    && Float.compare(y, otherVector.y) == 0
                    && Float.compare(z, otherVector.z) == 0;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format("(%f, %f, %f)", x, y, z);
    }
}
