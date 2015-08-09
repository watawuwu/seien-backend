package com.fuscus.seien.infra.core

/**
 * Created by watawuwu on 15/07/02.
 */
trait Entity[ID <: Identifier[_]] {
  val id: ID
  override def equals(that: Any): Boolean = that match {
    case entity: Entity[_] => this.id == entity.id
    case _ => false
  }
  override def hashCode: Int = id.hashCode
}
