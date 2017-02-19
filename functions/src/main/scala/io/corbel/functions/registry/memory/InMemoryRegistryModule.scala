package io.corbel.functions.registry.memory

import com.softwaremill.macwire._
import io.corbel.functions.registry.{Registry, RegistryModule}

/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
trait InMemoryRegistryModule extends RegistryModule[Registry] {

  override lazy val registry: Registry = wire[InMemoryRegistry]
}
