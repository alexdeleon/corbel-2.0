package io.corbel.functions.serializer

import java.io.ByteArrayOutputStream
import java.util

import com.gensler.scalavro.types.AvroType
import io.corbel.functions.api.http.HttpRequest
import org.apache.kafka.common.serialization.Serializer
import HttpRequestAvroSerializer._

/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
class HttpRequestAvroSerializer extends Serializer[HttpRequest] {
  override def close(): Unit = {}

  override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = {}

  override def serialize(topic: String, data: HttpRequest): Array[Byte] = {
    val stream = new ByteArrayOutputStream()
    io.write(data, stream)
    stream.toByteArray
  }
}

object HttpRequestAvroSerializer {
  val Type = AvroType[HttpRequest]
  lazy val io = Type.io
}
