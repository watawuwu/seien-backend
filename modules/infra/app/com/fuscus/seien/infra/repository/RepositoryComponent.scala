package com.fuscus.seien.infra.repository

import com.fuscus.seien.infra.core.{ Entity, Identifier }
import play.api.libs.concurrent.Execution.Implicits.defaultContext

import scala.concurrent.Future

trait RepositoryComponent[E <: Entity[PK], PK <: Identifier[_]] extends ColumnTypeMapper {
  self: HasJdbcDriver =>

  import driver.api._

  // @todo Implement uuid
  implicit def idColumnType: BaseColumnType[PK]

  // (implicit session: Session)
  abstract class Repository[T <: Table[E] with IdentifiableTable[PK]] extends TypeMappers {

    val query: TableQuery[T]

    private def filterQuery(id: PK): Query[T, E, Seq] =
      query.filter(_.id === id)

    def findById(id: PK): Future[Option[E]] =
      db.run(filterQuery(id).result.headOption)

    def all: Future[List[E]] =
      db.run(query.result).map(_.toList)

    def count: Future[Int] =
      db.run(query.length.result)

    def insert(entity: E): Future[Unit] =
      db.run(query += entity).map(_ => ())

    def insert(entity: List[E]): Future[Unit] =
      db.run(query ++= entity).map(_ => ())

    def delete(id: PK): Future[Unit] =
      db.run(filterQuery(id).delete).map(_ => ())

    def update(entity: E): Future[Unit] =
      db.run(filterQuery(entity.id).update(entity)).map(_ => ())

    def list(limit: Int, offset: Int = 0): Future[List[E]] =
      db.run(query.drop(limit).take(offset).result).map(_.toList)
  }
}
