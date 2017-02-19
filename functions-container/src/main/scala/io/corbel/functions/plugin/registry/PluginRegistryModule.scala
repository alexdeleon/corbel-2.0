package io.corbel.functions.plugin.registry

import io.corbel.functions.registry.distributed.{DistributedRegistry, PluginDiscovery, DistributedRegistryModule, InMemoryDistributedRegistryModule}
import io.corbel.functions.registry.{RegistryModule, Registry}
import com.softwaremill.macwire._

/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
trait PluginRegistryModule extends RegistryModule[Registry with PluginDiscovery with PluginRegistry] with DistributedRegistryModule  {

  class ConcretePluginRegistry extends DistributedRegistry(curatorFramework) with PluginRegistry {
    override def pluginName: String = PluginRegistryModule.this.pluginName
    override def version: String = PluginRegistryModule.this.pluginVersion
  }

  override lazy val registry: Registry with PluginDiscovery with PluginRegistry = wire[ConcretePluginRegistry]

  // deps
  def pluginName: String
  def pluginVersion: String

}
