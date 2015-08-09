package com.fuscus.seien.appli.data

import com.fuscus.seien.appli.output.CustomSerializer._
import com.fuscus.seien.domain.entity.Issue
import com.fuscus.seien.infra.vo.URI
import org.json4s._
import org.json4s.native.JsonMethods._
import org.specs2.specification.Scope

case class Test(name: String, uri: URI)
case class Test2(name: String, uri: String)
case class Child(name: String, age: Int, birthdate: Option[java.util.Date])
case class Address(street: String, city: String)
case class Person(name: String, address: Address, children: List[Child])

/**
 * Created by watawuwu on 15/08/08.
 */
trait TestContext extends Scope with DummyData {
  val dataPath = "test/com/fuscus/seien/appli/data/"

  implicit lazy val formats = (DefaultFormats + URISerializer + IssueIDSerializer) ++ org.json4s.ext.JodaTimeSerializers.all

  lazy val expectIssue: Issue = {
    parse(readFile("issue.json")).extract[Issue]
  }
}
