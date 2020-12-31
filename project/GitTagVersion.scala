import scala.sys.process.{Process, ProcessLogger}
import scala.util.{Failure, Success, Try}

object GitTagVersion {

  private def runCmd(cmd: String, args: String*): Try[String] = {
    val result = List.newBuilder[Either[String, String]]
    val code = Process(cmd :: args.toList).!(new ProcessLogger {
      def out(s: => String): Unit = result += Right(s)
      def err(s: => String): Unit = result += Left(s)
      def buffer[T](f: => T): T = f
    })
    if (code == 0)
      Success(result.result().collect { case Right(v) => v }.mkString("\n"))
    else
      Failure(
        new RuntimeException(
          s"Cmd: ${(cmd :: args.toList)
            .mkString(" ")} failed:\n${result.result().map(_.fold(identity, identity)).mkString("\n")}"
        )
      )
  }
  private def gitSha =
    runCmd("git", "rev-parse", "--short", "HEAD")
      .map("0.0.0-" + _)

  private def gitTag =
    runCmd("git", "describe", "--tags")
      .map(_.replaceFirst("^v", ""))

  private def noTag(err: Throwable) =
    Option(err.getMessage) match {
      case Some(msg) => msg.contains("No names found")
      case None      => false
    }

  def versionFromGit: String =
    gitTag.recoverWith { case err if noTag(err) => gitSha }.map(_.trim).get

  def isSnapShot(version: String): Boolean =
    version.matches("\\d+.\\d+.\\d+")
}
