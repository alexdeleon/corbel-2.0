package io.corbel.functions

import io.corbel.functions.api.http.HttpMethods.HttpMethod
import io.corbel.functions.api.http.{HttpHeader, HttpMethods, HttpRequest}

/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
package object converters {

  implicit def sprayRequestToFunctionsRequest(req: spray.http.HttpRequest): HttpRequest = HttpRequest(
    method = req.method,
    uri = req.uri.toString(),
    headers = req.headers.map(sprayHeaderToFunctionsHeader),
    entity = if(req.entity.isEmpty) None else Some(req.entity.data.toByteArray)
  )

  implicit def sprayMethodToFunctionsMethod(method: spray.http.HttpMethod): HttpMethod = HttpMethods.withName(method.name)

  implicit def sprayHeaderToFunctionsHeader(header: spray.http.HttpHeader): HttpHeader = HttpHeader(header.name, header.value)

}
