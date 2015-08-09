package com.fuscus.seien.domain.repository

import com.fuscus.seien.domain.entity.{ Issue, IssueID }
import com.fuscus.seien.infra.core.UUID
import com.fuscus.seien.infra.repository.{ HasJdbcDriver, IdentifiableTable, RepositoryComponent }
import com.fuscus.seien.infra.vo.URI
import org.joda.time.DateTime

trait IssueRepositoryComponent extends RepositoryComponent[Issue, IssueID] {
  this: HasJdbcDriver =>

  import TypeMappers._
  import driver.api._

  val issueRepository: IssueRepository

  implicit val idColumnType = MappedColumnType.base[IssueID, String](_.toString, a => IssueID(UUID(a)))

  class IssueRepository extends Repository[IssueTable] {
    val query = TableQuery[IssueTable]
  }

  class IssueTable(_tableTag: Tag)
      extends Table[Issue](_tableTag, "issue")
      with IdentifiableTable[IssueID] {

    def * = (id, title, uri, healthCheckUri, description, createdAt, updateAt, lockVersion, isDeleted) <> (Issue.tupled, Issue.unapply)

    override def id: Rep[IssueID] = column[IssueID]("id", O.PrimaryKey, O.Length(36, varying = false))
    val title: Rep[String] = column[String]("title", O.Length(255, varying = true))
    val uri: Rep[URI] = column[URI]("uri", O.Length(1024, varying = true))
    val healthCheckUri: Rep[Option[URI]] = column[Option[URI]]("health_check_uri", O.Length(1024, varying = true))
    val description: Rep[Option[String]] = column[Option[String]]("description", O.Length(1024, varying = true), O.Default(None))
    val createdAt: Rep[DateTime] = column[DateTime]("created_at")
    val updateAt: Rep[DateTime] = column[DateTime]("update_at")
    val lockVersion: Rep[Int] = column[Int]("lock_version")
    val isDeleted: Rep[Boolean] = column[Boolean]("is_deleted")
  }

}
