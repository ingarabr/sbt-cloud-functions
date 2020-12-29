ThisBuild / githubWorkflowTargetTags ++= Seq("v*")
ThisBuild / githubWorkflowPublishTargetBranches :=
  Seq(RefPredicate.StartsWith(Ref.Tag("v")))
ThisBuild / githubWorkflowPublish := Seq(
  WorkflowStep.Sbt(
    commands = List("publish"),
    env = Map(
      "BINTRAY_USER" -> "${{ secrets.BINTRAY_USER }}",
      "BINTRAY_PASS" -> "${{ secrets.BINTRAY_PASS }}"
    )
  )
)

ThisBuild / bintrayReleaseOnPublish := Version.isSnapShot(version.value)
ThisBuild / bintrayVcsUrl := Some(
  "git@github.com:ingarabr/sbt-gcp-functions.git"
)

ThisBuild / Compile / packageDoc / publishArtifact := false
ThisBuild / publishArtifact in Test := false
ThisBuild / publishMavenStyle := true
