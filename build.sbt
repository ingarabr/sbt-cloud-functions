import Versions.V
inThisBuild(
  Seq(
    organization := "com.github.ingarabr",
    scalaVersion := V.defaultScala,
    homepage := Some(url("https://github.com/ingarabr/http4s-cloud-functions")),
    licenses += ("MIT", url("http://opensource.org/licenses/MIT")),
    developers := List(
      Developer(
        "ingarabr",
        "Ingar Abrahamsen",
        "ingar.abrahamasen@gmail.com",
        url("https://github.com/ingarabr/")
      )
    )
  )
)

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
