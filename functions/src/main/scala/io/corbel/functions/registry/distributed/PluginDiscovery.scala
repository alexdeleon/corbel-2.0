package io.corbel.functions.registry.distributed

import org.apache.curator.framework.CuratorFramework
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder
import org.apache.zookeeper.{Watcher, WatchedEvent}
import PluginDiscovery._

/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
trait PluginDiscovery {

  val discovery =  ServiceDiscoveryBuilder.builder(classOf[RemotePluginProperties])
    .client(curatorFramework)
    .basePath(ZooKeeperRegistryPath)
    .serializer(RemotePluginProperties.instanceSerializer)
    .watchInstances(true)
    .build()

  discovery.start()

  def curatorFramework: CuratorFramework

}

object PluginDiscovery {
  val ZooKeeperRegistryPath = "io/corbel/functions/registry"

  implicit def functionToWatcher(f: (WatchedEvent => Unit)): Watcher = new Watcher {
    override def process(event: WatchedEvent): Unit = f(event)
  }
}