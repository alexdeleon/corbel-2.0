package io.corbel.actor

import akka.actor.ActorSystem

/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
trait AkkaModule {
  implicit val actorSystem: ActorSystem
}
