package models

import play.api.libs.json.{Format, Json}

case class PoolQuery(poolId: Int, percentile: Double)

object PoolQuery {
  implicit val fmt: Format[PoolQuery] = Json.format[PoolQuery]
}

