package io.corbel.functions.registry.distributed

import akka.http.scaladsl.model.Uri
import io.corbel.functions.model.HttpCorbelFunctionMatcher
import org.apache.curator.x.discovery.ServiceInstance
import org.apache.curator.x.discovery.details.InstanceSerializer

/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
case class FunctionsPluginRegistryEntry(
                                         name: String,
                                         version: String,
                                         endpoint: Uri,
                                         requestFilters: Set[HttpCorbelFunctionMatcher],
                                         responseFilters: Set[HttpCorbelFunctionMatcher],
                                         services: Set[HttpCorbelFunctionMatcher]
                                       )

object FunctionsPluginRegistryEntry {

  def instanceSerializer = new InstanceSerializer[FunctionsPluginRegistryEntry] {
    override def deserialize(bytes: Array[Byte]): ServiceInstance[FunctionsPluginRegistryEntry] = ???

    override def serialize(instance: ServiceInstance[FunctionsPluginRegistryEntry]): Array[Byte] = ???
  }

}