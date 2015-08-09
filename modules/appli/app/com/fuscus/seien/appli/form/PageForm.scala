package com.fuscus.seien.appli.form

import com.fuscus.seien.appli.input.PageInput
import play.api.data.Form
import play.api.data.Forms._

/**
 * Created by watawuwu on 15/07/03.
 */
trait PageForm {

  val DefaultLimit: Int = 25
  val DefaultOffset: Int = 0
  val DefaultSort: String = "desc"

  val pageForm = Form(
    mapping(
      "limit" -> default(number, DefaultLimit),
      "offset" -> default(number, DefaultOffset),
      "sort" -> default(text, DefaultSort))(PageInput.apply)(PageInput.unapply))
}
