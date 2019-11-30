package slowraytracer

trait Scene {

  def background: Color

  def objects: Seq[SceneObject]

  def lights: Seq[Light]
}

object Scene {

  final case class BuildableScene private (
    background: Color,
    objects: Seq[SceneObject],
    lights: Seq[Light]) extends Scene {

    def withBackground(background: Color): BuildableScene = copy(background)

    def withObject(sceneObject: SceneObject): BuildableScene = copy(objects = objects :+ sceneObject)

    def withLight(light: Light): BuildableScene = copy(lights = lights :+ light)
  }

  def buildable: BuildableScene = BuildableScene(Color.BLACK, Seq.empty, Seq.empty)
}
