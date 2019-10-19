package slowraytracer

import org.scalatest.FlatSpec

class ValidationsSpec extends FlatSpec {

  "assertPositive" should "throw IllegalArgumentException when value is negative" in {
    assertThrows[IllegalArgumentException] { Validations.assertPositive(-1, "value") }
  }

  "assertPositive" should "throw IllegalArgumentException when value is 0" in {
    assertThrows[IllegalArgumentException] { Validations.assertPositive(0, "value") }
  }

  "assertPositive" should "return nothing when value is positive" in {
    assertResult(()) { Validations.assertPositive(1, "value") }
  }

  "assertRange" should "throw IllegalArgumentException when value is out of range" in {
    val startInclusive = -2
    val endExclusive = 3
    assertThrows[IllegalArgumentException] { Validations.assertRange(-4, startInclusive, endExclusive, "value") }
    assertThrows[IllegalArgumentException] { Validations.assertRange(-3, startInclusive, endExclusive, "value") }
    assertThrows[IllegalArgumentException] { Validations.assertRange(3, startInclusive, endExclusive, "value") }
    assertThrows[IllegalArgumentException] { Validations.assertRange(4, startInclusive, endExclusive, "value") }
  }

  "assertRange" should "return nothing when value is within the range" in {
    assertResult(()) { Validations.assertRange(0, -2, 3, "value") }
  }
}
