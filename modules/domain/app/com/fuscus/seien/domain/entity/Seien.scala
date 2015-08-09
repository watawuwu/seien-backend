package com.fuscus.seien.domain.entity

import com.fuscus.seien.infra.core.{ UUID, Entity, Identifier }
import org.joda.time.DateTime

/**
 * Created by watawuwu on 15/07/02.
 */
case class SeienID(value: UUID = UUID()) extends Identifier[UUID]

// @todo Abstraction of the base class
case class Seien(
    id: SeienID = SeienID(),
    message: String,
    name: Option[String] = None,
    likeCount: Int = 0,
    faultID: IssueID,
    createdAt: DateTime = DateTime.now(),
    updatedAt: DateTime = DateTime.now(),
    lockVersion: Int = 0,
    isDeleted: Boolean = false) extends Entity[SeienID] {

  def cheer(): Unit = {}
}

// @todo workaround
// https://issues.scala-lang.org/browse/SI-3664
object Seien extends ((SeienID, String, Option[String], Int, IssueID, DateTime, DateTime, Int, Boolean) => Seien) {

  def apply(
    message: String,
    name: Option[String],
    faultID: IssueID): Seien = Seien(id = SeienID(), message = message, name = name, faultID = faultID)
}
