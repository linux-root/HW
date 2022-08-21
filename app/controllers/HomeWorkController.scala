package controllers

import models.{PoolData, PoolQuery}
import play.api.libs.json.Json
import play.api.mvc._
import services.PoolDataService

import javax.inject._
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class HomeWorkController @Inject()(val controllerComponents:  ControllerComponents) extends BaseController {
  def add: Action[PoolData] = Action.async(parse.json[PoolData]) { implicit request: Request[PoolData] =>
    PoolDataService.addPoolData(request.body).map(result => Ok(result.message))
  }

  def query: Action[PoolQuery] = Action.async(parse.json[PoolQuery]) { implicit request: Request[PoolQuery] =>
    PoolDataService.query(request.body).map(r => Ok(Json.toJson(r)))
  }
}
