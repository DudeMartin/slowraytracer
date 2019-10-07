package slowraytracer;

final class LightingCalculations {

    private LightingCalculations() {
    }

    static float diffuse(final Vector3 lightDirection, final Vector3 normal) {
        return Math.max(0, lightDirection.dotProduct(normal));
    }
}
