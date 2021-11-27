package com.github.ingarabr

case class DeployConfiguration(
    functionName: String,
    gcpProject: String,
    gcpLocation: String,
    memoryMb: Int = 512,
    triggerHttp: Boolean = true,
    allowUnauthenticated: Boolean = false,
    runtime: String = "java11",
    extraArgs: List[String] = List.empty,
    releaseChannel: ReleaseChannel = ReleaseChannel.GA
)

object DeployConfiguration {
  val empty: DeployConfiguration = DeployConfiguration("", "", "")
}

sealed abstract class ReleaseChannel(
    val cliName: Option[String]
)
object ReleaseChannel {
  case object GA extends ReleaseChannel(None)
  case object Alpha extends ReleaseChannel(Some("alpha"))
  case object Beta extends ReleaseChannel(Some("beta"))
}
