import Versions.V

ThisBuild / organization := "com.github.ingarabr"
ThisBuild / scalaVersion := V.defaultScala

lazy val root = (project in file("."))
  .settings(name := "sbt-cloud-functions-root")
  .enablePlugins(NoPublish)
  .aggregate(`sbt-cloud-functions`)

lazy val `sbt-cloud-functions` =
  (project in file("modules/sbt-cloud-functions"))
    .enablePlugins(SbtPlugin, BuildInfoPlugin)
    .settings(
      buildInfoKeys ++=
        Seq(
          // format: off
          BuildInfoKey.action("cloudFunctionFrameworkApiVersion")(V.cloudFunctionFrameworkApi),
          BuildInfoKey.action("cloudFunctionInvokerVersion")(V.cloudFunctionInvoker)
          // format: on
        )
    )
