package com.fuscus.seien.infra.repository

import java.sql.{ Date, Timestamp }

import com.fuscus.seien.infra.vo.URI
import org.joda.time.{ DateTime, LocalDate }

trait ColumnTypeMapper {
  self: HasJdbcDriver =>

  import driver.api._

  trait TypeMappers {

    implicit val dateTimeMapper: BaseColumnType[DateTime] = MappedColumnType.base[DateTime, Timestamp](
      dt => new Timestamp(dt.getMillis),
      ts => new DateTime(ts.getTime)
    )

    implicit val localDateMapper: BaseColumnType[LocalDate] = MappedColumnType.base[LocalDate, Date](
      dt => new Date(dt.toDate.getTime),
      d => new LocalDate(d.getTime)
    )

    implicit val uriColumnType = MappedColumnType.base[URI, String](_.value, URI)
  }

  object TypeMappers extends TypeMappers
}
