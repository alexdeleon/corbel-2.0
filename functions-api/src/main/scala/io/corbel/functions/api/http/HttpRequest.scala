package io.corbel.functions.api.http


/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
case class HttpRequest(method: HttpMethods.Value,
                       uri: String,
                       headers: List[HttpHeader],
                       entity: Option[Array[Byte]])
