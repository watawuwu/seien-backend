package com.fuscus.seien.infra.util

import mojolly.inflector.Inflector

// @todo create autoImport
object StringUtil {

  implicit class RichString(val self: String) extends AnyVal {

    private def camelify(str: String): String = {
      def loop(x: List[Char]): List[Char] = (x: @unchecked) match {
        case '_' :: '_' :: rest => loop('_' :: rest)
        case '_' :: c :: rest => Character.toUpperCase(c) :: loop(rest)
        case '_' :: Nil => Nil
        case c :: rest => Character.toLowerCase(c) :: loop(rest)
        case Nil => Nil
      }
      if (str == null)
        ""
      else if (!str.contains('-') && !str.contains('_')) {
        str
      } else {
        loop('_' :: str.toList).mkString
      }
    }

    def camelifyMethod(str: String): String = {
      val tmp: String = camelify(str)
      if (tmp.length == 0)
        ""
      else
        tmp.substring(0, 1).toLowerCase + tmp.substring(1)
    }

    def toLowerCamelCase: String = camelifyMethod(self.replaceAll("-", "_"))

    def toUpperCamelCase: String = camelify((self.head.toString.toUpperCase + self.tail).replaceAll("-", "_"))

    private def snakify(str: String): String = str.replaceAll("([A-Z]+)([A-Z][a-z])", "$1_$2").replaceAll("([a-z\\d])([A-Z])", "$1_$2").toLowerCase

    def toLowerSnakeCase: String = snakify(self.replaceAll("-", "_"))

    def toUpperSnakeCase: String = snakify(self.replaceAll("-", "_")).toUpperCase

    private def chainfy(str: String): String = snakify(str).replaceAll("_", "-")

    def toLowerChainCase: String = chainfy(self)

    def toUpperChainCase: String = chainfy(self).toUpperCase

    def pluralize: String = Inflector.singularize(self)
  }
}

