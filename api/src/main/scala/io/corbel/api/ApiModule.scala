package io.corbel.api

import com.softwaremill.macwire._
import io.corbel.actor.AkkaModule
import io.corbel.functions.http.CorbelFunctionHandlerFactory
import io.corbel.functions.registry.Registry
import io.corbel.http.Server

/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
trait ApiModule extends AkkaModule {

  def httpHandler: Server.Handler = wireWith(CorbelFunctionHandlerFactory.createCorbelFunctionHandler _)

  //module dependencies ----------
  def registry: Registry
}
