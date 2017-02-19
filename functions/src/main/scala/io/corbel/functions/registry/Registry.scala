package io.corbel.functions.registry

import akka.http.scaladsl.model.headers.Accept
import akka.http.scaladsl.model.{HttpMethod, HttpRequest, MediaRange, Uri}
import io.corbel.functions.model.{CorbelFunction, _}

import scala.reflect.ClassTag

/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
trait Registry {

  def register(f: CorbelFunction, activators: Activator*)

  def query(query: QueryMatcher): query.Result = query(this)

  def find[T <: CorbelFunction : ClassTag](criteria: RegistryFindCriteria[_ >: T]): Seq[T]

}

sealed trait QueryMatcher {
  type Result
  def apply(registry: Registry): Result
}

object QueryMatcher {
  implicit def fromHttpRequest(req: HttpRequest) = new QueryMatcher {
    override def apply(registry: Registry) = {
      val criteria = HttpRegistryFindCriteria(
        uri = Some(req.uri),
        method = Some(req.method),
        mediaTypes = req.header[Accept].map(_.mediaRanges)
      )
      registry.find[Service](criteria)
        .headOption.map(service => {
        val reqFilters = registry.find[RequestFilter](criteria)
        val resFilters = registry.find[ResponseFilter](criteria)
        (service, reqFilters, resFilters)
      })


    }
    override type Result = Option[(Service, Seq[RequestFilter], Seq[ResponseFilter])]
  }
}

sealed trait RegistryFindCriteria[T <: CorbelFunction]

case class HttpRegistryFindCriteria(
                                     uri: Option[Uri] = None,
                                     method: Option[HttpMethod] = None,
                                     mediaTypes: Option[Seq[MediaRange]] = None
                               ) extends RegistryFindCriteria[HttpCorbelFunction]