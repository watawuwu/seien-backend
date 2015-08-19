package com.fuscus.seien.domain.entity

import com.fuscus.seien.domain.vo.Category
import com.fuscus.seien.infra.core.{ Entity, UUID, UniversallyUniqueIdentifier }
import org.joda.time.DateTime

/**
 * Created by watawuwu on 15/07/02.
 */
case class DashBoardID(value: UUID = UUID.gen) extends UniversallyUniqueIdentifier

case class DashBoard(
    id: DashBoardID = DashBoardID(),
    category: Category.Val = Category.Tech,
    createdAt: DateTime = DateTime.now(),
    updatedAt: DateTime = DateTime.now(),
    lockVersion: Int = 0,
    isDeleted: Boolean = false) extends Entity[DashBoardID] {
}
