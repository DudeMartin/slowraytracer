package slowraytracer

abstract class RayIntersection {

  def ray: Ray

  def distance: Float

  def position: Vector3 = ray.endpoint + (ray.direction * distance)

  def normal: Vector3
}
