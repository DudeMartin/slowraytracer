package slowraytracer;

import org.immutables.value.Value;

@Value.Immutable
public abstract class Material {

    private static final int WHITE_ARGB = ColorUtilities.toOpaqueArgb(255, 255, 255);

    public abstract int ambientColor();

    public abstract float ambientIntensity();

    @Value.Default
    public int diffuseColor() {
        return ambientColor();
    }

    @Value.Default
    public float diffuseIntensity() {
        return 0;
    }

    @Value.Default
    public int specularColor() {
        return WHITE_ARGB;
    }

    @Value.Default
    public float specularIntensity() {
        return 0;
    }

    @Value.Default
    public float shininess() {
        return 0;
    }

    @Value.Check
    void checkInputs() {
        Validations.requireNonNegative(ambientIntensity(), "ambient intensity");
        Validations.requireNonNegative(diffuseIntensity(), "diffuse intensity");
        Validations.requireNonNegative(specularIntensity(), "specular intensity");
        Validations.requireNonNegative(shininess(), "shininess");
    }
}
