package io.corbel.functions

import akka.http.scaladsl.model.{HttpResponse, HttpRequest}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try


/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
package object model {

  trait Service extends Function[HttpRequest, Future[HttpResponse]] with HttpCorbelFunction
  trait RequestFilter extends Function[HttpRequest, Future[HttpRequest]] with HttpCorbelFunction
  trait ResponseFilter extends Function[HttpResponse, Future[HttpResponse]] with HttpCorbelFunction

  implicit def functionAsService(f: Function[HttpRequest, HttpResponse])(implicit ec: ExecutionContext): Service = new Service {
    override def apply(req: HttpRequest): Future[HttpResponse] = Future { f(req) }
  }

  implicit def functionAsResponseFilter(f: Function[HttpResponse, HttpResponse])(implicit ec: ExecutionContext): ResponseFilter = new ResponseFilter {
    override def apply(resp: HttpResponse): Future[HttpResponse] = Future { f(resp) }
  }
}
