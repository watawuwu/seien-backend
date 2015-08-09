package com.fuscus.seien.infra.core

import com.fuscus.seien.infra.util._

/**
 * Created by watawuwu on 2014/09/16.
 */
trait AppRetry {

  lazy val defaultRetryMaxCount: Int = 3
  lazy val defaultRetryWaitMsSecond: Int = 1000
  lazy val defaultRetryExceptions: List[Class[_ <: RuntimeException]] = List(classOf[RuntimeException])

  def retry[A](f: => A,
    retryMaxCount: Option[Int] = None,
    retryWaitMsSecond: Option[Int] = None,
    retryExceptions: Option[List[Class[_ <: RuntimeException]]] = None): A = {

    try {
      Retry.retry(
        retryMaxCount.getOrElse(defaultRetryMaxCount),
        retryWaitMsSecond.getOrElse(defaultRetryWaitMsSecond),
        retryExceptions.getOrElse(defaultRetryExceptions): _*)(f)
    } catch {
      case e: RetryException =>
        // @todo printStackTraceと同等のログ確認
        throw new Error(e.throwables.map(_.toString).toString())
    }
  }
}
