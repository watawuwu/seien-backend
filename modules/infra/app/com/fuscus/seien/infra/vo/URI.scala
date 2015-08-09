package com.fuscus.seien.infra.vo

/**
 * Created by watawuwu on 15/07/02.
 */
// @todo extends AnyVal?
// http://docs.scala-lang.org/ja/overviews/core/value-classes.html
case class URI(value: String) extends ValueObject {
  // @todo
  def valid: Boolean = true
}
