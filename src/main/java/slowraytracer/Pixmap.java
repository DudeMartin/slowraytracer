package slowraytracer;

import java.awt.image.BufferedImage;
import java.util.Objects;

public final class Pixmap {

    private final int width;
    private final int height;
    private final int[] data;

    public Pixmap(final int width, final int height) {
        this.width = Validations.requirePositive(width, "width");
        this.height = Validations.requirePositive(height, "height");
        data = new int[width * height];
    }

    public void set(final int x, final int y, final int color) {
        Validations.requireRange(x, 0, width, "X-coordinate");
        Validations.requireRange(y, 0, height, "Y-coordinate");
        data[x + y * width] = color;
    }

    public void fill(final Mapper mapper) {
        Objects.requireNonNull(mapper);
        for (var y = 0; y < height; y++) {
            for (var x = 0; x < width; x++) {
                data[x + y * width] = mapper.map(x, y);
            }
        }
    }

    public int get(final int x, final int y) {
        Validations.requireRange(x, 0, width, "X-coordinate");
        Validations.requireRange(y, 0, height, "Y-coordinate");
        return data[x + y * width];
    }

    public BufferedImage asBufferedImage() {
        final BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        bufferedImage.setRGB(0, 0, width, height, data, 0, width);
        return bufferedImage;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    @FunctionalInterface
    public interface Mapper {

        int map(int x, int y);
    }
}
