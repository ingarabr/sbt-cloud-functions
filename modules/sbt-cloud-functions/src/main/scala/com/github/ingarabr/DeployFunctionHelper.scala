package com.github.ingarabr

import java.io.File
import java.nio.file.Files
import scala.sys.process.ProcessLogger

object DeployFunctionHelper {

  def deployUsingJar(
      config: DeployConfiguration,
      classFile: String,
      jar: File,
      infoLog: String => Unit
  ): Unit = {
    if (config == DeployConfiguration.empty)
      throw new IllegalArgumentException("Invalid config")

    val tmpDir = Files.createTempDirectory("gcp-function")
    val targetJar = tmpDir.resolve(jar.getName).toFile
    sbt.IO.copyFile(jar, targetJar)
    targetJar.deleteOnExit()

    def toArg(b: Boolean, arg: String) = if (b) List(arg) else Nil

    val release = config.releaseChannel.cliName.toList
    val base = release ++ List("functions", "deploy", config.functionName)

    val args = Map(
      "--entry-point" -> classFile,
      "--source" -> tmpDir.toString,
      "--runtime" -> config.runtime,
      "--project" -> config.gcpProject,
      "--memory" -> s"${config.memoryMb}MB"
    ).toList
      .flatMap(e => List(e._1, e._2)) ++
      toArg(config.triggerHttp, "--trigger-http") ++
      toArg(config.allowUnauthenticated, "--allow-unauthenticated") ++
      config.extraArgs

    infoLog(s"Calling: gcloud ${(base ++ args).mkString(" ")}")

    val pl = new InMemoryProcessLogger
    val code = sys.process.Process("gcloud", base ++ args).!(pl)
    infoLog(pl.toString)
    if (code != 0) {
      throw new Exception(s"Failed to deploy function:\n${pl.toString}")
    }
  }

  class InMemoryProcessLogger extends ProcessLogger {
    private val values = List.newBuilder[Either[String, String]]

    def allLines: List[Either[String, String]] = values.result()
    def outLines: List[String] = values.result().collect { case Left(v) => v }
    def errLines: List[String] = values.result().collect { case Right(v) => v }

    override def out(s: => String): Unit = values.+=(Left(s))
    override def err(s: => String): Unit = values.+=(Right(s))
    override def buffer[T](f: => T): T = f

    override def toString: String =
      s"""|Std:
          |${outLines.mkString("\n")}
          |Err:
          |${errLines.mkString("\n")}
          |""".stripMargin

  }
}
