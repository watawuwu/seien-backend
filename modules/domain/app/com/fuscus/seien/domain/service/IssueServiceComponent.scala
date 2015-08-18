package com.fuscus.seien.domain.service

import com.fuscus.seien.domain.entity.{ Issue, IssueID }
import com.fuscus.seien.domain.repository.IssueRepositoryComponent
import com.fuscus.seien.infra.core._
import com.fuscus.seien.infra.repository.HasJdbcDriver
import com.fuscus.seien.infra.vo.URI
import play.api.{ Logger, Play }
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util._
import java.util.concurrent.TimeoutException

/**
 * Created by watawuwu on 15/07/09.
 */
trait IssueServiceComponent extends Service {
  this: IssueRepositoryComponent =>

  val service: IssueService

  class IssueService {
    def show(id: IssueID): Future[Either[Error, Issue]] = {
      issueRepository.findById(id) map
        (_.toRight(NotFoundError(Some(s"Not found issue. $id"))))
    }

    def index: Future[List[Issue]] = ???

    def create(title: String,
      imageURI: URI,
      healthCheckURI: URI,
      desc: String): Future[Either[Error, Issue]] = {

      val issue = Issue(title, imageURI, healthCheckURI, desc)
      issueRepository.insert(issue).map(_ => Right(issue)).recover {
        case e: TimeoutException =>
          logger.error(e.toString)
          // @todo Organize the error class
          Left(ConflictError(Some(e.toString)))
        case e =>
          logger.error(e.toString)
          Left(UnknownError(Some(e.toString)))
      }
    }
  }
}

object IssueServiceRegistry
    extends IssueServiceComponent
    with IssueRepositoryComponent
    with HasJdbcDriver
    with AppLogger {
  lazy val service = new IssueService
  lazy val issueRepository = new IssueRepository
  lazy val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)
  lazy val logger = Logger(this.getClass)
}
