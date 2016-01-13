lazy val root = project.in(file("."))
  .settings(tutSettings)

tutSourceDirectory := sourceDirectory.value / "raw"

tutTargetDirectory := sourceDirectory.value / "pages"

scalaVersion := "2.11.7"

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

libraryDependencies ++= Seq(
  "org.spire-math" %% "cats" % "0.3.0"
)

lazy val pdf = taskKey[Unit]("Builds the PDF version of the book")

pdf := {
  val a = tut.value
  "grunt pdf" !
}


