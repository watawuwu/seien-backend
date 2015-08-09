package com.fuscus.seien.infra.core

import play.api.Play.current
import play.api.libs.concurrent.Akka

import scala.concurrent.ExecutionContext

/**
 * Custom context implicit val list.
 *
 * Created by watawuwu on 2014/09/11.
 */
object AppContext {
  implicit val jobExecutionContext: ExecutionContext = Akka.system.dispatchers.lookup("context.job")
  implicit val expensiveOnlineExecutionContext: ExecutionContext = Akka.system.dispatchers.lookup("context.expensive-online")
}
