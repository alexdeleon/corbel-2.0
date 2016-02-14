package io.corbel.functions.api.http

import org.scalatest.{Matchers, FlatSpec}

/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
class MediaTypeTest extends FlatSpec with Matchers {

  behavior of "matches"

  it should "match concrete subtype agains wildcard" in {
    assert(MediaType("image", "*").matches(MediaType("image", "jpeg")))
  }

  it should "match same types" in {
    assert(MediaType("image", "jpeg").matches(MediaType("image", "jpeg")))
  }

  it should "match same types with parameters" in {
    assert(MediaType("plain", "text", Map("charset" -> "urf8")).matches(MediaType("plain", "text")))
  }

  it should "NOT match if parameters are different" in {
    assert(!MediaType("plain", "text", Map("charset" -> "urf8")).matches(MediaType("plain", "text", Map("charset" -> "ascii"))))
  }

  behavior of "apply"

  it should "parse media type" in {
    val mt = MediaType("application/json")
    mt.mainType should be("application")
    mt.subType should be("json")
    mt.parameters should be(Map.empty)
  }

  it should "throw exception if media type is invalid" in {
    an [IllegalArgumentException] should be thrownBy MediaType("no_valid")
  }
}
