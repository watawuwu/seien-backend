package com.fuscus.seien.appli.controller.api

import play.api.Logger
import play.api.mvc._

object DashboardController extends DashboardController {
  val logger = Logger(this.getClass)

}

trait DashboardController extends ApiController {
  def index = TODO
}

