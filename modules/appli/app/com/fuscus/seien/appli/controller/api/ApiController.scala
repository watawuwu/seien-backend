package com.fuscus.seien.appli.controller.api

import com.fuscus.seien.appli.cont._
import play.api.Logger
import play.api.data.Form
import play.api.mvc._

import scala.concurrent.Future

/**
 * Created by watawuwu on 15/08/05.
 */
trait ApiController extends Controller {
  val logger: Logger
  def simpleFormValidate[A](form: Form[A], request: Request[_]) =
    FormCont.hasErrors(form, request)(_ => Future.successful(BadRequest))
}
