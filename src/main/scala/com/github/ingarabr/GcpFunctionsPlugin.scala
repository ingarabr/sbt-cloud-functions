package com.github.ingarabr

import sbt._
import sbt.Keys._

object GcpFunctionsPlugin extends AutoPlugin {

  object autoImport extends GcpFunctionKeys
  import autoImport._

  override def projectSettings: Seq[Def.Setting[_]] =
    Seq(
      gcpFunctionInvokerVersion := "1.0.1",
      gcpFunctionFrameworkApiVersion := "1.0.3",
      gcpFunctionPort := 8080,
      libraryDependencies ++= Seq(
        "com.google.cloud.functions" % "functions-framework-api" % gcpFunctionFrameworkApiVersion.value % Provided,
        "com.google.cloud.functions.invoker" % "java-function-invoker" % gcpFunctionInvokerVersion.value % Test
      ),
      fork in (Test, gcpFunctionRunLocally / run) := true,
      gcpFunctionRunLocally := Def.taskDyn {
        (Test / runMain)
          .toTask(
            " com.google.cloud.functions.invoker.runner.Invoker" +
              s" --port ${gcpFunctionPort.value}" +
              s" --target ${gcpFunctionClass.value}"
          )
      }.value,
      gcpFunctionDeployConfiguration := DeployConfiguration.empty,
      gcpFunctionDeploy :=
        DeployFunctionHelper.deployUsingJar(
          gcpFunctionDeployConfiguration.value,
          gcpFunctionClass.value,
          gcpFunctionJar.value,
          (msg: String) => sLog.value.info(msg)
        )
    )

}
