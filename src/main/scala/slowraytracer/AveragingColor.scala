package slowraytracer

private[slowraytracer] class AveragingColor {

  private var alphaValue = 0
  private var redValue = 0
  private var greenValue = 0
  private var blueValue = 0
  private var count = 0

  def +=(other: Color): Unit = {
    alphaValue += other.alpha
    redValue += other.red
    greenValue += other.green
    blueValue += other.blue
    count += 1
  }

  def averaged: Color = if (count == 0) {
    Color.BLACK
  } else {
    Color.of(alphaValue / count, redValue / count, greenValue / count, blueValue / count)
  }
}
