package com.github.ingarabr

case class DeployConfiguration(
    functionName: String,
    gcpProject: String,
    gcpLocation: String,
    memoryMb: Int = 512,
    triggerHttp: Boolean = true,
    allowUnauthenticated: Boolean = false,
    runtime: String = "java11"
)

object DeployConfiguration {
  val empty: DeployConfiguration = DeployConfiguration("", "", "")
}
