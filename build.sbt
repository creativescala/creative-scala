import scala.sys.process._

scalaVersion := "2.12.10"

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-unchecked",
  "-feature",
  "-language:implicitConversions",
  "-language:postfixOps",
  "-Ywarn-dead-code",
  "-Xfatal-warnings"
)

enablePlugins(MdocPlugin)

libraryDependencies ++= Seq(
  "org.creativescala" %% "doodle" % "0.9.17",
  "org.typelevel" %% "cats-core" % "2.0.0"
)

initialCommands in console := """
      |import doodle.core._
      |import doodle.image._
      |import doodle.image.syntax._
      |import doodle.image.syntax.core._
      |import doodle.java2d._
    """.trim.stripMargin

// scalacOptions in Mdoc := (scalacOptions in Mdoc).value.filterNot(Set("-Ywarn-unused-import"))

mdocIn := sourceDirectory.value / "pages"

mdocOut := target.value / "pages"

lazy val pdf  = taskKey[Unit]("Build the PDF version of the book")
lazy val html = taskKey[Unit]("Build the HTML version of the book")
lazy val epub = taskKey[Unit]("Build the ePUB version of the book")
lazy val all  = taskKey[Unit]("Build all versions of the book")

pdf  := { mdoc.toTask("").value ; "grunt pdf"  ! }
html := { mdoc.toTask("").value ; "grunt html" ! }
epub := { mdoc.toTask("").value ; "grunt epub" ! }
all  := { pdf.value ; html.value ; epub.value }
