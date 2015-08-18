package com.fuscus.seien.domain.service

import com.fuscus.seien.domain.entity.{ Seien, SeienID }
import com.fuscus.seien.domain.repository.SeienRepositoryComponent
import com.fuscus.seien.infra.core.AppLogger
import com.fuscus.seien.infra.repository.HasJdbcDriver
import play.api.{ Logger, Play }
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile

import scala.concurrent.Future

/**
 * Created by watawuwu on 15/07/27.
 */
trait SeienServiceComponent extends Service {
  this: SeienRepositoryComponent =>

  val service: SeienService

  class SeienService {
    def show(id: SeienID): Future[Option[Seien]] = ???
    def index: Future[List[Seien]] = ???
  }
}

object SeienServiceRegistry
    extends SeienServiceComponent
    with SeienRepositoryComponent
    with HasJdbcDriver
    with AppLogger {
  val service = new SeienService
  val seienRepository = new SeienRepository
  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)
  lazy val logger = Logger(this.getClass)
}

