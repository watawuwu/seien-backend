package com.fuscus.seien.infra.core

import play.api.data.Form

/**
 * Extends From for application.
 *
 * Created by watawuwu on 2014/09/12.
 */
object AppForm {

  implicit class AppForm[A](val form: Form[A]) extends AnyVal {
    def allErrorMessage() = {
      form.errors.map(error => s"${error.key}: ${error.message}").mkString(",")
    }
  }
}
