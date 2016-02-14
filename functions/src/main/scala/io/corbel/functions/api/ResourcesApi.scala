package io.corbel.functions.api

import akka.actor.ActorRef
import graffiti.Service
import io.corbel.functions.actor.FunctionDispatcher
import spray.routing.RequestContext
import io.corbel.functions.converters._

/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
class ResourcesApi(dispatcher: ActorRef) extends Service("resource") {

  val routeFunction = (context: RequestContext) => dispatcher ! FunctionDispatcher.Message.DispatchRequest(context.request)

  route(routeFunction)

}
