package io.corbel.http

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Sink}

import scala.concurrent.Future

/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
class Server(interface: String, port: Int, handler: Server.Handler)(implicit actorSystem: ActorSystem) {

  implicit val materializer = ActorMaterializer()

  val binding = Http().bind(interface, port)
  val runnable = binding.to(Sink.foreach(connection => {
    connection.handleWith(handler)
  }))

  def start(): Future[ServerBinding] = {
    runnable.run()
  }
}

object Server {
  type Handler = Flow[HttpRequest, HttpResponse, _]
}
