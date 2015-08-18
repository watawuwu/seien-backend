package com.fuscus.seien.domain.service

import com.fuscus.seien.domain.entity.{ Issue, IssueID }
import com.fuscus.seien.domain.repository.IssueRepositoryComponent
import com.fuscus.seien.infra.core.{ NotFoundError, ConflictError, Error }
import com.fuscus.seien.infra.repository.HasJdbcDriver
import com.fuscus.seien.infra.vo.URI
import play.api.{ Logger, Play }
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util._

/**
 * Created by watawuwu on 15/07/09.
 */
trait IssueServiceComponent extends Service {
  this: IssueRepositoryComponent =>

  val service: IssueService

  class IssueService {
    def show(id: IssueID): Future[Either[Error, Issue]] = {
      issueRepository.findById(id) map
        (_.toRight(new NotFoundError(s"Not found issue. $id")))
    }

    def index: Future[List[Issue]] = ???

    // @todo change return type Future[Either[Error, Issue]]
    def create(title: String,
      imageURI: URI,
      healthCheckURI: URI,
      desc: String): Either[Error, Issue] = {

      val issue = Issue(title, imageURI, healthCheckURI, desc)
      issueRepository.insert(issue)

      // @todo error handling
      Right(issue)
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
