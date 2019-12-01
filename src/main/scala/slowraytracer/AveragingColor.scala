package slowraytracer

private[slowraytracer] class AveragingColor {

  private var alphaValue = 0
  private var redValue = 0
  private var greenValue = 0
  private var blueValue = 0
  private var count = 0

  def +=(other: Color): AveragingColor = {
    alphaValue += other.alpha
    redValue += other.red
    greenValue += other.green
    blueValue += other.blue
    count += 1
    this
  }

  def averaged: Color = Color.of(alphaValue / count, redValue / count, greenValue / count, blueValue / count)
}
