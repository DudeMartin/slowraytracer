package slowraytracer

object Application {

  def main(arguments: Array[String]): Unit = {
    import java.io.File
    import javax.imageio.ImageIO
    val pixmap = new Pixmap(800, 600)
    pixmap.fill((_, _) => Color.LIGHT_GRAY)
    renderScene(pixmap, Scene.buildable
      .withObject(Sphere(Vector3(-2, 0, -8), 4))
      .withObject(Sphere(Vector3(6, -1, -10), 3)))
    sys.exit(if (ImageIO.write(pixmap.asBufferedImage, "png", new File("out.png"))) 0 else 1)
  }

  private def renderScene(pixmap: Pixmap, scene: Scene): Unit = {
    val cameraPosition = Vector3.ZERO
    val fovRadians = Math.toRadians(90)
    val width = pixmap.width()
    val halfWidth = width / 2f
    val height = pixmap.height()
    val halfHeight = height / 2f
    val directionZ = -halfHeight / Math.tan(fovRadians / 2).asInstanceOf[Float]
    for (y <- 0 until height) {
      for (x <- 0 until width) {
        val directionX = (x + 0.5f) - halfWidth
        val directionY = -(y + 0.5f) + halfHeight
        val viewDirection = Vector3(directionX, directionY, directionZ)
        val viewRay = Ray(cameraPosition, viewDirection)
        if (scene.objects.exists(sceneObject => sceneObject.intersections(viewRay).nonEmpty)) {
          pixmap.set(x, y, Color.ORANGE)
        }
      }
    }
  }
}
