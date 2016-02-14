package io.corbel.functions.actor

import akka.actor.{Actor, ActorLogging, ActorRef}
import com.softwaremill.react.kafka._
import io.corbel.functions.actor.FunctionDispatcher._
import io.corbel.functions.api.http.HttpRequest
import io.corbel.functions.serializer.HttpRequestAvroSerializer
import org.reactivestreams.Subscriber

/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
class FunctionDispatcher(registry: ActorRef) extends Actor with ActorLogging {

  val kafka = new ReactiveKafka()
  implicit val system = context.system

  val subscriber: Subscriber[ProducerMessage[Array[Byte], HttpRequest]] = kafka.publish(ProducerProperties(
    bootstrapServers = "192.168.99.100:9092",
    topic = "dispatcher.functions.corbel.io",
    valueSerializer = new HttpRequestAvroSerializer()
  ))


  override def receive: Receive = {
    case Message.DispatchRequest(req) =>
      log.debug(s"New request: $req")
      dispatchRequest(req)
  }

  def dispatchRequest(req: HttpRequest) = {
    subscriber.onNext(ProducerMessage(req))
  }
}

object FunctionDispatcher {
  object Message {
    //in
    case class DispatchRequest(req: HttpRequest)
    //out
    //case class RequestResponse(resp: HttpResponse)
  }
}
