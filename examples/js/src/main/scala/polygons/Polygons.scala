package polygons

import cats.implicits.*
import doodle.core.*
import doodle.syntax.all.*
import doodle.image.*
import doodle.image.syntax.all.*
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
      .strokeWidth(1.0)
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

  @JSExport
  def vertices(id: String): Unit = {
    val dot = Image.circle(5).fillColor(Color.darkViolet)
    val image =
      dot
        .at(Point(100, 0.degrees))
        .on(dot.at(Point(100, 60.degrees)))
        .on(dot.at(Point(100, 120.degrees)))
        .on(dot.at(Point(100, 180.degrees)))
        .on(dot.at(Point(100, 240.degrees)))
        .on(dot.at(Point(100, 300.degrees)))

    image.drawWithFrame(makeFrame(id))
  }

  @JSExport
  def polygonPointsExercise(id: String): Unit = {
    def polygonPoints(sides: Int, radius: Double): Image = {
      val turn = (1.0 / sides).turns
      def loop(count: Int): Image =
        count match {
          case 0 => Image.empty
          case n =>
            Image
              .circle(5)
              .at(Point(radius, turn * n))
              .on(loop(n - 1))
        }

      loop(sides)
    }

    val image = polygonPoints(3, 50)
      .fillColor(Color.crimson)
      .beside(polygonPoints(5, 50).fillColor(Color.lawngreen))
      .beside(polygonPoints(7, 50).fillColor(Color.dodgerBlue))
    image.drawWithFrame(Frame(id))
  }
}
