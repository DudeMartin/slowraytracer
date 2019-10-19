package slowraytracer

case class Color private (argb: Int) {

  def alpha: Int = argb >>> 24 & 0xff

  def red: Int = argb >>> 16 & 0xff

  def green: Int = argb >>> 8 & 0xff

  def blue: Int = argb & 0xff

  def *(factor: Float): Color = copy(Color.toArgb(
    alpha,
    (red * factor).toInt,
    (green * factor).toInt,
    (blue * factor).toInt))
}

case object Color {

  final val BLACK = of(0, 0, 0)
  final val LIGHT_GRAY = of(192, 192, 192)
  final val ORANGE = of(255, 165, 0)

  def of(alpha: Int, red: Int, green: Int, blue: Int): Color = Color(toArgb(alpha, red, green, blue))

  def of(red: Int, green: Int, blue: Int): Color = of(255, red, green, blue)

  private def toArgb(alpha: Int, red: Int, green: Int, blue: Int) = alpha << 24 | red << 16 | green << 8 | blue
}
