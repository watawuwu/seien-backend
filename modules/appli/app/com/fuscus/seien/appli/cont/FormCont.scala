package com.fuscus.seien.appli.cont

import play.api.data.Form
import play.api.libs.json.JsValue
import play.api.mvc.Result
import play.api.mvc.Request
import scala.concurrent.Future

case class FormErrorException[A](
  message: String = null,
  cause: Throwable = null,
  form: Form[A]) extends Exception(message, cause)

object FormCont {
  def apply[A](form: Form[A], request: Request[_]): ActionCont[A] =
    ActionCont(form.bindFromRequest()(request).fold(
      form => Future.failed(FormErrorException(form = form)), _))

  def hasErrors[A](form: Form[A], request: Request[_])(hasErrors: Form[A] => Future[Result]): ActionCont[A] =
    ActionCont(form.bindFromRequest()(request).fold(hasErrors, _))

  def hasErrors[A](form: Form[A], request: Map[String, String])(hasErrors: Form[A] => Future[Result]): ActionCont[A] =
    ActionCont(form.bind(request).fold(hasErrors, _))

  def hasErrors[A](form: Form[A], request: JsValue)(hasErrors: Form[A] => Future[Result]): ActionCont[A] =
    ActionCont(form.bind(request).fold(hasErrors, _))
}

