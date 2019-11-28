package slowraytracer

import org.scalatest.FlatSpec

class Vector3Spec extends FlatSpec {

  "- (negate)" should "return a vector whose components have been negated" in {
    assertResult(Vector3(-1, -2, -3)) { -Vector3(1, 2, 3) }
  }

  "+" should "return a vector whose components are the corresponding sums of the two vectors" in {
    assertResult(Vector3(1, 2, 3)) { Vector3(1, 2, 3) + Vector3.ZERO }
    assertResult(Vector3(2, 4, 6)) { Vector3(1, 2, 3) + Vector3(1, 2, 3) }
  }

  "-" should "return a vector whose components are the corresponding differences of the two vectors" in {
    assertResult(Vector3.ZERO) { Vector3(1, 2, 3) - Vector3(1, 2, 3) }
    assertResult(Vector3(1, 2, 3)) { Vector3(1, 2, 3) - Vector3.ZERO }
  }

  "*" should "return a vector whose components have been multiplied by the constant" in {
    assertResult(Vector3(2, 4, 6)) { Vector3(1, 2, 3) * 2 }
  }

  "/" should "return a vector whose components have been divided by the constant" in {
    assertResult(Vector3(2, 4, 6)) { Vector3(1, 2, 3) / 0.5f }
  }

  "*" should "return the dot product of the two vectors" in {
    assertResult(10) { Vector3(2, 4, 6) * Vector3(1, 0.5f, 1) }
  }

  "norm" should "return the norm of the vector" in {
    assertResult(Math.sqrt(56).toFloat) { Vector3(2, 4, 6).norm }
  }

  "normalize" should "return the normalized version of the vector" in {
    val norm = Math.sqrt(56).toFloat
    assertResult(Vector3(2 / norm, 4 / norm, 6 / norm)) { Vector3(2, 4, 6).normalize }
  }
}
