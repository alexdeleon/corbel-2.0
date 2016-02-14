package io.corbel.functions

import akka.actor.Props
import graffiti.{Service, BasicApplication}
import io.corbel.functions.actor.FunctionDispatcher
import io.corbel.functions.api.ResourcesApi
import io.corbel.functions.registry.FunctionRegistryActor
import io.corbel.functions.registry.internal.InMemoryRegistry

/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
object FunctionsRunner extends BasicApplication("corbel-functions") {

  lazy val registry = system.actorOf(Props(new FunctionRegistryActor(new InMemoryRegistry)))
  lazy val dispatcher = system.actorOf(Props(new FunctionDispatcher(registry)), "functionDispatcher")

  override def service: Service = new ResourcesApi(dispatcher)

}
