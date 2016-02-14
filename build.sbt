name := "corbel"

lazy val commonSettings = Seq(
  organization := "io.corbel",
  version := "2.0.0-SNAPSHOT",
  scalaVersion := "2.11.7",

  libraryDependencies ++= Seq(
     "org.scalactic" %% "scalactic" % "2.2.6",
     "org.scalatest" %% "scalatest" % "2.2.6" % "test"
  ),

  resolvers in ThisBuild ++= Seq(
    Resolver.defaultLocal,
    Resolver.sonatypeRepo("releases"),
    Resolver.sonatypeRepo("snapshots")
  )
)

lazy val root = (project in file(".")).
  aggregate(functions)

lazy val functions = (project in file("functions")).
  settings(commonSettings: _*).
  dependsOn(functionsApi)

lazy val functionsApi = (project in file("functions-api")).
  settings(commonSettings: _*)

lazy val eventBus = (project in file("event-bus")).
  settings(commonSettings: _*)

lazy val events = (project in file("events")).
  settings(commonSettings: _*)