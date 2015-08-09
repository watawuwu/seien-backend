package com.fuscus.seien.appli.input

/**
 * Created by watawuwu on 2014/10/16.
 */
// @todo move to infrastructure layer?
case class PageInput(
  limit: Int,
  offset: Int,
  sort: String) extends Input

// @todo Convert to Enum
sealed abstract class PageSort(val code: String) {
  val name = toString
}

object PageSort {
  case object Desc extends PageSort("desc")
  case object Src extends PageSort("src")

  val values = Array(Desc, Src)

  def contains(code: String): Boolean = values.map(_.code).contains(code)
}
