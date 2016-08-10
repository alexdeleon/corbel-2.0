package io.corbel.functions

import com.softwaremill.macwire._
import io.corbel.functions.http.CorbelFunctionHandlerFactory
import io.corbel.functions.registry.Registry
import io.corbel.http.Server

/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
trait FunctionsContainerModule {

  def httpHandler: Server.Handler = null//wireWith(CorbelFunctionHandlerFactory.createCorbelFunctionHandler _)

  //module dependencies ----------
  def registry: Registry
}
