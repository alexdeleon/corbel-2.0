package io.corbel.functions.registry

import akka.actor.{Status, Actor, ActorLogging}
import FunctionRegistryActor._
import io.corbel.functions.api.Function

/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
class FunctionRegistryActor(registry: FunctionRegistry) extends Actor with ActorLogging {
  override def receive: Receive = {
    case Message.Register(f) =>
      log.info(s"Registering function: $f")
      registry.register(f)
      sender ! Status.Success
    case Message.Get(query) =>
      val functions = registry.get(query)
      sender !  Message.GetResult(functions)
  }
}

object FunctionRegistryActor {
  object Message {
    // in
    case class Register(function: Function)
    case class Get[F <: Function](query: FunctionRegistryQuery[Function])
    // out
    case class GetResult[F <: Function](functions: Seq[F])
  }
}
