package slowraytracer

case class Sphere(center: Vector3, radius: Float) extends SceneObject {

  override def intersections(ray: Ray): List[RayIntersection] = {
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
        List(SphereIntersection(ray, t0), SphereIntersection(ray, t1))
      } else {
        List(SphereIntersection(ray, t0))
      }
    } else {
      List.empty
    }
  }

  private case class SphereIntersection(ray: Ray, distance: Float) extends RayIntersection {

    override def normal: Vector3 = (position - center).normalize
  }
}
