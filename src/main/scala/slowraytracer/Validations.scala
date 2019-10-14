package slowraytracer

object Validations {

  def assertPositive(value: Int, valueName: String): Unit = {
    if (value <= 0) {
      throw new IllegalArgumentException(s"The $valueName must be positive.")
    }
  }

  def assertRange(value: Int, startInclusive: Int, endExclusive: Int, valueName: String): Unit = {
    if (value < startInclusive || value >= endExclusive) {
      throw new IllegalArgumentException(
        s"The $valueName of $value is not in the range of [$startInclusive, $endExclusive).")
    }
  }
}
