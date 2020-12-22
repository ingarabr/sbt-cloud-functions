inThisBuild(
  Seq(
    organization := "com.github.ingarabr",
    version := "0.0.1-SNAPSHOT-1"
  )
)

lazy val root = (project in file("."))
  .enablePlugins(SbtPlugin)
  .settings(
    name := "sbt-gcp-functions"
  )
