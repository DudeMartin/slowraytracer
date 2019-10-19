package slowraytracer

import java.awt.image.BufferedImage

class Pixmap(width: Int, height: Int) {

  Validations.assertPositive(width, "width")
  Validations.assertPositive(height, "height")

  private val data: Array[Color] = new Array[Color](width * height)

  def width(): Int = width

  def height(): Int = height

  def set(x: Int, y: Int, color: Color): Unit = {
    Validations.assertRange(x, 0, width, "X-coordinate")
    Validations.assertRange(y, 0, height, "Y-coordinate")
    data(x + y * width) = color;
  }

  def fill(mapper: (Int, Int) => Color): Unit = {
    for (y <- 0 until height) {
      for (x <- 0 until width) {
        data(x + y * width) = mapper(x, y)
      }
    }
  }

  def get(x: Int, y: Int): Color = {
    Validations.assertRange(x, 0, width, "X-coordinate")
    Validations.assertRange(y, 0, height, "Y-coordinate")
    data(x + y * width)
  }

  def asBufferedImage: BufferedImage = {
    val bufferedImage: BufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
    bufferedImage.setRGB(0, 0, width, height, data.map(_.argb), 0, width)
    bufferedImage
  }
}
