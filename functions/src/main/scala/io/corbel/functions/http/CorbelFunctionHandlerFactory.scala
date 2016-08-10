package io.corbel.functions.http

import akka.http.scaladsl.model.{HttpRequest, HttpResponse, StatusCodes}
import akka.stream.scaladsl.{Flow, Source}
import io.corbel.functions.registry.Registry

/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
object CorbelFunctionHandlerFactory {

  def createCorbelFunctionHandler(registry: Registry) = {
    Flow[HttpRequest].flatMapConcat(httpRequest => {
      //Query the corbel functions registry using the request as criteria
      registry.query(httpRequest) match {
        case Some((service, reqFilters, resFilters)) => {
          //Now tha we have a match, lets construct a processing pipeline
          val filteredReq = if(reqFilters.isEmpty) {
            Flow[HttpRequest] //identity flow
          } else {
            // pipeline of Req filters connected in sequence
            reqFilters.map(filter => Flow[HttpRequest].mapAsync(4)(filter)).reduce(_ via   _)
          }
          val serviceResponse = Flow[HttpRequest].mapAsync(4)(service)
          val filteredRes = if(resFilters.isEmpty) {
            Flow[HttpResponse] //identity flow
          } else {
            // pipeline of Res filters connected in sequence
            resFilters.map(filter => Flow[HttpResponse].mapAsync(4)(filter)).reduce(_ via _)
          }
          /*
            *      +------------------------------------------------+
            *      | Resulting Source                               |
            *      |                                                |
            *      |  +-------+        +---------+        +-------+ |
            *      |  |       |        |         |        |       | |
            * Req ~~> | Req   | ~Req~> | Service | ~Res~> | Res   | ~ Res ~~>
            *      |  |Filters|        |         |        |Filters| |
            *      |  +-------+        +---------+        +-------+ |
            *      +------------------------------------------------+
          */
          Source.single(httpRequest).via(filteredReq.via(serviceResponse).via(filteredRes))
        }
        //If there are not matching function in the registry then return 404 Not Found!
        case None => Source.single(HttpResponse(StatusCodes.NotFound))
      }
    })
  }

}
