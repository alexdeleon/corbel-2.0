package io.corbel.functions.registry.memory

import io.corbel.functions.registry.{Registry, RegistryModule}
import com.softwaremill.macwire._

/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
trait InMemoryRegistryModule extends RegistryModule {

  override lazy val registry: Registry = wire[InMemoryRegistry]
}
