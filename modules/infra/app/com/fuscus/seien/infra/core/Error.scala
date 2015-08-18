package com.fuscus.seien.infra.core

/**
 * Common exception for application.
 *
 * Created by watawuwu on 2014/08/18.
 */
trait Error extends AppResponseCode {
  val message: Option[String]
  val code: String
}

case class BadRequestError(message: Option[String] = None) extends Error {
  val code = BAD_REQUEST
}

case class UnauthorizedError(message: Option[String] = None) extends Error {
  val code = UNAUTHORIZED
}

case class ForbiddenError(message: Option[String] = None) extends Error {
  val code = FORBIDDEN
}

case class NotFoundError(message: Option[String] = None) extends Error {
  val code = NOT_FOUND
}

case class ConflictError(message: Option[String] = None) extends Error {
  val code = CONFLICT
}

case class GoneError(message: Option[String] = None) extends Error {
  val code = GONE
}

case class PreconditionFailedError(message: Option[String] = None) extends Error {
  val code = PRECONDITION_FAILED
}

case class TeaPotError(message: Option[String] = None) extends Error {
  val code = TEAPOT
}

case class UnprocessableEntityError(message: Option[String] = None) extends Error {
  val code = UNPROCESSABLE_ENTITY
}

case class LockedError(message: Option[String] = None) extends Error {
  val code = LOCKED
}

case class FailedDependencyError(message: Option[String] = None) extends Error {
  val code = FAILED_DEPENDENCY
}

case class UpgradeRequiredError(message: Option[String] = None) extends Error {
  val code = UPGRADE_REQUIRED
}

case class UnknownError(message: Option[String] = None) extends Error {
  val code = UNKNOWN
}

