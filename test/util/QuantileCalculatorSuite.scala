package util

import org.scalacheck.Gen
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

class QuantileCalculatorSuite extends AnyFunSuite with ScalaCheckPropertyChecks with Matchers{

  test("quantile as median") {
    val values = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    val calculatedQuantile = QuantileCalculator.percentile(50.0, values)
    assert(calculatedQuantile == 6)
  }


  private val genRandomValuesAndPercentile: Gen[(List[Int], Double)] = for {
    values <- Gen.nonEmptyListOf[Int](Gen.chooseNum[Int](0, Int.MaxValue))
    percentile <- Gen.choose[Double](0, 100)
  } yield (values, percentile)

  forAll(genRandomValuesAndPercentile) { case (values, percentile) =>
    val calculatedQuantile = QuantileCalculator.percentile(percentile, values)
    val totalX = values.count(_ <= calculatedQuantile)
    ((totalX / values.length) * 100 - percentile) should be < 0.1
  }

}
