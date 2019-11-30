package slowraytracer

import scala.collection.mutable.ArrayBuffer

class Raytracer(maximumDepth: Int) {

  Validations.assertNonNegative(maximumDepth, "maximum depth")

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
        val pixelColor = castRay(Ray(cameraPosition, Vector3(directionX, directionY, directionZ).normalize), scene)
          .map(computeColor(_, scene))
          .getOrElse(scene.background)
        pixmap.set(x, y, pixelColor)
      }
    }
  }

  private def castRay(ray: Ray, scene: Scene)(implicit depth: Int = 0) =
    if (depth > maximumDepth) Option.empty else scene.objects.flatMap(_.intersections(ray)).minByOption(_.distance)

  private def computeColor(intersection: RayIntersection, scene: Scene)(implicit depth: Int = 0): Color = {
    def offsetPosition(targetDirection: Vector3) = {
      if (targetDirection * intersection.normal < 0) {
        intersection.position - intersection.normal * 0.001f
      } else {
        intersection.position + intersection.normal * 0.001f
      }
    }
    val colors = ArrayBuffer.empty[Color]
    val material = intersection.material
    if (material.reflectance > 0) {
      val reflectionDirection = LightingCalculations.reflect(intersection.ray.direction, intersection.normal)
      colors += castRay(Ray(offsetPosition(reflectionDirection), reflectionDirection), scene)(depth + 1)
        .map(computeColor(_, scene)(depth + 1))
        .getOrElse(scene.background) * material.reflectance
    }
    if (material.refractionIntensity > 0) {
      colors += LightingCalculations.refract(intersection.ray.direction, intersection.normal, material.refractiveIndex)
        .map(refractionDirection => Ray(offsetPosition(refractionDirection), refractionDirection))
        .flatMap(castRay(_, scene)(depth + 1))
        .map(computeColor(_, scene)(depth + 1))
        .getOrElse(scene.background) * material.refractionIntensity
    }
    val visibleLights = scene.lights.filter(_ match {
      case pointLight: PointLight =>
        def distanceTo(target: Vector3) = (target - intersection.position).norm
        val lightDirection = (pointLight.position - intersection.position).normalize
        !castRay(Ray(offsetPosition(lightDirection), lightDirection), scene)
          .map(_.position)
          .map(distanceTo)
          .exists(_ < distanceTo(pointLight.position))
      case _ => true
    })
    colors.appendAll(visibleLights.map(_.reflect(intersection))).foldRight(Color.BLACK)(_ + _)
  }
}

object Raytracer extends App {
  import java.io.File
  import javax.imageio.ImageIO

  private val pixmap = new Pixmap(800, 600)
  private val orangeMaterial = Material(MaterialColor(Color.ORANGE, 0.75f))
  private val magentaMaterial = Material(
    MaterialColor(Color.MAGENTA, 0.15f),
    specularColor = MaterialColor(Color.WHITE, 0.5f),
    shininess = 2,
    reflectance = 0.5f)
  private val mirrorMaterial = Material(
    MaterialColor.DISABLED,
    specularColor = MaterialColor(Color.WHITE, 0.5f),
    shininess = 10,
    reflectance = 0.9f)
  private val refractiveMaterial = Material(MaterialColor.DISABLED, refractiveIndex = 1.45f, refractionIntensity = 0.9f)
  new Raytracer(3).renderScene(pixmap, Scene.buildable
    .withBackground(Color.LIGHT_GRAY)
    .withObject(Sphere(Vector3(-2, 0, -8), 4, orangeMaterial
      .copy(specularColor = MaterialColor(Color.WHITE, 0.5f), shininess = 5)))
    .withObject(Sphere(Vector3(6, -1, -10), 3, orangeMaterial))
    .withObject(Sphere(Vector3(2, 0, -7), 2, magentaMaterial))
    .withObject(Sphere(Vector3(5, 4, -7), 2, mirrorMaterial))
    .withObject(Sphere(Vector3(-6, 4, -6), 1, mirrorMaterial))
    .withObject(Sphere(Vector3(3, 1, -4), 0.5f, refractiveMaterial))
    .withLight(PointLight(Vector3(-10, 10, 0), 0.75f))
    .withLight(PointLight(Vector3(5, 0, 0), 0.5f))
    .withLight(AmbientLight(0.55f)))
  sys.exit(if (ImageIO.write(pixmap.asBufferedImage, "png", new File("out.png"))) 0 else 1)
}
