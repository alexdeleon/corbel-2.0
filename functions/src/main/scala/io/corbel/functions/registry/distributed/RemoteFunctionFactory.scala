package io.corbel.functions.registry.distributed

import io.corbel.functions.model.CorbelFunction

/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
trait RemoteFunctionFactory {

  def createRemoteFunction(address: String, port: Int): CorbelFunction

}
