package com.github.ingarabr

import buildinfo.BuildInfo
import sbt._
import sbt.Keys._

object CloudFunctionsPlugin extends AutoPlugin {

  object autoImport extends CloudFunctionKeys
  import autoImport._

  override def projectSettings: Seq[Def.Setting[_]] =
    Seq(
      cloudFunctionInvokerVersion := BuildInfo.cloudFunctionInvokerVersion,
      cloudFunctionFrameworkApiVersion := BuildInfo.cloudFunctionFrameworkApiVersion,
      cloudFunctionPort := 8080,
      libraryDependencies ++= Seq(
        "com.google.cloud.functions" % "functions-framework-api" % cloudFunctionFrameworkApiVersion.value % Provided,
        "com.google.cloud.functions.invoker" % "java-function-invoker" % cloudFunctionInvokerVersion.value % Test
      ),
      Test / cloudFunctionRunLocally / run / fork := true,
      cloudFunctionRunLocally := Def.taskDyn {
        (Test / runMain)
          .toTask(
            " com.google.cloud.functions.invoker.runner.Invoker" +
              s" --port ${cloudFunctionPort.value}" +
              s" --target ${cloudFunctionClass.value}"
          )
      }.value,
      cloudFunctionDeployConfiguration := DeployConfiguration.empty,
      cloudFunctionDeploy :=
        DeployFunctionHelper.deployUsingJar(
          cloudFunctionDeployConfiguration.value,
          cloudFunctionClass.value,
          cloudFunctionJar.value,
          (msg: String) => sLog.value.info(msg)
        )
    )

}
