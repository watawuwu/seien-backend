package com.fuscus.seien.appli.controller

import com.fuscus.seien.appli.cont.ActionCont
import com.fuscus.seien.appli.data.TestContext
import org.specs2.mock.Mockito
import play.api.Logger
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._
import play.api.test.{ FakeRequest, PlaySpecification }

import scala.concurrent.Future
/**
 * Created by wataru.matsui on 15/08/11.
 */
object AppControllerSpec extends PlaySpecification with Results with Mockito with TestContext {
  isolated

  val c = new AppController {
    val logger = mock[Logger]
  }

  def runSuccessful[A](request: A, cont: A => ActionCont[A]): Future[Result] =
    cont(request).run(_ => Future.successful(Ok))

  "AppControllerSpec " should {
    "corsCont valid" in {
      val result = runSuccessful(FakeRequest(), c.corsCont)
      status(result) must be equalTo OK
      c.corsResponseHeaderSeq.forall {
        case (k, v) =>
          header(k, result) === Some(v)
      } === true
    }

    "securityHeaderCont valid" in {
      val result = runSuccessful(FakeRequest(), c.securityHeaderCont)
      status(result) must be equalTo OK
      c.securityResponseHeaderSeq.forall {
        case (k, v) =>
          header(k, result) === Some(v)
      } === true
    }

    "responseHeaderCont valid" in {
      val result = runSuccessful(FakeRequest(), c.responseHeaderCont)
      status(result) must be equalTo OK
      (c.corsResponseHeaderSeq ++ c.securityResponseHeaderSeq).forall {
        case (k, v) =>
          header(k, result) === Some(v)
      } === true
    }

    "simpleFormValidate valid" in {
      val request = FakeRequest(GET, "/?name=test")
      val form = Form(single("name" -> nonEmptyText))
      val result = c.simpleFormValidate(form, request).run(_ => Future.successful(Ok))
      status(result) must be equalTo OK
    }

    "simpleFormValidate invalid" in {
      val request = FakeRequest()
      val form = Form(single("name" -> nonEmptyText))
      val result = c.simpleFormValidate(form, request).run(_ => Future.successful(Ok))
      status(result) must be equalTo BAD_REQUEST
    }

  }
}
