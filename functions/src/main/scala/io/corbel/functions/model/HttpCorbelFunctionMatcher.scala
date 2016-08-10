package io.corbel.functions.model

import akka.http.scaladsl.model.{MediaType, HttpMethod}

import scala.util.matching.Regex

/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
case class HttpCorbelFunctionMatcher(
                                      uriPattern: Regex,
                                      methods: Set[HttpMethod],
                                      mediaTypes: Set[MediaType],
                                      weight: Double = 0
                                    )
