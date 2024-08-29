package cycles

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
import doodle.syntax.angle
import cycles.Epicycles.parametricCircle

@JSExportTopLevel("CyclesInterpolation")
object Interpolation {

  def sample(samples: Int, curve: Angle => Point): List[Point] = {
    val step = Angle.one / samples
    def loop(count: Int): List[Point] = {
      val angle = step * count
      count match {
        case 0 => List.empty
        case n =>
          curve(angle) :: loop(n - 1)
      }
    }

    loop(samples)
  }

  val dot = Picture.circle(5).noStroke.fillColor(Color.hotpink)

  def animateInterpolation(
      minSamples: Int,
      maxSamples: Int,
      curve: Angle => Point
  ): Transducer[Picture[Unit]] = {
    val I = doodle.interact.animation.Interpolation

    (minSamples
      .to(maxSamples))
      .map { s =>
        val pts = sample(s, curve)
        val dots = pts.map(pt => dot.at(pt)).allOn
        // Make the curve end at it's start
        val c = Picture
          .path(ClosedPath.interpolatingSpline(pts :+ pts.head))
          .strokeColor(Color.darkBlue)
        I.constant(dots.on(c)).forDuration(1.seconds)
      }
      .toList
      .combineAll
  }

  @JSExport
  def circleInterpolation(id: String): Unit = {
    val parametricCircle = (angle: Angle) => Point(100, angle)
    animateInterpolation(3, 15, parametricCircle).repeatForever.animate(
      Frame(id).withSize(220, 220)
    )
  }
}
