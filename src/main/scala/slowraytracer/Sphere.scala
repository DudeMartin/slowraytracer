package slowraytracer

case class Sphere(center: Vector3, radius: Float) {

  def intersections(ray: Ray): List[RayIntersection] = {
    val L = center - ray.endpoint
    val tca = L * ray.direction.normalize
    if (tca < 0) {
      return List.empty
    }
    val d2 = L * L - tca * tca
    val radiusSquared = radius * radius
    if (d2 > radiusSquared) {
      return List.empty
    }
    val thc = Math.sqrt(radiusSquared - d2).asInstanceOf[Float]
    val t0 = tca - thc
    val t1 = tca + thc
    if (t0 >= 0) {
      if (t1 >= 0) {
        List(RayIntersection(ray, t0), RayIntersection(ray, t1))
      } else {
        List(RayIntersection(ray, t0))
      }
    } else {
      List.empty
    }
  }

  case class RayIntersection private (ray: Ray, distance: Float) {

    private val positionComputed = ray.endpoint + (ray.direction * distance)
    private val normalComputed = (position - center).normalize

    def position: Vector3 = positionComputed

    def normal: Vector3 = normalComputed
  }
}
