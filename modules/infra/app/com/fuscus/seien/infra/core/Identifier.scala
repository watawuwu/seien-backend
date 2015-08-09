package com.fuscus.seien.infra.core

/**
 * Created by watawuwu on 15/07/02.
 */
trait Identifier[+A] {
  def value: A

  override def equals(that: Any): Boolean = that match {
    case identifier: Identifier[_] => this.value == identifier.value
    case _ => false
  }

  override def toString: String = value.toString

  override def hashCode: Int = value.hashCode
}

trait UniversallyUniqueIdentifier extends Identifier[UUID]

