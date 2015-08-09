package com.fuscus.seien.infra.core

import play.cache.Cache

class AppCache {

  def cacheKey(obj: Any, identifiers: Seq[(Any, Any)]) = {
    obj.getClass.getSimpleName + ":" + concatIds(identifiers)
  }

  private def concatIds(identifiers: Seq[(Any, Any)]) = {
    identifiers.map(e => e._1.toString + "/" + e._2.toString).mkString(":")
  }

  def find[T](key: String) = Cache.get(key) match {
    case null => None
    case cached => Some(cached.asInstanceOf[T])
  }

  def store(key: String, obj: Any) = Cache.set(key, obj)

  def destroy(key: String) = Cache.remove(key)
}
