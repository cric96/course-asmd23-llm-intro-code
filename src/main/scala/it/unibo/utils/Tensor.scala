package it.unibo.utils

object Tensor:

  extension  [T: Numeric](data: Seq[T])
    def distance(other: Seq[T]): Double =
      Math.sqrt(data.zip(other).map {
        case (a, b) => Math.pow(Numeric[T].toFloat(a) - Numeric[T].toFloat(b), 2) }.sum)
      .toFloat
    def dotProduct(other: Seq[T]): Double = data.zip(other).map {
      case (a, b) => Numeric[T].toFloat(a) * Numeric[T].toFloat(b) }.sum
    def magnitude: Double = Math.sqrt(data.map { a => Math.pow(Numeric[T].toFloat(a), 2) }.sum).toFloat

    def cosineSimilarity(other: Seq[T]): Double =
      data.dotProduct(other) / (data.magnitude * other.magnitude)