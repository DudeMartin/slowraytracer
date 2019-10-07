package slowraytracer;

final class LightingCalculations {

    private LightingCalculations() {
    }

    static float diffuse(final Vector3 lightDirection, final Vector3 normal) {
        return Math.max(0, lightDirection.dotProduct(normal));
    }

    static float specular(
            final Vector3 lightDirection,
            final Vector3 normal,
            final Vector3 viewDirection,
            final float shininess) {
        final var reflection = reflect(lightDirection.negate(), normal).negate();
        return (float) Math.pow(Math.max(0, reflection.dotProduct(viewDirection)), shininess);
    }

    private static Vector3 reflect(final Vector3 lightDirection, final Vector3 normal) {
        return lightDirection.subtract(normal.multiply(2 * lightDirection.dotProduct(normal)));
    }
}
