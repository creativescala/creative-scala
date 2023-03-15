package polygons

import cats.implicits.*
import doodle.core.*
import doodle.syntax.all.*
import doodle.interact.syntax.all.*
import doodle.svg.*
import scala.concurrent.duration.*
import cats.effect.unsafe.implicits.global
import scala.scalajs.js.annotation.*

@JSExportTopLevel("Coordinates")
object Coordinates {
  // 256x144 = 16:9 aspect ratio
  val maxWidth = 256
  val maxHeight = 144

  val maxX = 13.0 * 16.0
  val maxY = 13.0 * 9.0
  val length = Math.sqrt(maxX * maxX + maxY * maxY)

  def makeFrame(id: String): Frame =
    Frame(id).withSize(maxWidth + 10, maxHeight + 10)

  val axes: Picture[Unit] =
    OpenPath.empty
      .moveTo(-10.0, 0.0)
      .lineTo(maxWidth, 0.0)
      .path
      .on(
        OpenPath.empty.moveTo(0.0, -10.0).lineTo(0.0, maxHeight).path
      )

  def cartesianPoint(pt: Point): Picture[Unit] =
    OpenPath.empty
      .moveTo(pt.x, 0.0)
      .lineTo(pt.x, pt.y)
      .path
      .on(OpenPath.empty.moveTo(0.0, pt.y).lineTo(pt.x, pt.y).path)
      .strokeWidth(3.0)
      .strokeDash(Array(7.0, 3.0))
      .strokeColor(Color.darkBlue)
      .on(Picture.circle(5.0).noStroke.fillColor(Color.darkBlue).at(pt))

  def polarPoint(pt: Point): Picture[Unit] =
    OpenPath.empty
      .moveTo(Point.zero)
      .lineTo(pt.x, pt.y)
      .path
      .strokeWidth(3.0)
      .strokeDash(Array(7.0, 3.0))
      .strokeColor(Color.darkBlue)
      .on(Picture.circle(5.0).noStroke.fillColor(Color.darkBlue).at(pt))

  def withAxes(picture: Picture[Unit]): Picture[Unit] =
    picture.on(axes).originAt(120, 70)

  val cartesianAnimation =
    0.0
      .upTo(maxX)
      .map(x => withAxes(cartesianPoint(Point(x, 0.0))))
      .forDuration(1.5.seconds)
      .andThen(_ =>
        0.0
          .upTo(maxY)
          .map(y => withAxes(cartesianPoint(Point(maxX, y))))
          .forDuration(1.5.seconds)
      )
      .repeatForever

  val polarAnimation =
    0.0
      .upTo(length)
      .map(x => withAxes(polarPoint(Point(x, 0.0))))
      .forDuration(1.5.seconds)
      .andThen(_ =>
        Angle.zero
          .upTo(Math.atan(9.0 / 16.0).radians)
          .map(a => withAxes(polarPoint(Point(length, a))))
          .forDuration(1.5.seconds)
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
