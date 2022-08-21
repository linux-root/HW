package models

import play.api.libs.json.{Format, Json}

case class PoolData(poolId: Int, poolValues: List[Int])

object PoolData {
  implicit val fmt: Format[PoolData] = Json.format[PoolData]
}