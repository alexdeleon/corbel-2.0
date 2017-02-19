package io.corbel.event.kafka

import akka.NotUsed
import akka.actor.ActorSystem
import akka.kafka.scaladsl.{Consumer, Producer}
import akka.kafka.{ConsumerSettings, ProducerMessage, ProducerSettings, Subscriptions}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Sink, Source}
import grizzled.slf4j.Logging
import io.corbel.event.{Event, EventBus, SubscriptionProperties}
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.{ByteArrayDeserializer, ByteArraySerializer, StringDeserializer, StringSerializer}
import org.json4s.DefaultFormats
import org.json4s.JsonAST.JObject
import org.json4s.native.Serialization._

/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
class KafkaEventBus(kafkaServers: String)(implicit system: ActorSystem) extends EventBus with Logging {

  implicit val materializer = ActorMaterializer()
  implicit val format = DefaultFormats

  val producerSettings = ProducerSettings(system, new ByteArraySerializer, new StringSerializer)
    .withBootstrapServers(kafkaServers)

  val consumerSettings = ConsumerSettings(system, new ByteArrayDeserializer, new StringDeserializer)
    .withBootstrapServers(kafkaServers)
    .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest")

  override def dispatcher(event: Event): Sink[JObject, NotUsed] = {
      Flow[JObject]
        .map(e => ProducerMessage.Message(new ProducerRecord[Array[Byte], String](event.id, write(e)), e))
        .via(Producer.flow(producerSettings))
        .map(result => {
          val record = result.message.record
          println(s"${record.topic}/${record.partition} ${result.offset}: ${record.value} (${result.message.passThrough}")
          result
        })
        .recover({
          case e: Throwable => e.printStackTrace()
        })
        .to(Sink.last)
    }

  override def subscribe(event: Event, props: SubscriptionProperties): Source[(Event, JObject), _] = {
    val settings = props.toConsumerSettings(consumerSettings)
    val subscription = Subscriptions.topics(event.id)
    Consumer.atMostOnceSource(settings, subscription).map(message => (event, deserialize(message.value)))
  }

  def deserialize(data:String): JObject = {
    read[JObject](data)
  }
}
