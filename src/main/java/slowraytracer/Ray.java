package slowraytracer;

import org.immutables.value.Value;

@Value.Immutable(builder = false)
public interface Ray {

    @Value.Parameter
    Vector3 endpoint();

    @Value.Parameter
    Vector3 direction();
}
