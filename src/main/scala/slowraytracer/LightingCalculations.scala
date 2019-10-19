package slowraytracer

private[slowraytracer] object LightingCalculations {

  def diffuseIntensity(lightDirection: Vector3, normal: Vector3): Float = {
    Math.max(0, lightDirection * normal)
  }
}
