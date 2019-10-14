package slowraytracer

object Application {

  def main(arguments: Array[String]): Unit = {
    import java.io.File
    import javax.imageio.ImageIO
    val pixmap = new Pixmap(800, 600)
    pixmap.fill((_, _) => 0xffa0b0c0)
    sys.exit(if (ImageIO.write(pixmap.asBufferedImage, "png", new File("out.png"))) 0 else 1)
  }
}
