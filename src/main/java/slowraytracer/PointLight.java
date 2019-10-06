package slowraytracer;

import java.util.Objects;

public final class PointLight {

    private final Vector3 position;
    private final float intensity;

    public PointLight(final Vector3 position, final float intensity) {
        this.position = Objects.requireNonNull(position);
        this.intensity = Validations.requirePositive(intensity, "intensity");
    }

    public Vector3 position() {
        return position;
    }

    public float intensity() {
        return intensity;
    }
}
