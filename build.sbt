import scala.sys.process._

scalaVersion := "2.12.8"

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

resolvers += Resolver.bintrayRepo("underscoreio", "training")

libraryDependencies ++= Seq(
  "underscoreio" %% "doodle" % "0.8.3",
  "org.typelevel" %% "cats-core" % "1.1.0"
)

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
