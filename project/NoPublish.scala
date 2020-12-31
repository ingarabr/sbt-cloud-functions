import sbt.Keys._
import sbt.plugins.JvmPlugin
import sbt.{AutoPlugin, Def, NoTrigger}

object NoPublish extends AutoPlugin {

  override def trigger = NoTrigger

  override def requires = JvmPlugin

  override def projectSettings: Seq[Def.Setting[_]] =
    Seq(
      publish := {},
      publish / skip := true
    )

}
