package io.corbel.functions.plugin

import org.scalatest.{FlatSpec, Matchers}

/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
class PluginScannerTest extends FlatSpec with Matchers {

  "scan" should "find TestPlugin" in {
    PluginScanner.scan.toSeq should contain (classOf[TestPlugin])
  }

}
