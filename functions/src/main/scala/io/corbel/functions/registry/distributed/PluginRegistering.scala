package io.corbel.functions.registry.distributed

import io.corbel.functions.registry.Registry
import org.apache.zookeeper.{WatchedEvent}

import scala.collection.JavaConversions._

import PluginDiscovery._


/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
trait PluginRegistering {
  self: Registry with PluginDiscovery with RemoteFunctionFactory =>

  // add watcher for plugins
  curatorFramework.getChildren.usingWatcher((_: WatchedEvent) => scanAndRegister()).forPath(ZooKeeperRegistryPath)

  // run the initial scan
  scanAndRegister()

  private def scanAndRegister(): Unit = {
    discovery.queryForNames().foreach(plugin => {
      val pluginProvider = discovery.serviceProviderBuilder().serviceName(plugin).build()
      val instance = pluginProvider.getInstance()
      instance.getPayload.services.foreach(service => register(createRemoteFunction(instance.getAddress, instance.getPort)))
      //todo: add filters
    })
  }



}
