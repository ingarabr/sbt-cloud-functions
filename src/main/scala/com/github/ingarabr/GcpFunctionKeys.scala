package com.github.ingarabr

import sbt.{settingKey, taskKey}

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
}
