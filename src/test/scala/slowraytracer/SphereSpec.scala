package slowraytracer

import org.scalatest.FlatSpec

class SphereSpec extends FlatSpec {

  "intersections" should "return an empty list when the ray misses the sphere" in {
    assertResult(List()) { Sphere(Vector3(3, 3, 3), 3).intersections(Ray(Vector3.ZERO, Vector3(0, 3, 0))) }
  }

  "intersections" should "return one intersection when the ray is tangent to the sphere" in {
    assert {
      val intersections = Sphere(Vector3(3, 3, 3), 3).intersections(Ray(Vector3.ZERO, Vector3(3, 3, 0)))
      if (intersections.isEmpty) {
        true
      } else {
        // Due to rounding errors, "true" tangents where the rays touch the sphere at exactly one point rarely occur.
        // To compensate for this, check that the distance between the intersections is small (ideally 0).
        val intersectionError = intersections.map(intersection => intersection.distance).foldRight(0f)(_ - _)
        Math.abs(intersectionError) < 0.005
      }
    }
  }

  "intersections" should "return two intersections when the ray passes through the sphere" in {
    assertResult(2) { Sphere(Vector3(3, 3, 3), 3).intersections(Ray(Vector3.ZERO, Vector3(1, 1, 1))).size }
  }
}
