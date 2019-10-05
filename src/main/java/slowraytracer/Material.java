package slowraytracer;

public final class Material {

    private final int diffuseColor;

    public Material(final int diffuseColor) {
        this.diffuseColor = diffuseColor;
    }

    public int diffuseColor() {
        return diffuseColor;
    }
}
