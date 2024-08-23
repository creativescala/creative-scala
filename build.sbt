import scala.sys.process._
import laika.config.LinkConfig
import laika.config.ApiLinks

val scala213 = "2.13.11"
val scala3 = "3.3.1"

ThisBuild / organization := "org.creativescala"
ThisBuild / organizationName := "Creative Scala"

ThisBuild / crossScalaVersions := List(scala3, scala213)
ThisBuild / scalaVersion := crossScalaVersions.value.head
ThisBuild / scalafixDependencies += "com.github.liancheng" %% "organize-imports" % "0.6.0"
ThisBuild / semanticdbEnabled := true
ThisBuild / semanticdbVersion := scalafixSemanticdb.revision

Global / onChangedBuildSource := ReloadOnSourceChanges

ThisBuild / console / initialCommands := """
      |import doodle.core._
      |import doodle.image._
      |import doodle.syntax.all._
      |import doodle.image.syntax.all._
      |import doodle.java2d._
    """.trim.stripMargin

lazy val css = taskKey[Unit]("Build the CSS")
lazy val build = taskKey[Unit]("Build the book")

val commonSettings = Seq(
  libraryDependencies ++= Seq(
    "org.creativescala" %%% "doodle" % "0.23.0"
  )
)

lazy val book = project
  .in(file("book"))
  .settings(
    commonSettings,
    scalacOptions ++= Seq(
      "-deprecation",
      "-encoding",
      "UTF-8",
      "-unchecked",
      "-feature",
      "-language:implicitConversions",
      "-language:postfixOps",
      "-Xfatal-warnings"
    ),
    mdocIn := sourceDirectory.value / "pages",
    mdocOut := target.value / "pages",
    Laika / sourceDirectories := Seq(
      mdocOut.value,
      sourceDirectory.value / "js",
      (examples / Compile / fastOptJS / artifactPath).value
        .getParentFile() / s"${(examples / moduleName).value}-fastopt"
    ),
    laikaExtensions ++= Seq(
      laika.format.Markdown.GitHubFlavor,
      laika.config.SyntaxHighlighting,
    ),
    laikaSite / target := target.value / "creative-scala",
    laikaIncludeEPUB := false,
    laikaIncludePDF := false,
    laikaTheme := CreativeScalaTheme.empty.build,
    css := {
      val src = sourceDirectory.value / "css"
      val dest1 = mdocOut.value
      val dest2 = (laikaSite / target).value
      val cmd1 =
        s"npx tailwindcss -i ${src.toString}/creative-scala.css -o ${dest1.toString}/creative-scala.css"
      val cmd2 =
        s"npx tailwindcss -i ${src.toString}/creative-scala.css -o ${dest2.toString}/creative-scala.css"
      cmd1 !

      cmd2 !
    }
  )
  .enablePlugins(MdocPlugin, LaikaPlugin)

lazy val examples = project
  .in(file("examples"))
  .settings(commonSettings)
  .enablePlugins(ScalaJSPlugin)

build := Def
  .sequential(
    (examples / Compile / fastLinkJS),
    (book / Compile / run).toTask(""),
    (book / mdoc).toTask(""),
    book / css,
    book / laikaSite,
    book / css
  )
  .value
