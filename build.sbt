name := "corbel"

lazy val commonSettings = Seq(
  organization := "io.corbel",
  version := "2.0.0-SNAPSHOT",
  scalaVersion := "2.11.8",

  resolvers in ThisBuild ++= Seq(
    Resolver.defaultLocal,
    Resolver.sonatypeRepo("releases"),
    Resolver.sonatypeRepo("snapshots")
  )
)

/* ----- Dependencies ---------------------------------------------- */
val AkkaVersion = "2.4.8"

lazy val commonDependencies = Seq(
  "org.scala-lang.modules" %% "scala-java8-compat" % "0.8.0-RC3",
  "com.softwaremill.macwire" %% "macros" % "2.2.3" % "provided",
  "com.softwaremill.macwire" %% "util" % "2.2.3",
  "com.softwaremill.macwire" %% "proxy" % "2.2.3",
  "com.typesafe" % "config" % "1.3.0",
  "org.scalactic" %% "scalactic" % "2.2.6",
  "org.clapper"   %%  "grizzled-slf4j" % "1.0.2",
  "org.json4s" %% "json4s-native" % "3.4.0",
  "org.scalatest" %% "scalatest" % "2.2.6" % "test"
)

lazy val runtimeDependencies = Seq(
  "ch.qos.logback" % "logback-classic" % "1.1.7"
)

lazy val akkaDependencies = Seq(
  "com.typesafe.akka" %% "akka-http-experimental" % AkkaVersion,
  "com.typesafe.akka" %% "akka-actor" % AkkaVersion
)

lazy val eventBusDependencies = Seq(
  "com.typesafe.akka" %% "akka-stream-kafka" % "0.11-M4"
)

lazy val functionsDependencies = Seq(
  "org.apache.curator" % "curator-x-discovery" % "3.2.0"
)

lazy val functionsContainerDependencies = Seq(
  "io.github.lukehutch" % "fast-classpath-scanner" % "1.99.0"
)


/* ----- Modules ---------------------------------------------- */
lazy val root = (project in file(".")).
  aggregate(functions)

lazy val common = (project in file("common")).
  settings(commonSettings: _*).
  settings(libraryDependencies := commonDependencies ++ akkaDependencies)


lazy val functions = (project in file("functions")).
  settings(commonSettings: _*).
  settings(libraryDependencies := commonDependencies ++ akkaDependencies ++ functionsDependencies).
  dependsOn(common)

lazy val functionsContainer = (project in file("functions-container")).
  settings(commonSettings: _*).
  settings(libraryDependencies := commonDependencies ++ runtimeDependencies ++ functionsContainerDependencies).
  dependsOn(common, functions, samplePlugin)


lazy val api = (project in file("api")).
  settings(libraryDependencies := commonDependencies ++ akkaDependencies ++ runtimeDependencies).
  settings(commonSettings: _*).
  dependsOn(common, functions)


lazy val eventBus = (project in file("event-bus")).
  settings(commonSettings: _*).
  settings(libraryDependencies := commonDependencies ++ akkaDependencies ++ eventBusDependencies ++ runtimeDependencies).
  dependsOn(common)


lazy val samplePlugin = (project in file("sample-plugin")).
  settings(commonSettings: _*).
  dependsOn(functions)
