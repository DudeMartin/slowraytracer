package slowraytracer;

import java.util.Objects;

public final class Sphere {

    private final Vector3 center;
    private final float radius;
    private final Material material;

    public Sphere(final Vector3 center, final float radius, final Material material) {
        this.center = Objects.requireNonNull(center);
        this.radius = Validations.requirePositive(radius, "radius");
        this.material = Objects.requireNonNull(material);
    }

    public boolean intersects(final Ray ray) {
        final var L = center.subtract(ray.endpoint());
        final var tca = L.dotProduct(ray.direction());
        if (tca < 0) {
            // The center of this sphere is behind the ray.
            return false;
        }
        final var d2 = L.dotProduct(L) - tca * tca;
        final var radiusSquared = radius * radius;
        if (d2 > radiusSquared) {
            // The distance between the center of this sphere and its projection on the ray is greater than the radius
            // so the ray does not intersect.
            return false;
        }
        final var thc = Math.sqrt(radiusSquared - d2);
        final var t0 = tca - thc;
        if (t0 >= 0) {
            return true;
        }
        final var t1 = tca + thc;
        return t1 >= 0;
    }

    public Material material() {
        return material;
    }
}
