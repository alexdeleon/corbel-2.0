package io.corbel.http

import java.net.NetworkInterface

import com.typesafe.config.{ConfigException, Config}
import grizzled.slf4j.Logging
import io.corbel.actor.AkkaModule


/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
trait HttpModule extends AkkaModule with Logging {

  lazy val server: Server = new Server(
    getHttpAddress,
    config.getInt(Config.HttpPort),
    httpHandler
  )

  private def getHttpAddress: String =
    if(config.hasPath(Config.HttpAddress)) config.getString(Config.HttpAddress)
    else getAddressFromInterface


  private def getAddressFromInterface: String = {
    try {
      val interface = config.getString(Config.HttpInterface)
      val networkInterface = NetworkInterface.getByName(interface)
      val inetAddress = networkInterface.getInetAddresses.nextElement()
      inetAddress.getHostAddress
    }
    catch {
      case e: ConfigException.Missing =>
        error(s"You must specify either ${Config.HttpAddress} or ${Config.HttpInterface} in your configuration", e)
        throw e
      case e: Throwable => throw e
    }
  }

  object Config {
    val HttpAddress = "http.address"
    val HttpInterface = "http.interface"
    val HttpPort = "http.port"
  }

  //module dependencies ----------
  def config: Config

  def httpHandler: Server.Handler


}
