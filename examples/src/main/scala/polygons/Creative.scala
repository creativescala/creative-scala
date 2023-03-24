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
    def style(percentage: Double, image: Image): Image = {
      image
        .strokeColor(
          Color.crimson.darkenBy((percentage * percentage).normalized)
        )
        .strokeWidth(3.0)
    }

    def concentricPolygons(
        total: Int,
        startRadius: Double,
        radiusStep: Double
    ): Image = {
      def loop(count: Int): Image =
        count match {
          case 0 =>
            regularPolygon(3, startRadius)
              .strokeColor(Color.crimson)
              .strokeWidth(3.0)
          case n =>
            loop(n - 1).on(
              style(
                n.toDouble / total.toDouble,
                regularPolygon(n + 3, startRadius + (radiusStep * n))
              )
            )
        }

      loop(total)
    }

    concentricPolygons(7, 20, 10).drawWithFrame(Frame(id))
  }

  @JSExport
  def spiralExercise(id: String): Unit = {
    def style(percentage: Double, image: Image): Image = {
      image
        .strokeColor(
          Color.turquoise.darkenBy((percentage * percentage).normalized)
        )
        .strokeWidth(3.0)
    }

    def polygonSpiral(
        total: Int,
        startRadius: Double,
        radiusStep: Double,
        angleStep: Angle
    ): Image = {
      def loop(count: Int): Image =
        count match {
          case 0 => style(0.0, regularPolygon(5, startRadius))
          case n =>
            loop(count - 1).on(
              style(
                count.toDouble / total,
                regularPolygon(5, startRadius + (radiusStep * n))
                  .rotate(angleStep * n)
              )
            )
        }

      loop(total)
    }
    polygonSpiral(15, 20, 7, 5.degrees).drawWithFrame(Frame(id))
  }

  @JSExport
  def curvygonExercise(id: String): Unit = {
    def regularCurvygon(
        sides: Int,
        radius: Int,
        radiusOffset: Int,
        offset1: Double,
        offset2: Double
    ): Image = {
      val turn = (1.0 / sides).turns
      def loop(count: Int): ClosedPath =
        count match {
          case 0 => ClosedPath.empty.moveTo(radius, 0.degrees)
          case n =>
            loop(n - 1).curveTo(
              radius - radiusOffset,
              turn * (n - 1) + (turn * offset1),
              radius + radiusOffset,
              turn * (n - 1) + (turn * offset2),
              radius,
              turn * n
            )
        }

      Image.path(loop(sides))
    }

    -70.0
      .upToIncluding(70.0)
      .map(offset => regularCurvygon(5, 100, offset.toInt, 0.2, 0.7).compile)
      .forDuration(2.5.seconds)
      .andThen(_ =>
        70.0
          .upToIncluding(-70.0)
          .map(offset =>
            regularCurvygon(5, 100, offset.toInt, 0.2, 0.7).compile
          )
          .forDuration(2.5.seconds)
      )
      .repeatForever
      .animate(Frame(id).withSize(250, 250))
  }

  @JSExport
  def rosetteExercise(id: String): Unit = {
    def rosette(sides: Int, radius: Int, skip: Int): Image = {
      val turn = (1.0 / sides).turns
      def loop(count: Int): ClosedPath =
        count match {
          case 0 => ClosedPath.empty.moveTo(radius, 0.degrees)
          case n =>
            loop(n - 1).lineTo(radius, turn * (n * skip))
        }

      Image.path(loop(sides))
    }

    rosette(7, 100, 3)
      .beside(rosette(8, 100, 3))
      .beside(rosette(10, 100, 3))
      .drawWithFrame(Frame(id))
  }
}
