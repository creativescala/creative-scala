scalaVersion := "2.11.8"

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-unchecked",
  "-feature",
  "-language:implicitConversions",
  "-language:postfixOps",
  "-Ywarn-dead-code",
  "-Xlint",
  "-Xfatal-warnings"
)

lazy val root = project.in(file("."))
  .settings(tutSettings)

resolvers += Resolver.bintrayRepo("underscoreio", "training")

libraryDependencies ++= Seq(
  "underscoreio" %% "doodle" % "0.6.5",
  "org.typelevel" %% "cats" % "0.4.0"
)

tutSourceDirectory := sourceDirectory.value / "pages"

tutTargetDirectory := target.value / "pages"

lazy val pdf  = taskKey[Unit]("Build the PDF version of the book")
lazy val html = taskKey[Unit]("Build the HTML version of the book")
lazy val epub = taskKey[Unit]("Build the ePUB version of the book")
lazy val all  = taskKey[Unit]("Build all versions of the book")

pdf  := { tutQuick.value ; "grunt pdf"  ! }
html := { tutQuick.value ; "grunt html" ! }
epub := { tutQuick.value ; "grunt epub" ! }
all  := { pdf ; html ; epub }
