package slowraytracer

case class AmbientLight(intensity: Float) extends Light {

  override def reflect(intersection: RayIntersection): Color = intersection.material.diffuseColor.color * intensity
}
