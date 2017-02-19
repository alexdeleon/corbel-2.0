package io.corbel.api

import akka.actor.ActorSystem
import akka.http.scaladsl.model.{HttpRequest, HttpResponse, StatusCodes}
import akka.stream.scaladsl.Flow
import akka.util.ByteString
import grizzled.slf4j.Logging
import io.corbel.config.ConfigModule
import io.corbel.functions.registry.distributed.{InMemoryDistributedRegistryModule, DistributedRegistryModule}
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

  val modules = new ApiModule with ConfigModule with HttpModule with InMemoryDistributedRegistryModule {
    val actorSystem = system
  }

  modules.server.start().onComplete({
    case Success(binding) => info(s"Bind server to ${binding.localAddress}")
    case Failure(e) => error("Failed to start server", e)
      system.terminate()
  })

}
