package slowraytracer;

import java.util.Objects;

public final class Ray {

    private final Vector3 endpoint;
    private final Vector3 direction;

    public Ray(final Vector3 endpoint, final Vector3 direction) {
        this.endpoint = Objects.requireNonNull(endpoint);
        this.direction = Objects.requireNonNull(direction);
    }

    public Vector3 endpoint() {
        return endpoint;
    }

    public Vector3 direction() {
        return direction;
    }
}
