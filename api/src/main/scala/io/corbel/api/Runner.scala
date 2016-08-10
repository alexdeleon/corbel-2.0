package io.corbel.api

import akka.actor.ActorSystem
import akka.http.scaladsl.model.{StatusCodes, HttpResponse, HttpRequest}
import akka.stream.scaladsl.Flow
import akka.util.ByteString
import grizzled.slf4j.Logging
import io.corbel.config.ConfigModule
import io.corbel.functions.registry.memory.InMemoryRegistryModule
import io.corbel.http.HttpModule

import scala.concurrent.ExecutionContext
import scala.util.{Success, Failure}


/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
object Runner extends App with Logging {

  val system = ActorSystem("corbel-api")
  implicit val ec: ExecutionContext = system.dispatcher

  val modules = new ApiModule with ConfigModule with HttpModule with InMemoryRegistryModule {
    val actorSystem = system
  }

  modules.registry.register((req: HttpRequest) => HttpResponse(entity = "hello"))
  modules.registry.register((res: HttpResponse) => res.status match {
    case StatusCodes.OK =>
      val newEntity = res.entity.transformDataBytes(Flow[ByteString].map(bytes => bytes ++ ByteString(" world")))
      res.withEntity(newEntity)
    case _ => res
  })

  modules.server.start().onComplete({
    case Success(binding) => info(s"Bind server to ${binding.localAddress}")
    case Failure(e) => error("Failed to start server", e)
      system.terminate()
  })

}
