package io.corbel.functions.registry.memory

import grizzled.slf4j.Logging
import io.corbel.functions.model.{Activator, CorbelFunction}
import io.corbel.functions.registry.{Registry, RegistryFindCriteria}

import scala.reflect.{ClassTag, classTag}


/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
class InMemoryRegistry extends Registry with Logging {

  var functions = Vector.empty[CorbelFunction]

  override def find[T <: CorbelFunction : ClassTag](criteria: RegistryFindCriteria[_ >: T]): Seq[T] = {
    val `type` = classTag[T].runtimeClass
    debug(s"Searching registry for ${`type`} with criteria: $criteria. Registry size = ${functions.size}")
    functions.flatMap({
      case e: T => Some(e)
      case _ => None
    })
  }

  override def register(f: CorbelFunction, activators: Activator*): Unit = {
    debug(s"Registering function: $f")
    functions +:= f
  }
}
