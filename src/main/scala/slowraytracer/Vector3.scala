package slowraytracer

case class Vector3(x: Float, y: Float, z: Float) {

  def unary_- : Vector3 = Vector3(-x, -y, -z)

  def +(other: Vector3): Vector3 = Vector3(x + other.x, y + other.y, z + other.z)

  def -(other: Vector3): Vector3 = Vector3(x - other.x, y - other.y, z - other.z)

  def *(constant: Float): Vector3 = Vector3(x * constant, y * constant, z * constant)

  def /(constant: Float): Vector3 = Vector3(x / constant, y / constant, z / constant)

  def *(other: Vector3): Float = x * other.x + y * other.y + z * other.z

  def norm: Float = Math.sqrt(this * this).toFloat

  def normalize: Vector3 = this / norm
}

case object Vector3 {

  final val ZERO: Vector3 = Vector3(0, 0, 0)
}