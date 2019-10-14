package slowraytracer

import java.awt.image.BufferedImage

class Pixmap(width: Int, height: Int) {

  Validations.assertPositive(width, "width")
  Validations.assertPositive(height, "height")

  private val data: Array[Int] = new Array[Int](width * height)

  def width(): Int = width

  def height(): Int = height

  def set(x: Int, y: Int, argb: Int): Unit = {
    Validations.assertRange(x, 0, width, "X-coordinate")
    Validations.assertRange(y, 0, height, "Y-coordinate")
    data(x + y * width) = argb;
  }

  def fill(mapper: (Int, Int) => Int): Unit = {
    for (y <- 0 until height) {
      for (x <- 0 until width) {
        data(x + y * width) = mapper(x, y)
      }
    }
  }

  def get(x: Int, y: Int): Int = {
    Validations.assertRange(x, 0, width, "X-coordinate")
    Validations.assertRange(y, 0, height, "Y-coordinate")
    data(x + y * width)
  }

  def asBufferedImage: BufferedImage = {
    val bufferedImage: BufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
    bufferedImage.setRGB(0, 0, width, height, data, 0, width)
    bufferedImage
  }
}
