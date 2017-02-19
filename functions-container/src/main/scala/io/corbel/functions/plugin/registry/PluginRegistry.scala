package io.corbel.functions.plugin.registry

import io.corbel.functions.model._
import io.corbel.functions.registry.distributed.{RemotePluginProperties, PluginDiscovery}
import io.corbel.functions.registry.{RegistryFindCriteria, Registry}
import org.apache.curator.x.discovery.ServiceInstance

import scala.reflect.ClassTag

/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
trait PluginRegistry extends Registry with PluginDiscovery {

  def pluginName: String
  def version: String

  private var toPublish = Vector.empty[(CorbelFunction, Seq[Activator])]
  private var serviceInstance: Option[ServiceInstance[RemotePluginProperties]] = None

  abstract override def register(f: CorbelFunction, activators: Activator*): Unit = {
    toPublish :+= (f, activators)
    super.register(f, activators:_*)
  }

  def start(address: String, port: Int): Unit = {

    // start publishing the functions for remote access
    var props = RemotePluginProperties(version)
    toPublish.foreach {
      case (s: Service, activators: Seq[Activator]) => for(activator <- activators) {
        props = props.withService(activator)
      }
    }

    serviceInstance = Some(ServiceInstance.builder[RemotePluginProperties]
      .name(pluginName)
      .address(address)
      .port(port)
      .payload(props)
      .build())

    //Publish the plugin
    discovery.registerService(serviceInstance.get)

  }

  def stop(): Unit = for(i <- serviceInstance) discovery.unregisterService(i)

}
