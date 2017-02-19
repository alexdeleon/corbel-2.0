package io.corbel.functions.registry.distributed

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source}
import grizzled.slf4j.Logging
import io.corbel.functions.model._

import scala.concurrent.Future

/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
class RemoteService(address: String, port: Int)(implicit system: ActorSystem) extends Service with Logging {

  implicit val materializer = ActorMaterializer()

  lazy val proxy = Http(system).outgoingConnection(address, port)

  override def apply(request: HttpRequest): Future[HttpResponse] = {
    debug(s"Invoking remote service $address:$port ${request.uri.path}")
    val handler = Source.single(request)
      .via(proxy)
      .runWith(Sink.head)
    handler
  }
}
