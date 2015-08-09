package com.fuscus.seien.appli.output

import com.fuscus.seien.domain.entity.Issue
import org.joda.time.DateTime
import play.api.libs.json._

case class IssueCreateOutput(
  id: String,
  title: String,
  uri: String,
  healthCheckURI: Option[String],
  description: Option[String],
  createdAt: DateTime,
  updatedAt: DateTime,
  isDeleted: Boolean)

object IssueCreateOutput {

  implicit val faultCreateResponseFormat = Json.format[IssueCreateOutput]

  def transFrom(issue: Issue): IssueCreateOutput = {
    IssueCreateOutput(
      issue.id.value.toString,
      issue.title,
      issue.uri.value.toString,
      issue.healthCheckUri.map(_.toString),
      issue.description,
      issue.createdAt,
      issue.updatedAt,
      issue.isDeleted)
  }
}
