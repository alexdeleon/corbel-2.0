package io.corbel.functions.registry.distributed

import io.corbel.functions.registry.memory.InMemoryRegistry
import org.apache.curator.framework.CuratorFramework
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder
import DistributedRegistry._
import org.apache.zookeeper.{WatchedEvent, Watcher}
import scala.collection.JavaConversions._
import scala.concurrent.ExecutionContext

/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
class DistributedRegistry(client: CuratorFramework)(implicit ec: ExecutionContext) extends InMemoryRegistry {


  val discovery =  ServiceDiscoveryBuilder.builder(classOf[FunctionsPluginRegistryEntry])
    .client(client)
    .basePath(ZooKeeperRegistryPath)
    .serializer(FunctionsPluginRegistryEntry.instanceSerializer)
    .watchInstances(true)
    .build()


  // add watcher for plugins
  client.getChildren.usingWatcher((_: WatchedEvent) => scanRegistry()).forPath(ZooKeeperRegistryPath)

  // run the initial scan
  scanRegistry()


  private def scanRegistry(): Unit = {
    discovery.queryForNames().foreach(plugin => {
      val pluginProvider = discovery.serviceProviderBuilder().serviceName(plugin).build()
      //todo: add entries to the registry using this provider

    })
  }



}

object DistributedRegistry {

  val ZooKeeperRegistryPath = "io/corbel/functions/registry"

  implicit def functionToWatcher(f: (WatchedEvent => Unit)): Watcher = new Watcher {
    override def process(event: WatchedEvent): Unit = f(event)
  }

}