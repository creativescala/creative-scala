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

@JSExportTopLevel("Creative")
object Creative {
  def regularPolygon(sides: Int, radius: Double): Image = {
    val turn = (1.0 / sides).turns
    def loop(count: Int): ClosedPath =
      count match {
        case 0 => ClosedPath.empty.moveTo(radius, 0.degrees)
        case n => loop(n - 1).lineTo(radius, turn * n)
      }

    Image.path(loop(sides))
  }

  @JSExport
  def concentricExercise(id: String): Unit = {
    def concentricPolygons(
        count: Int,
        startRadius: Double,
        radiusStep: Double
    ): Image =
      count match {
        case 0 =>
          regularPolygon(3, startRadius)
            .strokeColor(Color.crimson)
            .strokeWidth(3.0)
        case n =>
          concentricPolygons(n - 1, startRadius, radiusStep).on(
            regularPolygon(n, startRadius + (radiusStep * n))
          )
      }
  }
}
