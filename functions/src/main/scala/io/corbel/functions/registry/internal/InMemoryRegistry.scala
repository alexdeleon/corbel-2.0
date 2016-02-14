package io.corbel.functions.registry.internal

import io.corbel.functions.registry.{RequestHandlerQuery, FunctionRegistryQuery, FunctionRegistry}
import io.corbel.functions.api.{RequestHandler, Function}

/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
class InMemoryRegistry extends FunctionRegistry {

  private var registry = Vector.empty[Function]

  override def register[F <: Function](f: F): Unit = registry :+= f

  override def get[F <: Function](query: FunctionRegistryQuery[F]): Seq[F] = query match {
    case q: RequestHandlerQuery => getRequestHandlers(q).map(_.asInstanceOf[F])
    case _ => Seq.empty
  }

  protected def getRequestHandlers(q: RequestHandlerQuery): Seq[Function] = registry.filter({
    case RequestHandler(_, uriPattern, methods, inputContentType, outputContentType) =>
      q.reqUri.matches(uriPattern) &&
      methods.contains(q.method) &&
        (!q.method.isEntityAccepted || q.contentType.exists(in => inputContentType.exists(_.matches(in)))) &&
      outputContentType.matches(q.accepts)
    case _ => false
  })

}
