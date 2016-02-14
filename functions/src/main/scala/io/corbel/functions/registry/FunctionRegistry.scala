package io.corbel.functions.registry

import io.corbel.functions.api.http.HttpMethods.HttpMethod
import io.corbel.functions.api.http.MediaType
import io.corbel.functions.api.{RequestHandler, Function}


/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
trait FunctionRegistry {

  def register[F <: Function](f: F)

  def get[F <: Function](query: FunctionRegistryQuery[F]): Seq[F]

}

sealed trait FunctionRegistryQuery[F <: Function]

case class RequestHandlerQuery(
                                reqUri: String,
                                method: HttpMethod,
                                contentType: Option[MediaType],
                                accepts: MediaType
                              ) extends FunctionRegistryQuery[RequestHandler]