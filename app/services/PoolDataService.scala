package services

import models.{PoolData, PoolQuery}
import play.api.libs.json.{Format, Json}
import repository.PoolRepository
import repository.PoolRepository.UpdateResult
import util.QuantileCalculator

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object PoolDataService {
  implicit val fmt: Format[QueryResult] = Json.format[QueryResult]

  case class QueryResult(quantile: Int, totalElement: Int)

  def addPoolData(data: PoolData): Future[UpdateResult] = Future(PoolRepository.addPoolData(data))

  def query(query: PoolQuery): Future[QueryResult] = Future{
   PoolRepository.getPool(query.poolId) match {
     case None =>
       QueryResult(0, 0)
     case Some(poolData) =>
       val quantile = QuantileCalculator.percentile(query.percentile, poolData.poolValues)
       QueryResult(quantile, poolData.poolValues.size)
   }
  }
}
