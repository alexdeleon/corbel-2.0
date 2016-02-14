package io.corbel.functions.api.http

/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
object HttpMethods extends Enumeration {

  val CONNECT = HttpMethod("CONNECT", isSafe = false, isIdempotent = false, isEntityAccepted = false)
  val DELETE  = HttpMethod("DELETE" , isSafe = false, isIdempotent = true , isEntityAccepted = false)
  val GET     = HttpMethod("GET"    , isSafe = true , isIdempotent = true , isEntityAccepted = false)
  val HEAD    = HttpMethod("HEAD"   , isSafe = true , isIdempotent = true , isEntityAccepted = false)
  val OPTIONS = HttpMethod("OPTIONS", isSafe = true , isIdempotent = true , isEntityAccepted = true)
  val PATCH   = HttpMethod("PATCH"  , isSafe = false, isIdempotent = false, isEntityAccepted = true)
  val POST    = HttpMethod("POST"   , isSafe = false, isIdempotent = false, isEntityAccepted = true)
  val PUT     = HttpMethod("PUT"    , isSafe = false, isIdempotent = true , isEntityAccepted = true)
  val TRACE   = HttpMethod("TRACE"  , isSafe = true , isIdempotent = true , isEntityAccepted = false)

  case class HttpMethod(value: String, isSafe: Boolean, isIdempotent: Boolean, isEntityAccepted: Boolean) extends Val

  implicit def valueToHttpMethod(x: Value) = x.asInstanceOf[HttpMethod]
}
