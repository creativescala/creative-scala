package polygons

import cats.implicits.*
import doodle.core.*
import doodle.syntax.all.*
import doodle.interact.syntax.all.*
import doodle.svg.*
import scala.concurrent.duration.*
import cats.effect.unsafe.implicits.global
import scala.scalajs.js.annotation.*

@JSExportTopLevel("Cartesian")
object Cartesian {
  val axes: Picture[Unit] =
    OpenPath.empty
      .moveTo(-10.0, 0.0)
      .lineTo(250.0, 0.0)
      .path
      .on(
        OpenPath.empty.moveTo(0.0, -10.0).lineTo(0.0, 150.0).path
      )

  def point(pt: Point): Picture[Unit] =
    OpenPath.empty
      .moveTo(pt.x, 0.0)
      .lineTo(pt.x, pt.y)
      .path
      .on(OpenPath.empty.moveTo(0.0, pt.y).lineTo(pt.x, pt.y).path)
      .strokeWidth(3.0)
      .strokeDash(Array(7.0, 3.0))
      .strokeColor(Color.darkBlue)
      .on(Picture.circle(5.0).noStroke.fillColor(Color.darkBlue).at(pt))

  def picture(pt: Point): Picture[Unit] =
    point(pt).on(axes).originAt(120, 70)

  val animation =
    (0.0.upTo(200.0), 0.0.upTo(100.0))
      .mapN((x, y) => picture(Point(x, y)))
      .forDuration(2.seconds)
      .repeatForever

  @JSExport
  def draw(id: String): Unit = {
    val frame = Frame(id).withSize(300, 200)
    animation.animate(frame)
  }
}
