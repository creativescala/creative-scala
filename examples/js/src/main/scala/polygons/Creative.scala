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
          case 0 => style(0.0, regularPolygon(3, startRadius))
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
    def style(count: Int, image: Image): Image = {
      image.noStroke.fillColor(if (count % 2 == 0) Color.white else Color.black)
    }

    def polygonSpiral(
        total: Int,
        startRadius: Double,
        radiusStep: Double,
        angleStep: Angle
    ): Image = {
      def loop(count: Int): Image =
        count match {
          case 0 => style(0, regularPolygon(5, startRadius))
          case n =>
            loop(count - 1).on(
              style(
                count,
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
  def starPolygonConstruction(id: String): Unit = {
    val sides = 8
    val radius = 100
    val turn = (1.0 / 8.0).turns
    val point =
      Picture.circle(7.0).noStroke.fillColor(Color.hotpink)
    val duration = 0.75.seconds

    val background = regularPolygon(sides, radius).strokeColor(Color.darkBlue)

    def partialStar(count: Double): Picture[Unit] = {
      def loop(count: Int): OpenPath =
        count match {
          case 0 => OpenPath.empty.moveTo(radius, 0.degrees)
          case n => loop(n - 1).lineTo(radius, turn * (n * 3))
        }

      val pt = Point(radius, turn * (count * 3))
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
        .on(
          loop(count.toInt).path
            .strokeColor(Color.darkBlue)
            .strokeWidth(3.0)
        )
        .on(background.compile)
    }

    List
      .range(0, 8)
      .map(i =>
        (i.toDouble
          .upToIncluding((i + 1).toDouble)
          .map(partialStar)
          .forDuration(duration)
          .andThen(p => Interpolation.constant(p).forDuration(duration)))
      )
      .foldLeft(Transducer.apply(Picture.empty))((accum, elt) =>
        accum.andThen(_ => elt)
      )
      .repeatForever
      .animate(Frame(id).withSize(220, 220))
  }

  @JSExport
  def starPolygonExercise(id: String): Unit = {
    def starPolygon(sides: Int, radius: Int, skip: Int): Image = {
      val turn = (1.0 / sides).turns
      def loop(count: Int): ClosedPath =
        count match {
          case 0 => ClosedPath.empty.moveTo(radius, 0.degrees)
          case n =>
            loop(n - 1).lineTo(radius, turn * (n * skip))
        }

      Image.path(loop(sides))
    }

    starPolygon(8, 100, 3)
      .strokeColor(Color.mediumBlue)
      .strokeWidth(3)
      .fillColor(Color.mediumSpringGreen)
      .beside(
        starPolygon(24, 100, 7)
          .strokeWidth(1)
          .strokeDash(Array(3.0, 3.0))
      )
      .beside(
        starPolygon(24, 100, 13)
          .strokeColor(Color.gold)
          .fillColor(Color.crimson)
          .strokeWidth(3)
          .on(starPolygon(24, 100, 13).strokeWidth(7.0))
      )
      .drawWithFrame(Frame(id))
  }

  @JSExport
  def bezierCurveAnimation(id: String): Unit = {
    val point = Picture.circle(7.0).noStroke.fillColor(Color.hotpink)
    val subduedPoint = Picture.circle(7.0).noStroke.fillColor(Color.darkBlue)

    def bezierCurve(cp1: Point, cp2: Point): Picture[Unit] =
      OpenPath.empty
        .moveTo(-100, 0)
        .curveTo(cp1, cp2, Point(100, 0))
        .path
        .strokeColor(Color.darkBlue)
        .on(subduedPoint.at(-100, 0))
        .on(subduedPoint.at(100, 0))
        .on(point.at(cp1))
        .on(point.at(cp2))

    val up = -50.0.upToIncluding(50.0)
    val down = 50.0.upToIncluding(-50.0)
    val animation =
      (up, down)
        .mapN((y1, y2) => bezierCurve(Point(-33.0, y1), Point(33.0, y2)))
        .forDuration(2.5.seconds)
        .andThen(_ =>
          (down, up)
            .mapN((y1, y2) => bezierCurve(Point(-33.0, y1), Point(33.0, y2)))
            .forDuration(2.5.seconds)
        )
        .repeatForever
        .animate(Frame(id).withSize(240, 140))
  }

  @JSExport
  def curvygonExercise(id: String): Unit = {
    def style(count: Int, radiusOffset: Double, image: Image): Image = {
      val normalizedOffset = (radiusOffset + 0.9) / 1.8 // in range (0, 1)
      val countOffset = Math.abs((count / 8.0) - normalizedOffset)
      val offset = Math.sqrt(countOffset)
      image
        .strokeWidth(3.0)
        .strokeColor(
          Color.gold
            .spin(-30.degrees * offset)
            .darkenBy(offset.normalized)
        )
    }
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
    def concentricCurvygon(
        total: Int,
        sides: Int,
        radiusStart: Int,
        radiusStep: Int,
        radiusOffsetPercentage: Double,
        offset1: Double,
        offset2: Double
    ): Image = {
      def loop(count: Int): Image =
        count match {
          case 0 =>
            style(
              count,
              radiusOffsetPercentage,
              regularCurvygon(
                sides,
                radiusStart,
                (radiusOffsetPercentage * radiusStart).toInt,
                offset1,
                offset2
              )
            )
          case n =>
            val r = radiusStart + (n * radiusStep)
            loop(n - 1).on(
              style(
                count,
                radiusOffsetPercentage,
                regularCurvygon(
                  sides,
                  r,
                  (r * radiusOffsetPercentage).toInt,
                  offset1,
                  offset2
                )
              )
            )
        }

      loop(total)
    }

    -0.9
      .upToIncluding(0.9)
      .map(offset => concentricCurvygon(9, 5, 20, 10, offset, 0.2, 0.7).compile)
      .forDuration(2.5.seconds)
      .andThen(_ =>
        0.9
          .upToIncluding(-0.9)
          .map(offset =>
            concentricCurvygon(9, 5, 20, 10, offset, 0.2, 0.7).compile
          )
          .forDuration(2.5.seconds)
      )
      .repeatForever
      .animate(Frame(id).withSize(280, 280))
  }
}
