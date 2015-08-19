package com.fuscus.seien.appli.controller.api

import com.fuscus.seien.appli.cont._
import com.fuscus.seien.appli.input.IssueInput
import com.fuscus.seien.appli.output.CustomSerializer._
import com.fuscus.seien.domain.entity.{ Issue, IssueID }
import com.fuscus.seien.domain.service.IssueServiceRegistry
import com.fuscus.seien.domain.service.IssueServiceRegistry._
import com.fuscus.seien.infra.core.{ ConflictError, UUID }
import com.github.tototoshi.play2.json4s.native._
import org.json4s._
import play.api.Logger
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.validation.Constraints
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

  val showForm =
    Form(mapping("id" -> nonEmptyText(minLength = 36, maxLength = 36)
      .verifying(Constraints.pattern(UUID.pattern))) // LF
      (UUID.apply)(UUID.unapplyString))

  def showCont(uuid: UUID): ActionCont[Issue] = {
    ActionCont {
      (f: Issue => Future[Result]) =>
        service.show(IssueID(uuid)).flatMap {
          case Left(error) =>
            logger.error(error.toString)
            Future.successful(NotFound)
          case Right(issue) =>
            f(issue)
        }
    }
  }

  def show(id: String) = run { implicit request =>
    for {
      uuid <- simpleFormValidate(showForm, Map("id" -> id))
      issue <- showCont(uuid)
    } yield Ok(Extraction.decompose(issue))
  }

  val createForm = Form(
    mapping(
      "title" -> nonEmptyText(maxLength = 128),
      "uri" -> nonEmptyText(maxLength = 1024),
      "healthCheckUri" -> nonEmptyText(maxLength = 1024),
      "description" -> nonEmptyText(maxLength = 1024))(IssueInput.apply)(IssueInput.unapply))

  def createCont(input: IssueInput): ActionCont[Issue] = {
    ActionCont {
      (f: Issue => Future[Result]) =>
        service.create(input.title, input.uri, input.healthCheckUri, input.desc).flatMap {
          case Left(error) => error match {
            case e: ConflictError => Future.successful(Conflict)
            case _ => Future.successful(InternalServerError)
          }
          case Right(issue) =>
            f(issue)
        }
    }
  }

  def create = run { implicit request =>
    for {
      input <- simpleFormValidate(createForm, request)
      issue <- createCont(input)
    } yield Created(Extraction.decompose(issue))
  }
}

