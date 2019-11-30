package slowraytracer

case class Material(
  diffuseColor: MaterialColor,
  specularColor: MaterialColor = MaterialColor.DISABLED,
  shininess: Float = 0,
  reflectance: Float = 0,
  refractiveIndex: Float = 1,
  refractionIntensity: Float = 0)
