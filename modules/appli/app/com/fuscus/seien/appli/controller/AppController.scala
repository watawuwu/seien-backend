package com.fuscus.seien.appli.controller

import org.apache.commons.lang3.exception.ExceptionUtils
import play.api.Logger
import play.api.mvc._
import scala.concurrent.Future
import play.api.mvc.Results._

/**
 * Created by watawuwuon 15/07/02.
 */
trait AppController {

  val defaultResponseHeader = List(
    "Content-Type-Options" -> "nosniff",
    "X-XSS-Protection" -> "1; mode=block",
    "X-Frame-Options" -> "deny",
    "Content-Security-Policy" -> "default-src 'none'")

  def handleAction[A](action: Action[A], request: Request[A]): Future[Result] = {
    try {
      action(request)
    } catch {
      case e: Exception =>
        val stackTrace = ExceptionUtils.getStackTrace(e)
        Logger.error(s"${e.getMessage} stackTrace: $stackTrace")
        Future.successful(InternalServerError)
    }
  }
}
