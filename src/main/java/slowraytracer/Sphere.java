package slowraytracer;

import java.util.List;
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

    public List<RayIntersection> intersections(final Ray ray) {
        final var L = center.subtract(ray.endpoint());
        final var tca = L.dotProduct(ray.direction());
        if (tca < 0) {
            // The center of this sphere is behind the ray.
            return List.of();
        }
        final var d2 = L.dotProduct(L) - tca * tca;
        final var radiusSquared = radius * radius;
        if (d2 > radiusSquared) {
            // The distance between the center of this sphere and its projection on the ray is greater than the radius
            // so the ray does not intersect.
            return List.of();
        }
        final var thc = (float) Math.sqrt(radiusSquared - d2);
        final var t0 = tca - thc;
        final var t1 = tca + thc;
        if (t0 >= 0) {
            if (t1 >= 0) {
                return List.of(new RayIntersection(ray, t0), new RayIntersection(ray, t1));
            }
            return List.of(new RayIntersection(ray, t0));
        }
        return List.of();
    }

    public final class RayIntersection {

        private final float distance;
        private final Vector3 position;
        private final Vector3 normal;

        private RayIntersection(final Ray ray, final float distance) {
            this.distance = distance;
            position = ray.endpoint().add(ray.direction().multiply(distance));
            normal = position.subtract(center).normalize();
        }

        public float distance() {
            return distance;
        }

        public Vector3 position() {
            return position;
        }

        public Vector3 normal() {
            return normal;
        }

        public Material material() {
            return material;
        }
    }
}
