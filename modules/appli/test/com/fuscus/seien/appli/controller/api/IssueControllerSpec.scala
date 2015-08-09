package com.fuscus.seien.appli.controller.api

import com.fuscus.seien.appli.data.TestContext
import com.fuscus.seien.domain.service.IssueServiceRegistry._
import com.fuscus.seien.infra.vo.URI
import com.github.tototoshi.play2.json4s.test.native.Helpers._
import org.json4s._
import org.mockito.Matchers.{ eq => meq }
import org.specs2.mock.Mockito
import play.api.Logger
import play.api.mvc._
import play.api.test._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * Created by watawuwu on 15/07/03.
 */
object IssueControllerSpec extends PlaySpecification with Results with Mockito with TestContext {

  isolated

  val c = new IssueController {
    val service = mock[IssueService]
    val logger = mock[Logger]
  }

  "Issue#show" should {
    "should be valid" in {
      val issue = expectIssue
      val expectBody = Extraction.decompose(issue)

      c.service.show(issue.id) returns Future(Right(issue))

      val result: Future[Result] = c.show(issue.id.toString).apply(FakeRequest(GET, s"/issue/${issue.id}"))
      status(result) must be equalTo OK
      contentType(result) must beSome.which(_ == "application/json")
      contentAsJson4s(result) must_== expectBody
    }
  }

  "Issue#create2" should {
    "should be valid" in {

      val issue = expectIssue
      val expectBody = Extraction.decompose(issue)

      val params = List(("title", issue.title), ("uri", issue.uri.toString), ("healthCheckUri", issue.healthCheckUri.get.toString), ("description", issue.description.get))

      c.service.create(meq(issue.title), any[URI], any[URI], any[String]) returns Right(issue)

      val result: Future[Result] = c.create().apply(FakeRequest(POST, "/issue").withFormUrlEncodedBody(params: _*))
      status(result) must be equalTo CREATED
      contentType(result) must beSome.which(_ == "application/json")
      contentAsJson4s(result) must_== expectBody
    }
  }
}
