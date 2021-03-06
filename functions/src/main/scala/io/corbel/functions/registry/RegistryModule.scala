package io.corbel.functions.registry

/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
trait RegistryModule[T <: Registry] {

  def registry: T

}
