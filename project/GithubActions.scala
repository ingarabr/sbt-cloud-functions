import sbt.{AllRequirements, AutoPlugin, Def, Plugins}
import sbtghactions.GenerativePlugin.autoImport._
import sbtghactions.{GitHubActionsPlugin, RefPredicate}

object GithubActions extends AutoPlugin {

  override def trigger = AllRequirements

  override def requires: Plugins = GitHubActionsPlugin

  override def buildSettings: Seq[Def.Setting[_]] =
    Seq(
      githubWorkflowTargetTags ++= Seq("v*"),
      githubWorkflowPublishTargetBranches :=
        Seq(RefPredicate.StartsWith(Ref.Tag("v"))),
      githubWorkflowPublish := Seq(
        WorkflowStep.Sbt(
          commands = List("publish"),
          env = Map(
            "BINTRAY_USER" -> "${{ secrets.BINTRAY_USER }}",
            "BINTRAY_PASS" -> "${{ secrets.BINTRAY_PASS }}"
          )
        )
      )
    )
}
