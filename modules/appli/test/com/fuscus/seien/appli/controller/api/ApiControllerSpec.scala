package com.fuscus.seien.appli.controller.api

import com.fuscus.seien.appli.cont.ActionCont
import com.fuscus.seien.appli.controller.AppController
import com.fuscus.seien.appli.controller.AppControllerSpec._
import com.fuscus.seien.appli.data.TestContext
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import play.api.Logger
import play.api.mvc._
import play.api.test.{ FakeRequest, PlaySpecification }
import scala.concurrent.{ ExecutionContext, Future }
import play.api.libs.concurrent.Execution.Implicits.defaultContext

/**
 * Created by wataru.matsui on 15/08/11.
 */
object ApiControllerSpec extends PlaySpecification with Results with Mockito with TestContext {
  isolated

  val c = new ApiController {
    val logger = mock[Logger]
  }

  "ApiControllerSpec " should {
    "run valid" in {
      // @todo something different (・ω<)
      val result = c.run(_ => ActionCont.successful(Ok)).apply(FakeRequest())
      status(result) must be equalTo OK
      (c.corsResponseHeaderSeq ++ c.securityResponseHeaderSeq).forall {
        case (k, v) =>
          header(k, result) === Some(v)
      } === true
    }
  }

}
