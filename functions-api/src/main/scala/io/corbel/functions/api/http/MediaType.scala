package io.corbel.functions.api.http

import MediaType._


/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
case class MediaType(mainType: String, subType: String, parameters: Map[String, String] = Map.empty) {

  def matches(other: MediaType): Boolean =
    (subType == WildCard && mainType == other.mainType) ||
      (mainType == other.mainType && subType == other.subType && (other.parameters.isEmpty || parameters == other.parameters))
}

object MediaType {
  val WildCard = "*"

  def apply(mediaType: String): MediaType = mediaType match {
    case MediaType(mainType,subType,parameter) => MediaType(mainType, subType, parameter)
    case _ => throw new IllegalArgumentException(s"Invalid media type: $mediaType")
  }

  def unapply(mediaType: String): Option[(String, String, Map[String, String])] = {
    val parameterSplit = mediaType.split(";").toList
    val types = parameterSplit.head.split("/").toList match {
      case mainType :: subType :: Nil => Some((mainType, subType))
      case _ => None
    }
    val params = parameterSplit.drop(1).map(_.split("=")).filter(_.size == 2).map(p => p(0) -> p(1)).toMap
    types.map(t => (t._1, t._2, params))
  }

}

object MediaTypes {
  val `application/json` = MediaType("application", "json")
  val `text/plain` = MediaType("text", "plain")
}