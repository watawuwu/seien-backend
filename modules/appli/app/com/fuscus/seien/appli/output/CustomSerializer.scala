package com.fuscus.seien.appli.output

import com.fuscus.seien.domain.entity.IssueID
import com.fuscus.seien.infra.core.UUID
import com.fuscus.seien.infra.vo.URI
import org.json4s._

/**
 * Created by watawuwu on 15/08/09.
 */
// @todo move to infrastructure layer
object CustomSerializer {
  import JsonDSL._

  class URISerializer extends Serializer[URI] {

    private val URIClass = classOf[URI]

    def deserialize(implicit format: Formats): PartialFunction[(TypeInfo, JValue), URI] = {
      case (TypeInfo(URIClass, _), json) => json match {
        case JString(id) => URI(id)
        case x => throw new MappingException("Can't convert " + x + " to URI")
      }
    }

    def serialize(implicit formats: Formats): PartialFunction[Any, JValue] = {
      case x: URI => x.value
    }
  }

  object URISerializer extends URISerializer

  // @todo generics
  class IssueIDSerializer extends Serializer[IssueID] {

    private val UUIDClass = classOf[IssueID]

    def deserialize(implicit format: Formats): PartialFunction[(TypeInfo, JValue), IssueID] = {
      case (TypeInfo(UUIDClass, _), json) => json match {
        case JString(id) => IssueID(UUID(id))
        case x => throw new MappingException("Can't convert " + x + " to IssueID")
      }
    }

    def serialize(implicit formats: Formats): PartialFunction[Any, JValue] = {
      case x: IssueID => x.value.toString
    }
  }

  object IssueIDSerializer extends IssueIDSerializer
}

