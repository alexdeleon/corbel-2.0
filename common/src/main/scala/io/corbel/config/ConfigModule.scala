package io.corbel.config

import com.typesafe.config.{Config, ConfigFactory}

/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
trait ConfigModule {

  lazy val config: Config = ConfigFactory.load()

}
