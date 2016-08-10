package io.corbel.event

import java.util.UUID


/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
case class SubscriptionProperties private[event](group: String, clientId: String)


object SubscriptionProperties {

  def broadcast = {
    val id = UUID.randomUUID().toString
    SubscriptionProperties(id,id)
  }
  def fanOut(group: String) = {
    val id = UUID.randomUUID().toString
    SubscriptionProperties(group, id)
  }

}