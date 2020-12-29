ThisBuild / organization := "com.github.ingarabr"
ThisBuild / version := Version.versionFromGit

lazy val root = (project in file("."))
  .enablePlugins(SbtPlugin)
  .settings(
    name := "sbt-gcp-functions"
  )
