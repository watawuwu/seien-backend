package com.fuscus.seien.infra.core

trait Enum {

  type Val <: EnumVal

  private var values_ = List[Val]()
  private var valuesById_ = Map[Int, Val]()
  private var valuesByName_ = Map[String, Val]()

  def values = values_
  def valuesById = valuesById_
  def valuesByName = valuesByName_

  def apply(id: Int) = valuesById.get(id) // Some|None
  def apply(name: String) = valuesByName.get(name) // Some|None
  def contains(id: Int): Boolean = valuesById_.keySet.contains(id)
  def contains(name: String): Boolean = valuesByName_.keySet.contains(name)

  // Base class for enum values; it registers the value with the Enum.
  protected abstract class EnumVal(val id: Int, val label: Option[String] = None) extends Ordered[Val] {
    val theVal = this.asInstanceOf[Val] // only extend EnumVal to Val
    def name = label.getOrElse(toString)

    def compare(that: Val) = this.id - that.id
    def init() {
      if (valuesById_.get(id).isDefined) {
        throw new Exception("cannot init " + this + " enum value twice")
      }
      values_ ++= List(theVal)
      valuesById_ += (id -> theVal)
      valuesByName_ += (name -> theVal)
    }
  }
}
