package slowraytracer

import org.scalatest.FlatSpec

class PixmapSpec extends FlatSpec {

  "Pixmap" should "throw IllegalArgumentException when width is non-positive" in {
    assertThrows[IllegalArgumentException] { new Pixmap(-1, 1) }
    assertThrows[IllegalArgumentException] { new Pixmap(0, 1) }
  }

  "Pixmap" should "throw IllegalArgumentException when height is non-positive" in {
    assertThrows[IllegalArgumentException] { new Pixmap(1, -1) }
    assertThrows[IllegalArgumentException] { new Pixmap(1, 0) }
  }

  "width and height" should "return width and height values" in {
    val pixmap = new Pixmap(1, 2)
    assertResult(1) { pixmap.width() }
    assertResult(2) { pixmap.height() }
  }

  "set" should "throw IllegalArgumentException when coordinates are out of bounds" in {
    val pixmap = new Pixmap(1, 1)
    assertThrows[IllegalArgumentException] { pixmap.set(-1, 0, 0) }
    assertThrows[IllegalArgumentException] { pixmap.set(1, 0, 0) }
    assertThrows[IllegalArgumentException] { pixmap.set(0, -1, 0) }
    assertThrows[IllegalArgumentException] { pixmap.set(0, 1, 0) }
  }

  "get" should "throw IllegalArgumentException when coordinates are out of bounds" in {
    val pixmap = new Pixmap(1, 1)
    assertThrows[IllegalArgumentException] { pixmap.get(-1, 0) }
    assertThrows[IllegalArgumentException] { pixmap.get(1, 0) }
    assertThrows[IllegalArgumentException] { pixmap.get(0, -1) }
    assertThrows[IllegalArgumentException] { pixmap.get(0, 1) }
  }

  "set then get" should "return the value assigned by the set operation" in {
    val pixmap = new Pixmap(1, 1)
    assertResult(1337) {
      pixmap.set(0, 0, 1337)
      pixmap.get(0, 0)
    }
  }

  "fill then asBufferedImage" should "return a BufferedImage whose values were set by the fill operation" in {
    val pixmap = new Pixmap(2, 2)
    pixmap.fill(_ + 2 * _)
    val bufferedImage = pixmap.asBufferedImage
    assertResult(0) { bufferedImage.getRGB(0, 0) }
    assertResult(1) { bufferedImage.getRGB(1, 0) }
    assertResult(2) { bufferedImage.getRGB(0, 1) }
    assertResult(3) { bufferedImage.getRGB(1, 1) }
  }
}
