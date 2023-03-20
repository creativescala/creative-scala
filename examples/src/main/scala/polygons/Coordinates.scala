package polygons

import cats.implicits.*
import doodle.core.*
import doodle.syntax.all.*
import doodle.interact.animation.Interpolation
import doodle.interact.syntax.all.*
import doodle.svg.*
import scala.concurrent.duration.*
import cats.effect.unsafe.implicits.global
import scala.scalajs.js.annotation.*

@JSExportTopLevel("Coordinates")
object Coordinates {
  // 4:3 aspect ratio
  val maxWidth = 16.0 * 16.0
  val maxHeight = 16.0 * 12.0

  // 3/4/5 right-angled triangle
  val m = 33.0
  val maxX = m * 4.0
  val maxY = m * 3.0
  val length = m * 5.0

  val xStart = -10.0
  val yStart = -10.0

  def makeFrame(id: String): Frame =
    Frame(id).withSize(maxWidth + 10, maxHeight + 10)

  val point =
    Picture.circle(7.0).noStroke.fillColor(Color.hotpink).at(maxX, maxY)

  val axes: Picture[Unit] =
    OpenPath.empty
      .moveTo(xStart, 0.0)
      .lineTo(maxWidth, 0.0)
      .path
      .on(
        OpenPath.empty.moveTo(0.0, yStart).lineTo(0.0, maxHeight).path
      )

  def cartesianLines(pt: Point): Picture[Unit] =
    OpenPath.empty
      .moveTo(maxX, yStart)
      .lineTo(maxX, pt.y)
      .path
      .on(
        OpenPath.empty.moveTo(xStart, maxY).lineTo(pt.x, maxY).path
      )
      .strokeWidth(3.0)
      .strokeDash(Array(3.0, 3.0))
      .strokeColor(Color.darkBlue)
      .on(point)

  def polarPoint(pt: Point): Picture[Unit] =
    OpenPath.empty
      .moveTo(Point.zero)
      .lineTo(pt.x, pt.y)
      .path
      .strokeWidth(3.0)
      .strokeDash(Array(3.0, 3.0))
      .strokeColor(Color.darkBlue)
      .on(point)

  def withAxes(picture: Picture[Unit]): Picture[Unit] =
    picture.on(axes).originAt(120, 70)

  val cartesianAnimation =
    (
      0.0.upTo(maxWidth).forDuration(1.5.seconds),
      0.0.upTo(maxHeight).forDuration(1.5.seconds)
    )
      .mapN((x, y) => withAxes(cartesianLines(Point(x, y))))
      .andThen(picture =>
        Interpolation.constant(picture).forDuration(0.75.seconds)
      )
      .repeatForever

  val polarAnimation =
    0.0
      .upTo(length)
      .map(x => withAxes(polarPoint(Point(x, 0.0))))
      .forDuration(0.75.seconds)
      .andThen(_ =>
        Angle.zero
          .upTo(Point(maxX, maxY).angle)
          .map(a => withAxes(polarPoint(Point(length, a))))
          .forDuration(0.75.seconds)
      )
      .andThen(picture =>
        Interpolation.constant(picture).forDuration(0.75.seconds)
      )
      .repeatForever

  @JSExport
  def cartesian(id: String): Unit = {
    cartesianAnimation.animate(makeFrame(id))
  }

  @JSExport
  def polar(id: String): Unit = {
    polarAnimation.animate(makeFrame(id))
  }
}
