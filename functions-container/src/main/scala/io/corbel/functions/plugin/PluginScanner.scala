package io.corbel.functions.plugin

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner
import io.github.lukehutch.fastclasspathscanner.matchprocessor.ImplementingClassMatchProcessor


/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
object PluginScanner {

  def scan: Iterable[Class[_ <: FunctionsPlugin]] = {
    var plugins = List.empty[Class[_ <: FunctionsPlugin]]
    val scanner = new FastClasspathScanner("io.corbel.functions.plugin")
    scanner.matchClassesImplementing[FunctionsPlugin](classOf[FunctionsPlugin], new ImplementingClassMatchProcessor[FunctionsPlugin] {
      override def processMatch(matchingClass: Class[_ <: FunctionsPlugin]): Unit = {
        plugins ::= matchingClass
      }
    })
    scanner.scan()
    plugins
  }

}
