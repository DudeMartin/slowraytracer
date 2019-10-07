package slowraytracer;

public final class Material {

    private final int ambientColor;
    private final float ambientIntensity;
    private final int diffuseColor;
    private final float diffuseIntensity;
    private final int specularColor;
    private final float specularIntensity;
    private final float shininess;

    public Material(
            final int ambientColor,
            final float ambientIntensity,
            final int diffuseColor,
            final float diffuseIntensity,
            final int specularColor,
            final float specularIntensity,
            final float shininess)  {
        this.ambientColor = ambientColor;
        this.ambientIntensity = Validations.requireNonNegative(ambientIntensity, "ambient intensity");
        this.diffuseColor = diffuseColor;
        this.diffuseIntensity = Validations.requireNonNegative(diffuseIntensity, "diffuse intensity");
        this.specularColor = specularColor;
        this.specularIntensity = Validations.requireNonNegative(specularIntensity, "specular intensity");
        this.shininess = Validations.requireNonNegative(shininess, "shininess");
    }

    public int ambientColor() {
        return ambientColor;
    }

    public float ambientIntensity() {
        return ambientIntensity;
    }

    public int diffuseColor() {
        return diffuseColor;
    }

    public float diffuseIntensity() {
        return diffuseIntensity;
    }

    public int specularColor() {
        return specularColor;
    }

    public float specularIntensity() {
        return specularIntensity;
    }

    public float shininess() {
        return shininess;
    }
}
