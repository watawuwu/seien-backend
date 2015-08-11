package com.fuscus.seien.appli.data

import scala.io.Source

/**
 * Created by watawuwu on 15/01/08.
 */
trait DummyData {
  val dataPath: String

  def readFile(fileName: String): String = {
    Source.fromFile(dataPath + fileName).mkString
  }
}
