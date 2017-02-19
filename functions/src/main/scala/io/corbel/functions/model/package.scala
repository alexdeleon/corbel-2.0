package io.corbel.functions

import akka.actor.ActorSystem
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer

import scala.concurrent.{ExecutionContext, Future}


/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
package object model {

  trait Service extends Function[HttpRequest, Future[HttpResponse]] with HttpCorbelFunction
  trait RequestFilter extends Function[HttpRequest, Future[HttpRequest]] with HttpCorbelFunction
  trait ResponseFilter extends Function[HttpResponse, Future[HttpResponse]] with HttpCorbelFunction

  implicit def functionAsService(f: Function[HttpRequest, HttpResponse])(implicit ec: ExecutionContext): CorbelFunction = new Service {
    override def apply(req: HttpRequest): Future[HttpResponse] = Future { f(req) }
  }

  implicit def asyncFunctionAsService(f: Function[HttpRequest, Future[HttpResponse]])(implicit ec: ExecutionContext): CorbelFunction = new Service {
    override def apply(req: HttpRequest): Future[HttpResponse] = f(req)
  }

  implicit def routeAsAsService(route: Route)(implicit system: ActorSystem): CorbelFunction = new Service {
    implicit val mat = ActorMaterializer()
    override def apply(req: HttpRequest): Future[HttpResponse] = Route.asyncHandler(route).apply(req)
  }

  implicit def functionAsResponseFilter(f: Function[HttpResponse, HttpResponse])(implicit ec: ExecutionContext): CorbelFunction = new ResponseFilter {
    override def apply(resp: HttpResponse): Future[HttpResponse] = Future { f(resp) }
  }
}
