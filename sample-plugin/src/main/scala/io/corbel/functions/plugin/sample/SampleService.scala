package io.corbel.functions.plugin.sample

import akka.http.scaladsl.server.Directives._

/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
object SampleService {

  def apply() = get {
    complete("This is a GET request to the sample plugin")
  }

}
