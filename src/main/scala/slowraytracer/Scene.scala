package slowraytracer

trait Scene {

  def background: Color

  def objects: Seq[SceneObject]

  def pointLights: Seq[PointLight]
}

object Scene {

  final case class BuildableScene private (
    background: Color,
    objects: Seq[SceneObject],
    pointLights: Seq[PointLight]) extends Scene {

    def withBackground(background: Color): BuildableScene = copy(background)

    def withObject(sceneObject: SceneObject): BuildableScene = copy(objects = objects :+ sceneObject)

    def withPointLight(pointLight: PointLight): BuildableScene = copy(pointLights = pointLights :+ pointLight)
  }

  def buildable: BuildableScene = BuildableScene(Color.BLACK, Seq.empty, Seq.empty)
}
