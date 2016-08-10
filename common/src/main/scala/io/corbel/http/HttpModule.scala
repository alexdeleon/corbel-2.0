package io.corbel.http

import com.typesafe.config.Config
import io.corbel.actor.AkkaModule

/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
trait HttpModule extends AkkaModule {

  lazy val server: Server = new Server(
    config.getString(Config.HttpInterface),
    config.getInt(Config.HttpPort),
    httpHandler
  )

  object Config {
    val HttpInterface = "http.interface"
    val HttpPort = "http.port"
  }

  //module dependencies ----------
  def config: Config

  def httpHandler: Server.Handler


}
