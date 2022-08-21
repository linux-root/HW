package repository

import models.PoolData
import org.scalatest.funsuite.AnyFunSuite

import scala.annotation.tailrec

class PoolRepositorySpec extends AnyFunSuite{

  PoolRepository.reset()

  @tailrec
  private def isOrdered(xs: List[Int], lt: Int = Int.MinValue): Boolean = xs match {
    case head :: tail =>
      head >= lt && isOrdered(tail, head)
    case Nil => true
  }

   test("add") {
     val data = PoolData(2, List(1, 2, 8, 2, 1, 5, 4))
     val result = PoolRepository.addData(data)
     assert(result.message == "inserted")
   }

  test("all items are in order") {
    val result = PoolRepository.getPool(2).get
    assert(isOrdered(result.poolValues))
  }

  test("add 3 items, all items are still in order") {
    val updateResult = PoolRepository.addData(PoolData(2, List(3, 6, 7)))
    val result = PoolRepository.getPool(2).get
    assert(updateResult.message == "appended")
    assert(isOrdered(result.poolValues))
  }
}
