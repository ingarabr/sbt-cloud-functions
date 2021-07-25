import sbt.{AllRequirements, AutoPlugin, Def, Plugins}
import sbtghactions.GenerativePlugin.autoImport._
import sbtghactions.{GitHubActionsPlugin, RefPredicate, WorkflowStep}

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
          List("ci-release"),
          env = Map(
            "PGP_PASSPHRASE" -> "${{ secrets.PGP_PASSPHRASE }}",
            "PGP_SECRET" -> "${{ secrets.PGP_SECRET }}",
            "SONATYPE_PASSWORD" -> "${{ secrets.SONATYPE_PASSWORD }}",
            "SONATYPE_USERNAME" -> "${{ secrets.SONATYPE_USERNAME }}"
          )
        )
      )
    )
}
