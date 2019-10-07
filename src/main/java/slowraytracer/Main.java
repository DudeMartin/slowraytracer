package slowraytracer;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.List;
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
        final var sceneSpheres = Stream.generate(Main::generateSphere).limit(5).collect(Collectors.toList());
        final var sceneLights = List.of(
                new PointLight(Vector3.ZERO, 0.25f),
                new PointLight(new Vector3(-10, 10, 10), 1.1f));
        pixmap.fill((x, y) -> Color.LIGHT_GRAY.getRGB());
        renderScene(pixmap, sceneSpheres, sceneLights);
        System.exit(ImageIO.write(pixmap.asBufferedImage(), "png", new File("out.png")) ? 0 : 1);
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
        return new Sphere(
                new Vector3(x, y, z),
                radius,
                new Material(diffuseColor.getRGB(), 0.1f, diffuseColor.getRGB(), 0.8f, Color.WHITE.getRGB(), 0.5f, 50));
    }

    private static void renderScene(
            final Pixmap pixmap,
            final Iterable<Sphere> spheres,
            final Iterable<PointLight> lights) {
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
                final var viewRay = new Ray(Vector3.ZERO, viewDirection);
                final var intersectionOptional = castRay(viewRay, spheres);
                if (intersectionOptional.isPresent()) {
                    pixmap.set(x, y, computeLighting(viewRay, intersectionOptional.get(), lights));
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

    private static int computeLighting(
            final Ray ray,
            final Sphere.RayIntersection intersection,
            final Iterable<PointLight> lights) {
        final var material = intersection.material();
        var diffuseIntensity = 0f;
        var specularIntensity = 0f;
        for (final PointLight light : lights) {
            final var lightDirection = light.position().subtract(intersection.position()).normalize();
            diffuseIntensity += light.intensity() * material.diffuseIntensity() * LightingCalculations.diffuse(
                    lightDirection,
                    intersection.normal());
            specularIntensity += light.intensity() * material.specularIntensity() * LightingCalculations.specular(
                    lightDirection,
                    intersection.normal(),
                    ray.direction(),
                    material.shininess());
        }
        final var ambient = argbToVector(material.ambientColor()).multiply(material.ambientIntensity());
        final var diffuse = argbToVector(material.diffuseColor()).multiply(diffuseIntensity);
        final var specular = argbToVector(material.specularColor()).multiply(specularIntensity);
        return vectorToArgb(ambient.add(diffuse).add(specular));
    }

    private static Vector3 argbToVector(final int argb) {
        return new Vector3(
                ColorUtilities.redComponent(argb),
                ColorUtilities.greenComponent(argb),
                ColorUtilities.blueComponent(argb));
    }

    private static int vectorToArgb(final Vector3 vectorArgb) {
        return ColorUtilities.toOpaqueArgb(
                ColorUtilities.normalize((int) vectorArgb.x()),
                ColorUtilities.normalize((int) vectorArgb.y()),
                ColorUtilities.normalize((int) vectorArgb.z()));
    }
}
