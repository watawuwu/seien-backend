package com.fuscus.seien.domain.repository

import com.fuscus.seien.domain.entity.{ DashBoard, DashBoardID }
import com.fuscus.seien.domain.vo.Category
import com.fuscus.seien.infra.core.UUID
import com.fuscus.seien.infra.repository.{ HasJdbcDriver, IdentifiableTable, RepositoryComponent }
import org.joda.time.DateTime
import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile

trait DashBoardRepositoryComponent extends RepositoryComponent[DashBoard, DashBoardID] {
  this: HasJdbcDriver =>

  import TypeMappers._
  import driver.api._

  val dashboardRepository: DashBoardRepository

  implicit val idColumnType = MappedColumnType.base[DashBoardID, String](_.toString, a => DashBoardID(UUID(a)))

  // @todo don't use get
  implicit val categoryColumnType = MappedColumnType.base[Category.Val, Int](_.id, x => Category(x).get)

  class DashBoardRepository extends Repository[DashBoardTable] {
    val query = TableQuery[DashBoardTable]
  }

  class DashBoardTable(_tableTag: Tag)
      extends Table[DashBoard](_tableTag, "dashboard")
      with IdentifiableTable[DashBoardID] {

    def * = (id, category, createdAt, updateAt, lockVersion, isDeleted) <> (DashBoard.tupled, DashBoard.unapply)

    override def id: Rep[DashBoardID] = column[DashBoardID]("id", O.PrimaryKey, O.Length(36, varying = false))
    val category: Rep[Category.Val] = column[Category.Val]("category")
    val createdAt: Rep[DateTime] = column[DateTime]("created_at")
    val updateAt: Rep[DateTime] = column[DateTime]("update_at")
    val lockVersion: Rep[Int] = column[Int]("lock_version")
    val isDeleted: Rep[Boolean] = column[Boolean]("is_deleted")
  }

}
