package com.fuscus.seien.infra.repository

import slick.lifted.Rep

/**
 * Created by watawuwu on 15/07/23.
 */
trait IdentifiableTable[A] {
  def id: Rep[A]
}
