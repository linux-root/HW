package repository

import models.PoolData

import scala.collection.mutable

object PoolRepository {
  private val _storage: mutable.Map[Int, List[Int]] = mutable.Map.empty[Int, List[Int]]

  trait UpdateResult {
    def message: String
  }

  object UpdateResult {
    object Inserted extends UpdateResult {
      override def message: String = "inserted"
    }

    object Appended extends UpdateResult {
      override def message: String = "appended"
    }
  }

  def addPoolData(data: PoolData): UpdateResult = {
    _storage.get(data.poolId) match {
      case None =>
        _storage += (data.poolId -> data.poolValues)
        UpdateResult.Inserted
      case Some(values) =>
        _storage += (data.poolId -> (values ::: data.poolValues))
        UpdateResult.Appended
    }
  }

  def getPool(pooId: Int): Option[PoolData] = {
    _storage.get(pooId).map(values => PoolData(pooId, values))
  }

}
