package io.corbel.functions.model


/**
  * An Activator defines the request rules that activate an HttpCorbelFunction
  *
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
case class Activator(
                      uriPattern: String,
                      methods: Set[String],
                      mediaTypes: Set[String],
                      weight: Double = 0
                    )

