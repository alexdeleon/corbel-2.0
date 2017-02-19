package io.corbel.functions.registry.distributed

import io.corbel.functions.model.Activator
import org.apache.curator.x.discovery.ServiceInstance
import org.apache.curator.x.discovery.details.InstanceSerializer

import scala.pickling.Defaults._
import scala.pickling.binary._

/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
case class RemotePluginProperties(
                                   version: String,
                                   requestFilters: Set[Activator] = Set.empty,
                                   responseFilters: Set[Activator] = Set.empty,
                                   services: Set[Activator] = Set.empty
                                 ) {

  def withService(activator: Activator) = copy(services = this.services + activator)
  def withResponseFilter(activator: Activator) = copy(responseFilters = this.requestFilters + activator)
  def withRequestFilter(activator: Activator) = copy(requestFilters = this.requestFilters + activator)

}

object RemotePluginProperties {

  lazy val instanceSerializer = new InstanceSerializer[RemotePluginProperties] {
    override def deserialize(bytes: Array[Byte]): ServiceInstance[RemotePluginProperties] = {
      val wrapper = bytes.unpickle[ServiceInstanceWrapper]
      ServiceInstance.builder[RemotePluginProperties]
        .name(wrapper.name)
        .address(wrapper.address)
        .port(wrapper.port)
        .payload(wrapper.payload)
        .build()
    }

    override def serialize(instance: ServiceInstance[RemotePluginProperties]): Array[Byte] = {
      ServiceInstanceWrapper(instance.getName, instance.getAddress, instance.getPort, instance.getPayload).pickle.value
    }
  }

  private case class ServiceInstanceWrapper(name: String, address: String, port: Int, payload: RemotePluginProperties)
}
