package com.fuscus.seien.domain.vo

import com.fuscus.seien.infra.core.Enum

/**
 * Created by watawuwu on 15/07/27.
 */
object Category extends Enum {
  sealed abstract class Val(id: Int) extends EnumVal(id)

  case object Tech extends Val(1) { Tech.init() }
  case object Neta extends Val(2) { Neta.init() }

  private val init = List(Tech, Neta)
}
