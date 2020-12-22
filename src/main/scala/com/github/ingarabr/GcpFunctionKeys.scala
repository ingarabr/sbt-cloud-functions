package com.github.ingarabr

import sbt.{settingKey, taskKey}

import java.io.File

trait GcpFunctionKeys {
  val gcpFunctionClass =
    taskKey[String]("The function class to use.")

  val gcpFunctionPort =
    taskKey[Int]("The http port to server the function on.")

  val gcpFunctionRunLocally =
    taskKey[Unit]("Run a cloud function locally")

  val gcpFunctionInvokerVersion =
    settingKey[String]("java-function-invoker version")

  val gcpFunctionFrameworkApiVersion =
    settingKey[String]("functions-framework-api version")

  val gcpFunctionDeployConfiguration =
    settingKey[DeployConfiguration]("Deploy configuration")

  val gcpFunctionJar =
    taskKey[File]("The jar file to deploy.")

  val gcpFunctionDeploy =
    taskKey[Unit](
      """|Deploy function.
         |Uses default authentication mechanism defined by the Google java lib.""".stripMargin
    )

}
