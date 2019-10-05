package slowraytracer;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public final class Main {

    public static void main(final String[] arguments) throws IOException {
        final var imageWidth = 640;
        final var imageHeight = 480;
        final var pixmap = new Pixmap(imageWidth, imageHeight);
        pixmap.fill((x, y) -> new Color((float) x / imageWidth, (float) y / imageHeight, 0).getRGB());
        ImageIO.write(pixmap.asBufferedImage(), "png", new File("out.png"));
    }
}
