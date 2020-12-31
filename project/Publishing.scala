import bintray.BintrayPlugin
import bintray.BintrayKeys._
import sbt.Keys._
import sbt.{Def, _}

object Publishing extends AutoPlugin {

  override def trigger = AllRequirements

  override def requires: Plugins = BintrayPlugin

  override def buildSettings: Seq[Def.Setting[_]] =
    Seq(
      licenses += ("MIT", url("http://opensource.org/licenses/MIT")),
      version := GitTagVersion.versionFromGit,
      bintrayReleaseOnPublish := GitTagVersion.isSnapShot(version.value),
      bintrayVcsUrl := Some("git@github.com:ingarabr/sbt-cloud-functions.git")
    )
  override def projectSettings: Seq[Def.Setting[_]] =
    Seq(
      Test / publishArtifact := false,
      publishMavenStyle := true,
      bintrayRepository := "oss"
    )
}
