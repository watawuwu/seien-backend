package com.fuscus.seien.infra.core

import com.eaio.uuid.{ UUID => UnderlyingUUID }

// @todo use the apply function?
case class UUID(private val underlying: UnderlyingUUID) extends Ordered[UUID] {
  override def compare(that: UUID): Int = underlying compareTo that.underlying
  override def toString: String = underlying.toString
  override def hashCode: Int = underlying.hashCode
  def equals(that: UUID): Boolean = underlying.equals(that.underlying)
}

object UUID {
  def apply(string: CharSequence): UUID = this(new UnderlyingUUID(string))
  def apply(): UUID = this(new UnderlyingUUID)
  def apply(time: Long, clockSeqAndNode: Long): UUID = this(new UnderlyingUUID(time, clockSeqAndNode))
}
