package io.corbel.functions.plugin

import io.corbel.functions.registry.Registry

/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
protected[plugin] trait FunctionsPlugin {

  def registerFunctions(registry: Registry)

}
