inThisBuild(
  Seq(
    organization := "com.github.ingarabr",
    version := "0.0.1-SNAPSHOT"
  )
)

lazy val root = (project in file("."))
  .enablePlugins(SbtPlugin)
  .settings(
    name := "sbt-gcp-functions"
  )
