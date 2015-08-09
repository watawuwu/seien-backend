package com.fuscus.seien.infra.repository

import play.api.db.slick.HasDatabaseConfig
import slick.driver.JdbcProfile

/**
 * Created by watawuwu on 15/07/24.
 */
trait HasJdbcDriver extends HasDatabaseConfig[JdbcProfile]
