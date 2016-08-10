package io.corbel.functions

import akka.actor.ActorSystem
import grizzled.slf4j.Logging
import io.corbel.config.ConfigModule
import io.corbel.functions.plugin.PluginScanner
import io.corbel.functions.registry.memory.InMemoryRegistryModule
import io.corbel.http.HttpModule

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
object Runner extends App with Logging {

  val system = ActorSystem("corbel-api")
  implicit val ec: ExecutionContext = system.dispatcher

  val modules = new FunctionsContainerModule with ConfigModule with HttpModule with InMemoryRegistryModule {
    val actorSystem = system
  }

  modules.server.start().onComplete({
    case Success(binding) => info(s"Bind server to ${binding.localAddress}")
    case Failure(e) => error("Failed to start server", e)
      system.terminate()
  })

  PluginScanner.scan.foreach(println)

}
