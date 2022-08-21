package util

import org.scalacheck.Gen
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

class QuantileCalculatorSuite extends AnyFunSuite with ScalaCheckPropertyChecks{
  private val ACCEPTED_ERROR: Double = 0.01

  test("quantile as median") {
    val values = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    val calculatedQuantile = QuantileCalculator.percentile(50.0, values)
    assert(calculatedQuantile == 5.5)
  }

  test("test case found by property testing"){
    val values = List(0, 0, 0, 0)
    val calculatedQuantile = QuantileCalculator.percentile(1, values)
    assert(calculatedQuantile == 0)
  }

  private val genRandomValuesAndPercentile: Gen[(List[Int], Double)] = for {
    values <- Gen.nonEmptyListOf[Int](Gen.chooseNum[Int](Int.MinValue, Int.MaxValue))
    percentile <- Gen.choose[Double](1, 100)
  } yield (values, percentile)

  /**
   * property testing based on percentile definition
   */
  forAll(genRandomValuesAndPercentile) { case (values, percentile) =>
    whenever(values.size > 3) {
      val result = QuantileCalculator.percentile(percentile, values)
      val totalElementLessThanOrEqualToResult = values.count(_ <= result)
      val error = (totalElementLessThanOrEqualToResult / values.length) * 100 - percentile
      assert(error - 100.0 < ACCEPTED_ERROR || (error < ACCEPTED_ERROR))
    }
  }

}
