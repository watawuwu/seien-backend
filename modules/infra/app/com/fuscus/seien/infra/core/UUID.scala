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
  def apply(time: Long, clockSeqAndNode: Long): UUID = this(new UnderlyingUUID(time, clockSeqAndNode))
  def unapplyString(value: UUID): Option[String] = Some(value.toString)
  def gen: UUID = this(new UnderlyingUUID)
  val pattern = """[0-9a-z\-]{8}-[0-9a-z\-]{4}-[0-9a-z\-]{4}-[0-9a-z\-]{4}-[0-9a-z\-]{12}""".r
}
