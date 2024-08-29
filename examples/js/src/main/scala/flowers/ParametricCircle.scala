package flowers

import cats.syntax.all.*
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

@JSExportTopLevel("FlowersParametricCircle")
object ParametricCircle {
  @JSExport
  def circle(id: String): Unit = {
    val radius = 100

    def parametricCircle(angle: Angle): Point =
      Point(100, angle)

    val duration = 0.5.seconds

    val point =
      Picture.circle(7.0).noStroke.fillColor(Color.hotpink)

    def dashedLine(pt: Point): Picture[Unit] =
      OpenPath.empty
        .moveTo(Point.zero)
        .lineTo(pt)
        .path
        .strokeWidth(3.0)
        .strokeDash(Array(3.0, 3.0))
        .strokeColor(Color.darkBlue)

    def background(count: Int, turn: Angle): Picture[Unit] =
      0.to(count).map(i => point.at(radius, turn * i)).foldLeft(Picture.empty) {
        (accum, elt) => elt.on(accum)
      }

    def segment(start: Angle, end: Angle): Interpolation[Picture[Unit]] =
      start.upToIncluding(end).map(a => dashedLine(Point(radius, a)))

    def circleAnimation(points: Int): Transducer[Picture[Unit]] = {
      val turn = (1.0 / points).turns

      def loop(count: Int): Transducer[Picture[Unit]] =
        count match {
          case 0 =>
            Interpolation.constant(background(0, turn)).forDuration(duration)
          case n =>
            loop(n - 1).andThen(_ =>
              segment(turn * (n - 1), turn * n)
                .map(p => p.on(background(n - 1, turn)))
                .forDuration(duration)
            )
        }

      loop(points).andThen(_ =>
        Interpolation
          .constant(background(points, turn))
          .forDuration(duration * 2)
      )
    }

    val animation =
      circleAnimation(12).repeatForever.animate(Frame(id).withSize(220, 220))
  }
}
