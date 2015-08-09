package com.fuscus.seien.infra.core

/**
 * Common exception for application.
 *
 * Created by watawuwu on 2014/08/18.
 */
class Error(
  message: String,
  code: String = AppResponseCode.UNEXPECTED)
    extends RuntimeException(message) with AppResponseCode {

  def getCode = code
}

class BadRequestError(
  message: String,
  code: String = AppResponseCode.BAD_REQUEST)
    extends Error(message, code) {
}

class UnauthorizedError(
  message: String,
  code: String = AppResponseCode.UNAUTHORIZED)
    extends Error(message, code) {
}

class ForbiddenError(
  message: String,
  code: String = AppResponseCode.FORBIDDEN)
    extends Error(message, code) {
}

class NotFoundError(
  message: String,
  code: String = AppResponseCode.NOT_FOUND)
    extends Error(message, code) {
}

class ConflictError(
  message: String,
  code: String = AppResponseCode.CONFLICT)
    extends Error(message, code) {
}

class GoneError(
  message: String,
  code: String = AppResponseCode.GONE)
    extends Error(message, code) {
}

class PreconditionFailedError(
  message: String,
  code: String = AppResponseCode.PRECONDITION_FAILED)
    extends Error(message, code) {
}

class TeaPotError(
  message: String,
  code: String = AppResponseCode.TEAPOT)
    extends Error(message, code) {
}

class UnprocessableEntityError(
  message: String,
  code: String = AppResponseCode.UNPROCESSABLE_ENTITY)
    extends Error(message, code) {
}

class LockedError(
  message: String,
  code: String = AppResponseCode.LOCKED)
    extends Error(message, code) {
}

class FailedDependencyError(
  message: String,
  code: String = AppResponseCode.FAILED_DEPENDENCY)
    extends Error(message, code) {
}

class UpgradeRequiredError(
  message: String,
  code: String = AppResponseCode.UPGRADE_REQUIRED)
    extends Error(message, code) {
}
