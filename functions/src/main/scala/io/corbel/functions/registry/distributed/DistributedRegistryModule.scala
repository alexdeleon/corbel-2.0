package io.corbel.functions.registry.distributed

import akka.actor.ActorSystem
import com.softwaremill.macwire._
import com.typesafe.config.Config
import io.corbel.functions.registry.memory.InMemoryRegistry
import io.corbel.functions.registry.{RegistryModule, Registry}
import org.apache.curator.framework.{CuratorFramework, CuratorFrameworkFactory}
import org.apache.curator.retry.ExponentialBackoffRetry


/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
trait DistributedRegistryModule {

  lazy val curatorFramework: CuratorFramework = {
    val factory = CuratorFrameworkFactory.builder()
      .connectString(config.getString("registry.remote.zookeeper.servers"))
      .retryPolicy(new ExponentialBackoffRetry(10, 10^5))
    val curatorFramework = factory.build()
    curatorFramework.start()
    curatorFramework
  }

  /* ------ module dependencies -- */
  implicit val actorSystem: ActorSystem

  def config: Config
}

class DistributedRegistry(val curatorFramework: CuratorFramework) extends InMemoryRegistry with PluginDiscovery
   with PluginRegistering {

}

trait InMemoryDistributedRegistryModule extends RegistryModule[Registry with PluginDiscovery] with DistributedRegistryModule {

  lazy val registry: Registry with PluginDiscovery = wire[DistributedRegistry]

}