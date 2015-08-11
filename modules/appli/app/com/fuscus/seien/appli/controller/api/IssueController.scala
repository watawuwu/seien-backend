package com.fuscus.seien.appli.controller.api

import com.fuscus.seien.appli.cont._
import com.fuscus.seien.appli.input.IssueInput
import com.fuscus.seien.appli.output.CustomSerializer._
import com.fuscus.seien.domain.entity.{ Issue, IssueID }
import com.fuscus.seien.domain.service.IssueServiceRegistry
import com.fuscus.seien.domain.service.IssueServiceRegistry._
import com.fuscus.seien.infra.core.UUID
import org.json4s._
import com.github.tototoshi.play2.json4s.native._
import play.api.Logger
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc._

import scala.concurrent.Future

object IssueController extends IssueController {
  val logger = Logger(this.getClass)
  lazy val service = IssueServiceRegistry.service
}

trait IssueController extends ApiController with Json4s {

  // @todo to import object
  implicit lazy val formats = (DefaultFormats + URISerializer + IssueIDSerializer) ++ org.json4s.ext.JodaTimeSerializers.all

  val service: IssueService

  def showCont(id: String): ActionCont[Issue] = {
    ActionCont {
      (f: Issue => Future[Result]) =>
        service.show(IssueID(UUID(id))).flatMap {
          case Left(error) =>
            logger.error(error.toString)
            Future.successful(NotFound)
          case Right(issue) =>
            f(issue)
        }
    }
  }

  def show(id: String) = run { implicit request =>
    for (issue <- showCont(id)) yield Ok(Extraction.decompose(issue))
  }

  val form = Form(
    mapping(
      "title" -> nonEmptyText(maxLength = 128),
      "uri" -> nonEmptyText(maxLength = 1024),
      "healthCheckUri" -> nonEmptyText(maxLength = 1024),
      "description" -> nonEmptyText(maxLength = 1024))(IssueInput.apply)(IssueInput.unapply))

  def createCont(input: IssueInput): ActionCont[Issue] = {
    ActionCont {
      (f: Issue => Future[Result]) =>
        service.create(
          input.title,
          input.uri,
          input.healthCheckUri,
          input.desc) match {
            case Left(error) =>
              logger.error(error.toString)
              Future.successful(Conflict)
            case Right(issue) =>
              f(issue)
          }
    }
  }

  def create = run { implicit request =>
    for {
      input <- simpleFormValidate(form, request)
      issue <- createCont(input)
    } yield Created(Extraction.decompose(issue))
  }
}

