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

  def +(other: Color): Color = copy(Color.toArgb(alpha, red + other.red, green + other.green, blue + other.blue))
}

case object Color {

  final val BLACK = of(0, 0, 0)
  final val LIGHT_GRAY = of(192, 192, 192)
  final val ORANGE = of(255, 200, 0)
  final val MAGENTA = of(255, 0, 255)
  final val WHITE = of(255, 255, 255)

  def of(alpha: Int, red: Int, green: Int, blue: Int): Color = Color(toArgb(alpha, red, green, blue))

  def of(red: Int, green: Int, blue: Int): Color = of(255, red, green, blue)

  private def toArgb(alpha: Int, red: Int, green: Int, blue: Int) = alpha << 24 | red << 16 | green << 8 | blue
}
