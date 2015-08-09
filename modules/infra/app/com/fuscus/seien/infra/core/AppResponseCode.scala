package com.fuscus.seien.infra.core

/**
 * Created by watawuwu on 14/10/24.
 */
trait AppResponseCode {
  val OK = "OK"
  val UNEXPECTED = "Unexpected"
  val NOT_FOUND = "NotFound"
  val BAD_REQUEST = "BadRequest"
  val UNAUTHORIZED = "Unauthorized"
  val FORBIDDEN = "Forbidden"
  val CONFLICT = "Conflict"
  val GONE = "Gone"
  val PRECONDITION_FAILED = "PreconditionFailed"
  val TEAPOT = "Teapot"
  val UNPROCESSABLE_ENTITY = "UnprocessableEntity"
  val LOCKED = "Locked"
  val FAILED_DEPENDENCY = "FailedDependency"
  val UPGRADE_REQUIRED = "UpgradeRequired"

  val INVALIDATE_INPUT = "InvalidateInput"
  val INVALIDATE_ARN = "InvalidateArn"
}

object AppResponseCode extends AppResponseCode
