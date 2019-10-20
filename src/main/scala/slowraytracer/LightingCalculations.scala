package slowraytracer

private[slowraytracer] object LightingCalculations {

  def diffuseIntensity(lightDirection: Vector3, normal: Vector3): Float = Math.max(0, lightDirection * normal)

  def specularIntensity(lightDirection: Vector3, normal: Vector3, viewDirection: Vector3, shininess: Float): Float =
    Math.pow(Math.max(0, reflect(lightDirection, normal) * viewDirection), shininess).toFloat

  def reflect(lightDirection: Vector3, normal: Vector3): Vector3 =
    lightDirection - normal * 2 * (lightDirection * normal)
}
