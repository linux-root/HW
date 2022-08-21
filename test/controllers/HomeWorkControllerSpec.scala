package controllers

import models.{PoolData, PoolQuery}
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.Play.materializer
import play.api.libs.json.Json
import play.api.test.Helpers._
import play.api.test._
import services.PoolDataService.QueryResult

/**
 * Note: each test case shouldn't be executed singly because
 * this test suite is stateful: a test might depend on state of previous test
 * All test should be run in order by executing HomeWorkControlSpec
 */
class HomeWorkControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  private lazy val controller = inject[HomeWorkController]

  "HomeWorkController add" should {

    "add new pool with id 2" in {
      val testData = PoolData(2, List(1, 7, 2, 6))
      val request = FakeRequest(POST, "/add")
        .withJsonBody(Json.toJson(testData))
        .withHeaders("Content-Type" -> "application/json")
      val result = call(controller.add, request)

      status(result) mustBe 200
      contentAsString(result) must include("inserted")
    }


    "append number 9 to pool with id 2" in {
      val testData = PoolData(2, List(9))
      val request = FakeRequest(POST, "/add")
        .withJsonBody(Json.toJson(testData))
        .withHeaders("Content-Type" -> "application/json")
      val result = call(controller.add, request)

      status(result) mustBe 200
      contentAsString(result) must include("appended")
    }
  }

  "HomeWorkController query" should {
    "query pool 2 with percentile = 50%" in {
      val queryData = PoolQuery(2, 50.0)
      val request = FakeRequest(POST, "/query")
        .withJsonBody(Json.toJson(queryData))
        .withHeaders("Content-Type" -> "application/json")
      val result = call(controller.query, request)

      status(result) mustBe 200
      contentAsJson(result) mustBe Json.toJson(QueryResult(2, 5))
    }
  }
}
