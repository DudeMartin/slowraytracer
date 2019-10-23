package slowraytracer

object Raytracer {

  def main(arguments: Array[String]): Unit = {
    import java.io.File
    import javax.imageio.ImageIO
    val pixmap = new Pixmap(800, 600)
    val orangeMaterial = Material(MaterialColor(Color.ORANGE, 0.55f), MaterialColor(Color.ORANGE, 0.75f))
    val magentaMaterial = Material(
      MaterialColor(Color.MAGENTA, 0.65f),
      specularColor = MaterialColor(Color.WHITE, 0.5f),
      shininess = 2,
      reflectance = 0.5f)
    val mirrorMaterial = Material(
      MaterialColor.DISABLED,
      specularColor = MaterialColor(Color.WHITE, 0.5f),
      shininess = 10,
      reflectance = 0.9f)
    val refractiveMaterial = Material(MaterialColor.DISABLED, refractiveIndex = 1.45f, refractionIntensity = 0.9f)
    pixmap.fill((_, _) => Color.LIGHT_GRAY)
    renderScene(pixmap, Scene.buildable
      .withObject(Sphere(Vector3(-2, 0, -8), 4, orangeMaterial
        .copy(specularColor = MaterialColor(Color.WHITE, 0.5f), shininess = 5)))
      .withObject(Sphere(Vector3(6, -1, -10), 3, orangeMaterial))
      .withObject(Sphere(Vector3(2, 0, -7), 2, magentaMaterial))
      .withObject(Sphere(Vector3(5, 4, -7), 2, mirrorMaterial))
      .withObject(Sphere(Vector3(-6, 4, -6), 1, mirrorMaterial))
      .withObject(Sphere(Vector3(3, 1, -4), 0.5f, refractiveMaterial))
      .withPointLight(PointLight(Vector3(-10, 10, 0), 0.75f))
      .withPointLight(PointLight(Vector3(5, 0, 0), 0.5f)))
    sys.exit(if (ImageIO.write(pixmap.asBufferedImage, "png", new File("out.png"))) 0 else 1)
  }

  def renderScene(
    pixmap: Pixmap,
    scene: Scene,
    cameraPosition: Vector3 = Vector3.ZERO,
    fovRadians: Float = Math.toRadians(90).toFloat): Unit = {
    val width = pixmap.width
    val halfWidth = width / 2f
    val height = pixmap.height
    val halfHeight = height / 2f
    val directionZ = -halfHeight / Math.tan(fovRadians / 2).toFloat
    for (y <- 0 until height) {
      for (x <- 0 until width) {
        val directionX = (x + 0.5f) - halfWidth
        val directionY = -(y + 0.5f) + halfHeight
        val viewDirection = Vector3(directionX, directionY, directionZ).normalize
        castRay(Ray(cameraPosition, viewDirection), scene).map(computeColor(_, scene)).foreach(pixmap.set(x, y, _))
      }
    }
  }

  private def castRay(ray: Ray, scene: Scene)(implicit depth: Int = 0) =
    if (depth > 3) Option.empty else scene.objects.flatMap(_.intersections(ray)).minByOption(_.distance)

  private def computeColor(intersection: RayIntersection, scene: Scene)(implicit depth: Int = 0): Color = {
    import LightingCalculations._
    def directionTo(target: Vector3, source: Vector3) = (target - source).normalize
    val material = intersection.material
    val ambientColor = material.ambientColor.color * material.ambientColor.intensity
    val visibleLights = scene.pointLights.filterNot(light => {
      def distanceTo(target: Vector3, source: Vector3) = (target - source).norm
      castRay(Ray(intersection.position, directionTo(light.position, intersection.position)), scene)
        .map(_.position)
        .map(distanceTo(_, intersection.position))
        .exists(_ < distanceTo(light.position, intersection.position))
    })
    val totalDiffuseIntensity = visibleLights.foldRight(0f)((light, intensity) => {
      intensity + light.intensity * material.diffuseColor.intensity * diffuseIntensity(
        directionTo(light.position, intersection.position),
        intersection.normal)
    })
    val diffuseColor = material.diffuseColor.color * totalDiffuseIntensity
    val totalSpecularIntensity = visibleLights.foldRight(0f)((light, intensity) => {
      intensity + light.intensity * material.specularColor.intensity * specularIntensity(
        directionTo(light.position, intersection.position),
        intersection.normal,
        intersection.ray.direction,
        material.shininess)
    })
    val specularColor = material.specularColor.color * totalSpecularIntensity
    val reflectedRay = Ray(intersection.position, reflect(intersection.ray.direction, intersection.normal))
    val reflectionColor = castRay(reflectedRay, scene)(depth + 1).map(computeColor(_, scene)(depth + 1))
      .getOrElse(Color.LIGHT_GRAY) * material.reflectance
    val refractionColor = refract(intersection.ray.direction, intersection.normal, material.refractiveIndex)
      .map(refractionDirection => {
        val refractionEndpoint = if (refractionDirection * intersection.normal < 0) {
          intersection.position - intersection.normal * 0.001f
        } else {
          intersection.position + intersection.normal * 0.001f
        }
        Ray(refractionEndpoint, refractionDirection)
      })
      .flatMap(castRay(_, scene)(depth + 1))
      .map(computeColor(_, scene)(depth + 1))
      .getOrElse(Color.LIGHT_GRAY) * material.refractionIntensity
    ambientColor + diffuseColor + specularColor + reflectionColor + refractionColor
  }
}
