package com.fuscus.seien.appli.controller

import com.fuscus.seien.appli.cont.{ FormCont, ActionCont }
import com.fuscus.seien.infra.core.AppLogger
import play.api.Logger
import play.api.data.Form
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.JsValue
import play.api.mvc._

import scala.concurrent.Future

/**
 * Created by watawuwu on 15/07/02.
 */
trait AppController extends Controller with AppLogger {

  val securityResponseHeaderSeq = List(
    "Content-Type-Options" -> "nosniff",
    "X-XSS-Protection" -> "1; mode=block",
    "X-Frame-Options" -> "deny",
    "Content-Security-Policy" -> "default-src 'none'")

  val corsResponseHeaderSeq = Seq(
    "Access-Control-Allow-Origin" -> "*",
    "Access-Control-Allow-Methods" -> "GET, POST, DELETE, PUT")

  def corsCont(request: Request[AnyContent]): ActionCont[Request[AnyContent]] =
    ActionCont { (f: Request[AnyContent] => Future[Result]) =>
      f(request).map(_.withHeaders(corsResponseHeaderSeq: _*))
    }

  def securityHeaderCont(request: Request[AnyContent]): ActionCont[Request[AnyContent]] =
    ActionCont { (f: Request[AnyContent] => Future[Result]) =>
      f(request).map(_.withHeaders(securityResponseHeaderSeq: _*))
    }

  def responseHeaderCont(request: Request[AnyContent]): ActionCont[Request[AnyContent]] = {
    for {
      _ <- corsCont(request)
      cont <- securityHeaderCont(request)
    } yield cont
  }

  def simpleFormValidate[A](form: Form[A], request: Request[_]): ActionCont[A] =
    FormCont.hasErrors(form, request)(_ => Future.successful(BadRequest))

  def simpleFormValidate[A](form: Form[A], request: Map[String, String]): ActionCont[A] =
    FormCont.hasErrors(form, request)(_ => Future.successful(BadRequest))

  def simpleFormValidate[A](form: Form[A], request: JsValue): ActionCont[A] =
    FormCont.hasErrors(form, request)(_ => Future.successful(BadRequest))
}
