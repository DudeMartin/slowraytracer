package slowraytracer;

import org.immutables.value.Value;

@Value.Immutable(builder = false)
public abstract class PointLight {

    @Value.Parameter
    public abstract Vector3 position();

    @Value.Parameter
    public abstract float intensity();

    @Value.Check
    void checkInputs() {
        Validations.requirePositive(intensity(), "intensity");
    }
}
