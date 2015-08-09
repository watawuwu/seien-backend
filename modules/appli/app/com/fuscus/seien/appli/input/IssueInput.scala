package com.fuscus.seien.appli.input

import com.fuscus.seien.infra.vo.URI

/**
 * Created by watawuwu on 15/07/02.
 */
case class IssueInput(
    title: String,
    uriString: String,
    healthCheckUriString: String,
    desc: String) extends Input {

  // @todo Where to conversion
  val uri = URI(uriString)
  val healthCheckUri = URI(healthCheckUriString)
}
