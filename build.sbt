import scala.sys.process._
import laika.config.LaikaKeys

val scala213 = "2.13.8"
val scala3 = "3.1.2"

ThisBuild / organization := "org.creativescala"
ThisBuild / organizationName := "Creative Scala"

ThisBuild / crossScalaVersions := List(scala3, scala213)
ThisBuild / scalaVersion := crossScalaVersions.value.head
ThisBuild / useSuperShell := false
ThisBuild / scalafixDependencies += "com.github.liancheng" %% "organize-imports" % "0.5.0"
ThisBuild / semanticdbEnabled := true
ThisBuild / semanticdbVersion := scalafixSemanticdb.revision

Global / onChangedBuildSource := ReloadOnSourceChanges

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding",
  "UTF-8",
  "-unchecked",
  "-feature",
  "-language:implicitConversions",
  "-language:postfixOps",
  "-Xfatal-warnings"
)

enablePlugins(MdocPlugin, LaikaPlugin)

libraryDependencies ++= Seq(
  "org.creativescala" %% "doodle" % "0.11.2",
  "org.creativescala" %% "doodle-svg" % "0.11.2"
)

console / initialCommands := """
      |import doodle.core._
      |import doodle.image._
      |import doodle.syntax.all._
      |import doodle.image.syntax.all._
      |import doodle.java2d._
    """.trim.stripMargin

// scalacOptions in Mdoc := (scalacOptions in Mdoc).value.filterNot(Set("-Ywarn-unused-import"))

mdocIn := sourceDirectory.value / "pages"
mdocOut := target.value / "pages"
Laika / sourceDirectories := Seq(
  mdocOut.value / "expressions"
)
laikaExtensions ++= Seq(
  laika.markdown.github.GitHubFlavor,
  laika.parse.code.SyntaxHighlighting
)
laikaSite / target := target.value / "creative-scala"
laikaIncludeEPUB := true
laikaIncludePDF := true

lazy val build = taskKey[Unit]("Build the book")

build := { mdoc.toTask("").value; laikaSite.value }
