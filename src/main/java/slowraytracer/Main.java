package slowraytracer;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.imageio.ImageIO;

public final class Main {

    private static final double VERTICAL_FIELD_OF_VIEW_RADIANS = Math.toRadians(90);
    private static final Random RANDOM = new Random();

    public static void main(final String[] arguments) throws IOException {
        final var imageWidth = 800;
        final var imageHeight = 600;
        final var pixmap = new Pixmap(imageWidth, imageHeight);
        pixmap.fill((x, y) -> Color.LIGHT_GRAY.getRGB());
        renderSpheres(pixmap, Stream.generate(Main::generateSphere).limit(5).collect(Collectors.toList()));
        ImageIO.write(pixmap.asBufferedImage(), "png", new File("out.png"));
    }

    private static Sphere generateSphere() {
        final var radius = 0.1f + RANDOM.nextFloat() * 4;
        final var halfRadius = radius / 2;
        final var left = RANDOM.nextBoolean();
        final var down = RANDOM.nextBoolean();
        final var x = (left ? -RANDOM.nextFloat() : RANDOM.nextFloat()) * 3 * halfRadius;
        final var y = (down ? -RANDOM.nextFloat() : RANDOM.nextFloat()) * 3 * halfRadius;
        final var z = -5.1f + halfRadius;
        final var diffuseColor = new Color(RANDOM.nextFloat(), RANDOM.nextFloat(), RANDOM.nextFloat());
        return new Sphere(new Vector3(x, y, z), radius, new Material(diffuseColor.getRGB()));
    }

    private static void renderSpheres(final Pixmap pixmap, final Iterable<Sphere> spheres) {
        final var width = pixmap.width();
        final var halfWidth = width / 2;
        final var height = pixmap.height();
        final var halfHeight = height / 2;
        final var directionZ = -halfHeight / ((float) Math.tan(VERTICAL_FIELD_OF_VIEW_RADIANS / 2));
        for (var y = 0; y < height; y++) {
            for (var x = 0; x < width; x++) {
                final var directionX = (x + 0.5f) - halfWidth;
                final var directionY = -(y + 0.5f) + halfHeight;
                final var viewDirection = new Vector3(directionX, directionY, directionZ).normalize();
                final var colorOptional = castRay(new Ray(Vector3.ZERO, viewDirection), spheres)
                        .map(intersection -> intersection.material().diffuseColor());
                if (colorOptional.isPresent()) {
                    pixmap.set(x, y, colorOptional.get());
                }
            }
        }
    }

    private static Optional<Sphere.RayIntersection> castRay(final Ray ray, final Iterable<Sphere> spheres) {
        var closestIntersection = Optional.<Sphere.RayIntersection>empty();
        for (final Sphere sphere : spheres) {
            final var distanceLimit = closestIntersection.map(Sphere.RayIntersection::distance).orElse(Float.MAX_VALUE);
            final var intersectionOptional = sphere.intersections(ray)
                    .stream()
                    .filter(intersection -> intersection.distance() < distanceLimit)
                    .findFirst();
            if (intersectionOptional.isPresent()) {
                closestIntersection = intersectionOptional;
            }
        }
        return closestIntersection;
    }
}
