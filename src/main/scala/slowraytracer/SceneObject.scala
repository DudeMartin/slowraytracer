package slowraytracer

trait SceneObject {

  def intersections(ray: Ray): Iterable[RayIntersection]
}
