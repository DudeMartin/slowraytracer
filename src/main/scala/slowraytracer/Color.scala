package slowraytracer

case class Color private (argb: Int) {

  def alpha: Int = argb >>> 24 & 0xff

  def red: Int = argb >>> 16 & 0xff

  def green: Int = argb >>> 8 & 0xff

  def blue: Int = argb & 0xff

  def *(factor: Float): Color = Color.of(alpha, (red * factor).toInt, (green * factor).toInt, (blue * factor).toInt)

  def +(other: Color): Color = Color.of(alpha, red + other.red, green + other.green, blue + other.blue)

  override def toString: String = s"argb($alpha, $red, $green, $blue)"
}

case object Color {

  final val BLACK = of(0, 0, 0)
  final val LIGHT_GRAY = of(192, 192, 192)
  final val ORANGE = of(255, 200, 0)
  final val MAGENTA = of(255, 0, 255)
  final val WHITE = of(255, 255, 255)

  def of(alpha: Int, red: Int, green: Int, blue: Int): Color = Color(toArgb(alpha, red, green, blue))

  def of(red: Int, green: Int, blue: Int): Color = of(255, red, green, blue)

  private def toArgb(alpha: Int, red: Int, green: Int, blue: Int) =
    normalize(alpha) << 24 | normalize(red) << 16 | normalize(green) << 8 | normalize(blue)

  private def normalize(component: Int): Int = Math.max(0, Math.min(255, component))
}
