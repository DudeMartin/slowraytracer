package slowraytracer;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public final class Main {

    private static final double VERTICAL_FIELD_OF_VIEW_RADIANS = Math.toRadians(90);

    public static void main(final String[] arguments) throws IOException {
        final var imageWidth = 640;
        final var imageHeight = 480;
        final var pixmap = new Pixmap(imageWidth, imageHeight);
        pixmap.fill((x, y) -> new Color((float) x / imageWidth, (float) y / imageHeight, 0).getRGB());
        render(pixmap, new Sphere(new Vector3(-2, 0, -8), 4));
        ImageIO.write(pixmap.asBufferedImage(), "png", new File("out.png"));
    }

    private static void render(final Pixmap pixmap, final Sphere sphere) {
        final var halfWidth = pixmap.width() / 2;
        final var halfHeight = pixmap.height() / 2;
        final var directionZ = -halfHeight / ((float) Math.tan(VERTICAL_FIELD_OF_VIEW_RADIANS / 2));
        pixmap.fill((x, y) -> {
            final var directionX = (x + 0.5f) - halfWidth;
            final var directionY = (y + 0.5f) - halfHeight;
            final var viewDirection = new Vector3(directionX, directionY, directionZ).normalize();
            return sphere.intersects(new Ray(Vector3.ZERO, viewDirection))
                    ? Color.ORANGE.getRGB()
                    : Color.LIGHT_GRAY.getRGB();
        });
    }
}
