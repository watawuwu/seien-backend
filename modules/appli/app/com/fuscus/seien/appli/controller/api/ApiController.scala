package com.fuscus.seien.appli.controller.api

import com.fuscus.seien.appli.cont._
import com.fuscus.seien.appli.controller.AppController
import play.api.mvc._

import scala.concurrent.{ ExecutionContext, Future }

/**
 * Created by watawuwu on 15/08/05.
 */
trait ApiController extends AppController {
  def run(f: Request[AnyContent] => ActionCont[Result])(implicit ec: ExecutionContext): Action[AnyContent] = {
    Action.async { implicit request =>
      (for {
        _ <- responseHeaderCont(request)
        result <- f(request)
      } yield result).run(Future.successful)
    }
  }
}

