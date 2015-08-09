package com.fuscus.seien.domain.repository

import com.fuscus.seien.domain.entity._
import com.fuscus.seien.infra.core.UUID
import com.fuscus.seien.infra.repository.{ HasJdbcDriver, RepositoryComponent, IdentifiableTable }
import org.joda.time.DateTime
import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile

trait SeienRepositoryComponent extends RepositoryComponent[Seien, SeienID] {
  this: HasJdbcDriver =>

  import driver.api._
  import TypeMappers._

  val seienRepository: SeienRepository

  implicit val idColumnType = MappedColumnType.base[SeienID, String](_.toString, a => SeienID(UUID(a)))
  implicit val faultIdColumnType = MappedColumnType.base[IssueID, String](_.toString, a => IssueID(UUID(a)))

  class SeienRepository extends Repository[SeienTable] {
    val query = TableQuery[SeienTable]
  }

  class SeienTable(_tableTag: Tag)
      extends Table[Seien](_tableTag, "seien")
      with IdentifiableTable[SeienID] {

    def * = (id, message, name, linkCount, faultId, createdAt, updateAt, lockVersion, isDeleted) <> (Seien.tupled, Seien.unapply)

    override def id: Rep[SeienID] = column[SeienID]("id", O.PrimaryKey, O.Length(36, varying = false))
    val message: Rep[String] = column[String]("message", O.Length(258, varying = true))
    val name: Rep[Option[String]] = column[Option[String]]("name", O.Length(32, varying = true), O.Default(None))
    val linkCount: Rep[Int] = column[Int]("link_count")
    val faultId: Rep[IssueID] = column[IssueID]("fault_id", O.Length(36, varying = false))
    val createdAt: Rep[DateTime] = column[DateTime]("created_at")
    val updateAt: Rep[DateTime] = column[DateTime]("update_at")
    val lockVersion: Rep[Int] = column[Int]("lock_version")
    val isDeleted: Rep[Boolean] = column[Boolean]("is_deleted")
  }
}
