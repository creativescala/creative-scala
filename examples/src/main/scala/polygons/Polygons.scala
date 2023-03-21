package polygons

import cats.implicits.*
import doodle.core.*
import doodle.syntax.all.*
import doodle.interact.animation.*
import doodle.interact.syntax.all.*
import doodle.svg.*
import scala.concurrent.duration.*
import cats.effect.unsafe.implicits.global
import scala.scalajs.js.annotation.*

@JSExportTopLevel("Polygons")
object Polygons {
  def makeFrame(id: String): Frame =
    Frame(id).withSize(220, 220)

  @JSExport
  def points(id: String): Unit = {
    val duration = 0.5.seconds

    val background = Picture
      .regularPolygon(6, 100)
      .strokeWidth(3.0)
      .strokeColor(Color.darkBlue)

    val point =
      Picture.circle(7.0).noStroke.fillColor(Color.hotpink)

    def dashedLine(pt: Point): Picture[Unit] =
      point
        .at(pt)
        .on(
          OpenPath.empty
            .moveTo(Point.zero)
            .lineTo(pt)
            .path
            .strokeWidth(3.0)
            .strokeDash(Array(3.0, 3.0))
            .strokeColor(Color.darkBlue)
        )

    def segment(start: Angle): Transducer[Picture[Unit]] =
      start
        .upToIncluding(start + 60.degrees)
        .map(a => dashedLine(Point(100, a)).on(background))
        .forDuration(duration)
        .andThen(p => Interpolation.constant(p).forDuration(duration))

    val start =
      0.0
        .upToIncluding(100.0)
        .map(x => dashedLine(Point(x, 0.0)).on(background))
        .forDuration(duration)
        .andThen(p => Interpolation.constant(p).forDuration(duration))

    val animation =
      start
        .andThen(_ => segment(0.degrees))
        .andThen(_ => segment(60.degrees))
        .andThen(_ => segment(120.degrees))
        .andThen(_ => segment(180.degrees))
        .andThen(_ => segment(240.degrees))
        .andThen(_ => segment(300.degrees))
        .repeatForever

    animation.animate(makeFrame(id))
  }
}
