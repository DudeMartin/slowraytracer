package slowraytracer

import scala.collection.mutable.ListBuffer

trait Scene {

  def objects: List[SceneObject]
}

object Scene {

  final class Mutable extends Scene {

    private val objectBuffer = new ListBuffer[SceneObject]()

    def withObject(sceneObject: SceneObject): Mutable = {
      objectBuffer += sceneObject
      this
    }

    override def objects: List[SceneObject] = objectBuffer.toList
  }

  def mutable: Mutable = new Mutable()
}
