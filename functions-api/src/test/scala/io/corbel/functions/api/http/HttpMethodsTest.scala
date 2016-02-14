package io.corbel.functions.api.http

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

import com.gensler.scalavro.types.AvroType
import org.scalatest.{FlatSpec, Matchers}

import scala.util.Success

/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
class HttpMethodsTest extends FlatSpec with Matchers {

  val Type = AvroType[HttpMethods.Value]
  val io = Type.io

  println(Type.schema())

  "HttpMethods" should "be serializable with Avro" in {
    val stream = new ByteArrayOutputStream()
    io.write(HttpMethods.GET, stream)
    val x = io.read(new ByteArrayInputStream(stream.toByteArray))
    x should be(Success(HttpMethods.GET))
  }

}
