package com.github.ingarabr

import sbt.{settingKey, taskKey}

import java.io.File

trait CloudFunctionKeys {
  val cloudFunctionClass =
    taskKey[String]("The function class to use.")

  val cloudFunctionPort =
    taskKey[Int]("The http port to server the function on.")

  val cloudFunctionRunLocally =
    taskKey[Unit]("Run a cloud function locally")

  val cloudFunctionInvokerVersion =
    settingKey[String]("java-function-invoker version")

  val cloudFunctionFrameworkApiVersion =
    settingKey[String]("functions-framework-api version")

  val cloudFunctionDeployConfiguration =
    settingKey[DeployConfiguration]("Deploy configuration")

  val cloudFunctionJar =
    taskKey[File]("The jar file to deploy.")

  val cloudFunctionDeploy =
    taskKey[Unit](
      """|Deploy function.
         |Uses default authentication mechanism defined by the Google java lib.""".stripMargin
    )

}
