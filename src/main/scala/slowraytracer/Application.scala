package slowraytracer

object Application {

  def main(arguments: Array[String]): Unit = {
    import java.io.File
    import javax.imageio.ImageIO
    val pixmap = new Pixmap(800, 600)
    val orangeMaterial = Material(MaterialColor(Color.ORANGE, 0.25f), MaterialColor(Color.WHITE, 0.75f))
    val magentaMaterial = Material(MaterialColor(Color.MAGENTA, 1))
    pixmap.fill((_, _) => Color.LIGHT_GRAY)
    renderScene(pixmap, Scene.buildable
      .withObject(Sphere(Vector3(-2, 0, -8), 4, orangeMaterial))
      .withObject(Sphere(Vector3(6, -1, -10), 3, orangeMaterial))
      .withObject(Sphere(Vector3(2, 0, -7), 2, magentaMaterial))
      .withPointLight(PointLight(Vector3(-10, 10, 0), 1)))
    sys.exit(if (ImageIO.write(pixmap.asBufferedImage, "png", new File("out.png"))) 0 else 1)
  }

  private def renderScene(pixmap: Pixmap, scene: Scene): Unit = {
    val cameraPosition = Vector3.ZERO
    val fovRadians = Math.toRadians(90)
    val width = pixmap.width()
    val halfWidth = width / 2f
    val height = pixmap.height()
    val halfHeight = height / 2f
    val directionZ = -halfHeight / Math.tan(fovRadians / 2).toFloat
    for (y <- 0 until height) {
      for (x <- 0 until width) {
        val directionX = (x + 0.5f) - halfWidth
        val directionY = -(y + 0.5f) + halfHeight
        val viewDirection = Vector3(directionX, directionY, directionZ).normalize
        val viewRay = Ray(cameraPosition, viewDirection)
        castRay(viewRay, scene).map(computeColor(_, scene)).foreach(pixmap.set(x, y, _))
      }
    }
  }

  private def castRay(ray: Ray, scene: Scene) = {
    scene.objects.flatMap(_.intersections(ray)).minByOption(_.distance)
  }

  private def computeColor(intersection: RayIntersection, scene: Scene) = {
    val material = intersection.material
    val diffuseIntensity = scene.pointLights.foldRight(0f)((light, intensity) => {
      val lightDirection = (light.position - intersection.position).normalize
      intensity + light.intensity * material.diffuseColor.intensity * LightingCalculations.diffuseIntensity(
        lightDirection,
        intersection.normal)
    })
    material.ambientColor.color * material.ambientColor.intensity + material.diffuseColor.color * diffuseIntensity
  }
}
