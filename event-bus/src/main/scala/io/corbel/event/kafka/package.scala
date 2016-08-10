package io.corbel.event

import akka.kafka.ConsumerSettings

/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
package object kafka {

  implicit class SubscriptionPropertiesWrapper(val props: SubscriptionProperties) extends AnyVal {
    def toConsumerSettings[K,V](settings: ConsumerSettings[K,V]): ConsumerSettings[K,V] =
      settings.withGroupId(props.group).withClientId(props.clientId)
  }

}
