package io.corbel.functions.plugin

import akka.actor.ActorSystem
import io.corbel.functions.model.Activator
import io.corbel.functions.plugin.sample.SampleService
import io.corbel.functions.registry.Registry


/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
class SamplePlugin extends FunctionsPlugin {

  implicit val actorSystem = ActorSystem()

  override def registerFunctions(registry: Registry): Unit = {
    println("registering sample plugin with registry="+registry)
    registry.register(SampleService(), Activator("/test", Set("GET"), Set("*/*")))
  }
}
