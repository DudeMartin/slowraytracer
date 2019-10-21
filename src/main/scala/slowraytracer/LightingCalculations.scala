package slowraytracer

private[slowraytracer] object LightingCalculations {

  def diffuseIntensity(lightDirection: Vector3, normal: Vector3): Float = Math.max(0, lightDirection * normal)

  def specularIntensity(lightDirection: Vector3, normal: Vector3, viewDirection: Vector3, shininess: Float): Float =
    Math.pow(Math.max(0, reflect(lightDirection, normal) * viewDirection), shininess).toFloat

  def reflect(lightDirection: Vector3, normal: Vector3): Vector3 =
    lightDirection - normal * 2 * (lightDirection * normal)

  def refract(
    lightDirection: Vector3,
    normal: Vector3,
    targetRefractionIndex: Float,
    sourceRefractionIndex: Float = 1): Option[Vector3] = {
    val cos = -Math.max(-1, Math.min(1, lightDirection * normal))
    if (cos < 0) {
      return refract(lightDirection, -normal, sourceRefractionIndex, targetRefractionIndex)
    }
    val r = sourceRefractionIndex / targetRefractionIndex
    val k = 1 - r * r * (1 - cos * cos)
    if (k < 0) Option.empty else Option(lightDirection * r + normal * (r * cos - Math.sqrt(k).toFloat))
  }
}
