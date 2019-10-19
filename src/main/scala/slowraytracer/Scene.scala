package slowraytracer

trait Scene {

  def objects: Seq[SceneObject]
}

object Scene {

  final case class BuildableScene private (objects: Seq[SceneObject]) extends Scene {

    def withObject(sceneObject: SceneObject): BuildableScene = {
      copy(objects :+ sceneObject)
    }
  }

  def buildable: BuildableScene = BuildableScene(Seq.empty)
}
