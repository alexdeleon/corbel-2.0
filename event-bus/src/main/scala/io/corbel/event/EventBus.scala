package io.corbel.event

import akka.NotUsed
import akka.stream.Materializer
import akka.stream.scaladsl.{Sink, Source}
import org.json4s.JsonAST.JObject


/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
trait EventBus {

  protected implicit val materializer: Materializer

  def dispatch(event: Event)(body: JObject): Unit = dispatcher(event).runWith(Source.single(body))

  def dispatcher(event: Event): Sink[JObject, NotUsed]

  def subscribe(event: Event, props: SubscriptionProperties): Source[(Event, JObject), _]
}
