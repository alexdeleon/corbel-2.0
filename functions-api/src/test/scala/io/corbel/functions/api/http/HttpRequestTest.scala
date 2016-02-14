package io.corbel.functions.api.http

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

import com.gensler.scalavro.types.AvroType
import org.scalatest.{FlatSpec, Matchers, FunSuite}

import scala.util.Success

/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
class HttpRequestTest extends FlatSpec with Matchers {

  val Type = AvroType[HttpRequest]
  val io = Type.io

  println(Type.schema())

  "HttpRequest" should "be serializable with Avro" in {
    val request = HttpRequest(HttpMethods.GET, "http://example.org", List.empty, None)
    val stream = new ByteArrayOutputStream()
    io.write(request, stream)
    val x = io.read(new ByteArrayInputStream(stream.toByteArray))
    x should be(Success(request))
  }

}
