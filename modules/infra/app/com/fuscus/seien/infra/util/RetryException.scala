package com.fuscus.seien.infra.util

case class RetryException(throwables: List[Throwable]) extends Exception(throwables.toString())

object Retry {

  import scala.util.control.Exception.allCatch

  def retry[T](retryLimit: Int)(f: => T): T =
    retry(retryLimit, 0, classOf[Throwable])(f)

  def retry[T](retryLimit: Int, retryInterval: Int)(f: => T): T =
    retry(retryLimit, retryInterval, classOf[Throwable])(f)

  def retry[T](retryLimit: Int, catchExceptionClasses: Class[_]*)(f: => T): T =
    retry(retryLimit, 0, e => catchExceptionClasses.exists(_.isAssignableFrom(e.getClass)))(f)

  def retry[T](retryLimit: Int, shouldCatch: Throwable => Boolean)(f: => T): T =
    retry(retryLimit, 0, shouldCatch)(f)

  def retry[T](retryLimit: Int, retryInterval: Int, catchExceptionClasses: Class[_]*)(f: => T): T =
    retry(retryLimit, retryInterval, e => catchExceptionClasses.exists(_.isAssignableFrom(e.getClass)))(f)

  private def retry[T](retryLimit: Int, retryInterval: Int, shouldCatch: Throwable => Boolean)(f: => T): T = {
    @annotation.tailrec
    def go(errors: List[Throwable], f: => T): T = {
      allCatch.either(f) match {
        case Right(r) => r
        case Left(e) =>
          if (shouldCatch(e)) {
            if (errors.size < retryLimit - 1) {
              Thread.sleep(retryInterval.toLong)
              go(e :: errors, f)
            } else {
              throw RetryException(e :: errors)
            }
          } else throw e
      }
    }
    go(Nil, f)
  }
}

