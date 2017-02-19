package io.corbel.functions

import akka.actor.ActorSystem
import grizzled.slf4j.Logging
import io.corbel.config.ConfigModule
import io.corbel.functions.plugin.PluginScanner
import io.corbel.functions.plugin.registry.PluginRegistryModule
import io.corbel.http.HttpModule

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
object Runner extends App with Logging {

  val system = ActorSystem("corbel-api")
  implicit val ec: ExecutionContext = system.dispatcher

  val modules = new FunctionsContainerModule with ConfigModule with HttpModule with PluginRegistryModule {
    val actorSystem = system
    val pluginName = "test"
    val pluginVersion = "1.0.0"
  }

  modules.server.start().onComplete({
    case Success(binding) =>
      info(s"Bind server to ${binding.localAddress}")
      PluginScanner.scan.foreach(_.newInstance().registerFunctions(modules.registry))
      //go!
      modules.registry.start(binding.localAddress.getAddress.getHostAddress, binding.localAddress.getPort)
    case Failure(e) => error("Failed to start server", e)
      system.terminate()
  })




}
