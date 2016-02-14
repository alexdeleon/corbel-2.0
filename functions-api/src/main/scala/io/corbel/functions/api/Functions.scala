package io.corbel.functions.api

import io.corbel.functions.api.http.HttpMethods.HttpMethod
import io.corbel.functions.api.http.MediaType

/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  *
  *         Function type:
  *           - RequestHandler: Req -> Res - Handles an HTTP request
  *           - EventHandler: Any -> Unit - Handles an external event asynchronously
  *           - InternalEventHandler: Event -> Unit - Handles an internal event
  *           - StoreHook: StorageReq -> Either[Accept,Reject] - Hook function to Corbel Store
  */
object Functions extends Enumeration {
  val RequestHandler, EventHandler, InternalEventHandler, StoreHook = Value
}

sealed trait Function {
  val id: String
}

case class RequestHandler(id: String, uriPattern: String, methods: Seq[HttpMethod], inputContentType: Option[MediaType], outputContentType: MediaType) extends Function