package io.corbel.functions.plugin

import io.corbel.functions.registry.Registry

/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
class SamplePlugin extends FunctionsPlugin {

  override def registerFunctions(registry: Registry): Unit = {
    println("registering sample plugin with registry="+registry)
  }
}
