package slowraytracer

trait Light {

  def intensity: Float

  def reflect(intersection: RayIntersection): Color
}
