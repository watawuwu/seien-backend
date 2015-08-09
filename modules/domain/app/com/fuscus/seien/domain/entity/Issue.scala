package com.fuscus.seien.domain.entity

import com.fuscus.seien.infra.core._
import com.fuscus.seien.infra.vo.URI
import org.joda.time.DateTime

/**
 * Created by watawuwu on 15/07/02.
 */

// @todo is better to use a type alias
case class IssueID(value: UUID = UUID()) extends UniversallyUniqueIdentifier

case class Issue(
    id: IssueID = IssueID(),
    title: String,
    uri: URI,
    healthCheckUri: Option[URI] = None,
    description: Option[String] = None,
    createdAt: DateTime = DateTime.now(),
    updatedAt: DateTime = DateTime.now(),
    lockVersion: Int = 0,
    isDeleted: Boolean = false) extends Entity[IssueID] {
}

// @todo workaround
// https://issues.scala-lang.org/browse/SI-3664
object Issue extends ((IssueID, String, URI, Option[URI], Option[String], DateTime, DateTime, Int, Boolean) => Issue) {

  def apply(
    title: String,
    uri: URI,
    healthCheckURI: URI,
    description: String): Issue = {
    Issue(title = title, uri = uri, healthCheckUri = Some(healthCheckURI), description = Some(description))
  }
}
