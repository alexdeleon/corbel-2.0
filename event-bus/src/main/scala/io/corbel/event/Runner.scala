package io.corbel.event

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Source
import io.corbel.config.ConfigModule
import io.corbel.event.kafka.KafkaEventBusModule
import org.json4s.JsonAST.JObject
import org.json4s.JsonDSL._

/**
  * This Runner is just for testing and demonstration
  *
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
object Runner extends App {

  implicit val system: ActorSystem = ActorSystem("event-bus")
  implicit val materializer = ActorMaterializer()

  val modules = new KafkaEventBusModule with ConfigModule {
    override implicit val actorSystem: ActorSystem = system
  }

  val eventBus = modules.eventBus

  eventBus.subscribe(Events.Sample, SubscriptionProperties.broadcast).runForeach(e => {
    println(e.toString)
  })

  Thread.sleep(2000)

  Source(1 to 1000).map[JObject](i => "x" -> i).runWith(eventBus.dispatcher(Events.Sample))

}
