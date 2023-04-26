package hof

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

@JSExportTopLevel("HofParametricCurve")
object ParametricCurve {

  def parametricSpiral(angle: Angle): Point =
    Point((Math.exp(angle.toTurns) - 1) * 200, angle)

  def drawCurve(
      points: Int,
      marker: Point => Image,
      curve: Angle => Point
  ): Image = {
    // Angle.one is one complete turn. I.e. 360 degrees
    val turn = Angle.one / points
    def loop(count: Int): Image = {
      count match {
        case 0 =>
          val pt = curve(Angle.zero)
          marker(pt).at(pt)
        case n =>
          val pt = curve(turn * count)
          marker(pt).at(pt).on(loop(n - 1))
      }
    }

    loop(points)
  }

  @JSExport
  def spiral(id: String): Unit = {
    val marker = (point: Point) =>
      Image
        .circle(point.r * 0.125 + 7)
        .fillColor(Color.red.spin(point.angle / -4.0))
        .noStroke

    drawCurve(20, marker, parametricSpiral _).drawWithFrame(Frame(id))
  }
}
