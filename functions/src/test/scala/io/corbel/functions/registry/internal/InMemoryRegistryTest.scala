package io.corbel.functions.registry.internal

import io.corbel.functions.api.http.{MediaType, MediaTypes, HttpMethods}
import io.corbel.functions.registry.RequestHandlerQuery
import io.corbel.functions.api.RequestHandler
import org.scalatest.{Matchers, FlatSpec}
/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
class InMemoryRegistryTest extends FlatSpec with Matchers {

  val registry = new InMemoryRegistry
  val f = RequestHandler(
    id = "123",
    uriPattern = "/resource/TestCollection.*",
    methods = Seq(HttpMethods.GET, HttpMethods.POST),
    inputContentType = Some(MediaTypes.`application/json`),
    outputContentType = MediaTypes.`application/json`
  )
  registry.register(f)

  behavior of "get"
  it should "match RequestHandler" in {

    val results = registry.get(RequestHandlerQuery(
      reqUri = "/resource/TestCollection",
      method = HttpMethods.GET,
      contentType = None,
      accepts = MediaTypes.`application/json`
    ))

    results.size should be(1)
    results should contain(f)
  }

  it should "NOT find RequestHandler if URI does not match" in {

    val results = registry.get(RequestHandlerQuery(
      reqUri = "/resource/otherCollection",
      method = HttpMethods.GET,
      contentType = None,
      accepts = MediaTypes.`application/json`
    ))

    results.size should be(0)
  }

  it should "NOT find RequestHandler if method does not match" in {

    val results = registry.get(RequestHandlerQuery(
      reqUri = "/resource/TestCollection",
      method = HttpMethods.PUT,
      contentType = Some(MediaTypes.`application/json`),
      accepts = MediaTypes.`application/json`
    ))

    results.size should be(0)
  }

  it should "NOT find RequestHandler if accepts does not match" in {

    val results = registry.get(RequestHandlerQuery(
      reqUri = "/resource/TestCollection",
      method = HttpMethods.GET,
      contentType = None,
      accepts = MediaType("application/pdf")
    ))

    results.size should be(0)
  }

  it should "NOT find RequestHandler if content type does not match" in {

    val results = registry.get(RequestHandlerQuery(
      reqUri = "/resource/TestCollection",
      method = HttpMethods.POST,
      contentType = Some(MediaTypes.`text/plain`),
      accepts = MediaTypes.`application/json`
    ))

    results.size should be(0)
  }

  it should "ignore content type if method does not allow entity" in {

    val results = registry.get(RequestHandlerQuery(
      reqUri = "/resource/TestCollection",
      method = HttpMethods.GET,
      contentType = Some(MediaTypes.`text/plain`),
      accepts = MediaTypes.`application/json`
    ))

    results.size should be(1)
    results should contain(f)
  }

}
