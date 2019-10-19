package slowraytracer

case class MaterialColor(color: Color, intensity: Float)

case object MaterialColor {

  final val DISABLED = MaterialColor(Color.BLACK, 0)
}