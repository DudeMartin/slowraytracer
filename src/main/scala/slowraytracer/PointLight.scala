package slowraytracer

case class PointLight(position: Vector3, intensity: Float) extends Light {

  override def reflect(intersection: RayIntersection): Color = {
    val material = intersection.material
    val lightDirection = (position - intersection.position).normalize
    val diffuseIntensity = LightingCalculations.diffuseIntensity(lightDirection, intersection.normal)
    val diffuseColor = material.diffuseColor.color * material.diffuseColor.intensity * diffuseIntensity * intensity
    val specularIntensity = LightingCalculations.specularIntensity(
      lightDirection,
      intersection.normal,
      intersection.ray.direction,
      material.shininess)
    val specularColor = material.specularColor.color * material.specularColor.intensity * specularIntensity * intensity
    diffuseColor + specularColor
  }
}
