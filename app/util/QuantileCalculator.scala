package util

object QuantileCalculator {
  /**
   * refer to https://www.calculatorsoup.com/calculators/statistics/percentile-calculator.php
   */
  def percentile(p: Double, sortedValues: List[Int]): Double = {
    require(sortedValues.nonEmpty)
    val x = sortedValues
    val n = x.size
    val r = (p / 100) * (n - 1) // rank
    val ri = r.toInt // integer part of r
    val rf = r - ri // fractional part of r
    x(ri) + rf*(x(ri + 1) - x(ri))
  }
}