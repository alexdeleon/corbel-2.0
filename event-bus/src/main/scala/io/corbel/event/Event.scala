package io.corbel.event

/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
case class Event(id: String)

object Events {
  val Sample = Event("io.corbel.event.sample")
}
