package util

object QuantileCalculator {
  def percentile(p: Double, values: List[Int]): Int = {
    val sorted = values
    val rank = math.ceil((values.length - 1) * (p / 100)).toInt
    sorted(rank)
  }
}