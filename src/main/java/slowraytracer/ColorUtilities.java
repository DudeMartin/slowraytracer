package slowraytracer;

public final class ColorUtilities {

    private ColorUtilities() {
    }

    public static int alphaComponent(final int argb) {
        return (argb & 0xff000000) >>> 24;
    }

    public static int redComponent(final int argb) {
        return (argb & 0xff0000) >>> 16;
    }

    public static int greenComponent(final int argb) {
        return (argb & 0xff00) >>> 8;
    }

    public static int blueComponent(final int argb) {
        return argb & 0xff;
    }

    public static int normalize(final int component) {
        return Math.max(0, Math.min(255, component));
    }

    public static int toArgb(final int alpha, final int red, final int green, final int blue) {
        return alpha << 24 | red << 16 | green << 8 | blue;
    }

    public static int toOpaqueArgb(final int red, final int green, final int blue) {
        return toArgb(255, red, green, blue);
    }

    public static int scaleOpaqueArgb(final int argb, final float factor) {
        final var redScaled = (int) (redComponent(argb) * factor);
        final var greenScaled = (int) (greenComponent(argb) * factor);
        final var blueScaled = (int) (blueComponent(argb) * factor);
        return toOpaqueArgb(normalize(redScaled), normalize(greenScaled), normalize(blueScaled));
    }
}
