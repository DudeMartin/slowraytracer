package slowraytracer

trait Scene {

  def objects: Seq[SceneObject]

  def pointLights: Seq[PointLight]
}

object Scene {

  final case class BuildableScene private (objects: Seq[SceneObject], pointLights: Seq[PointLight]) extends Scene {

    def withObject(sceneObject: SceneObject): BuildableScene = {
      copy(objects :+ sceneObject)
    }

    def withPointLight(pointLight: PointLight): BuildableScene = {
      copy(pointLights = pointLights :+ pointLight)
    }
  }

  def buildable: BuildableScene = BuildableScene(Seq.empty, Seq.empty)
}
