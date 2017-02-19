package io.corbel.functions

import io.corbel.functions.http.CorbelFunctionHandlerFactory
import io.corbel.functions.registry.Registry
import io.corbel.http.Server
import com.softwaremill.macwire._

/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
trait FunctionsContainerModule {

  def httpHandler: Server.Handler = wireWith(CorbelFunctionHandlerFactory.createCorbelFunctionHandler _)

  //module dependencies ----------
  def registry: Registry
}
