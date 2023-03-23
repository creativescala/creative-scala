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

@JSExportTopLevel("Paths")
object Paths {
  @JSExport
  def hexagon(id: String): Unit = {
    val hexagonPath =
      ClosedPath.empty
        .moveTo(100, 0.degrees)
        .lineTo(100, 60.degrees)
        .lineTo(100, 120.degrees)
        .lineTo(100, 180.degrees)
        .lineTo(100, 240.degrees)
        .lineTo(100, 300.degrees)

    val hexagonImage = Image.path(hexagonPath)

    hexagonImage.drawWithFrame(Frame(id))
  }

  @JSExport
  def regularPolygonExercise(id: String): Unit = {
    def regularPolygon(sides: Int, radius: Double): Image = {
      val turn = (1.0 / sides).turns
      def loop(count: Int): ClosedPath =
        count match {
          case 0 => ClosedPath.empty.moveTo(radius, 0.degrees)
          case n => loop(n - 1).lineTo(radius, turn * n)
        }

      Image.path(loop(sides))
    }

    val polygons =
      regularPolygon(3, 25)
        .on(regularPolygon(5, 50))
        .on(regularPolygon(7, 75))

    polygons.drawWithFrame(Frame(id))
  }
}
