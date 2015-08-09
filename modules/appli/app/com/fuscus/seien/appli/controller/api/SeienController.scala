package com.fuscus.seien.appli.controller.api

import com.fuscus.seien.domain.service.SeienServiceRegistry
import com.fuscus.seien.domain.service.SeienServiceRegistry._
import play.api.Logger

object SeienController extends SeienController {
  val logger = Logger(this.getClass)
  val seienRepository = SeienServiceRegistry.seienRepository
}

trait SeienController extends ApiController {
  val seienRepository: SeienRepository

  def create(issueID: String) = TODO
  def show(id: String) = TODO
}

