package io.corbel.event.kafka

import akka.actor.ActorSystem
import com.typesafe.config.Config
import io.corbel.event.{EventBus, EventBusModule}

/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
trait KafkaEventBusModule extends EventBusModule {
  import Config._

  lazy val eventBus: EventBus = new KafkaEventBus(config.getString(KafkaBootstrapServers))

  object Config {
    val KafkaBootstrapServers = "eventbus.kafka.bootstrapServers"
  }

  //module dependencies ----------
  implicit val actorSystem: ActorSystem

  def config: Config

}
