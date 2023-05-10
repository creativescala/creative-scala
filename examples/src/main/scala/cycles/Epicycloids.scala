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

@JSExportTopLevel("CyclesEpicycloids")
object Epicycloids {

  def drawCurve(samples: Int, curve: Angle => Image): Image = {
    val step = Angle.one / samples
    def loop(count: Int): Image = {
      val angle = step * count
      count match {
        case 0 => Image.empty
        case n =>
          curve(angle).on(loop(n - 1))
      }
    }

    loop(samples)
  }

  def animateCurve(
      samples: Int,
      curve: Angle => Point,
      draw: Angle => Picture[Unit],
      dot: Picture[Unit]
  ): Transducer[Picture[Unit]] =
    (0.degrees
      .upToIncluding(360.degrees))
      .forSteps(samples)
      .scanLeft((Picture.empty, List.empty[Point])) { (accum, angle) =>
        val pt = curve(angle)
        val (_, pts) = accum
        val nextPts = pt :: pts
        val p =
          Picture
            .path(OpenPath(PathElement.interpolatingSpline(nextPts)))
            .on(draw(angle))
        (p, nextPts)
      }
      .map { case (picture, _) => picture }

  def parametricCircle(speed: Int): Angle => Point =
    angle => Point(1.0, angle * speed)

  def scale(factor: Double): Point => Point =
    (point: Point) => point.scaleLength(factor)

  def wheel(speed: Int, factor: Double): Angle => Point =
    parametricCircle(speed).andThen(scale(factor))

  def on(wheel1: Angle => Point, wheel2: Angle => Point): Angle => Point =
    angle => wheel1(angle) + wheel2(angle).toVec

  def threeWheels(
      speed1: Int,
      scale1: Double,
      speed2: Int,
      scale2: Double,
      speed3: Int,
      scale3: Double
  ): Angle => Point =
    on(wheel(speed1, scale1), on(wheel(speed2, scale2), wheel(speed3, scale3)))

  val dot: Point => Image =
    pt => Image.circle(5).noStroke.fillColor(Color.blueViolet).at(pt)

  @JSExport
  def epicycloid(id: String): Unit = {
    val curve: Angle => Image =
      threeWheels(7, 75.0, 13, 32.0, 25, 15.0).andThen(dot)

    drawCurve(700, curve).drawWithFrame(Frame(id))
  }

  @JSExport
  def epicycloidTwoWheels(id: String): Unit = {
    val r1 = 75.0
    val r2 = 32.0
    val wheel1 = wheel(7, r1)
    val wheel2 = wheel(13, r2)

    val dot = Picture.circle(5).noStroke.fillColor(Color.red)

    def picture(angle: Angle): Picture[Unit] = {
      val pt1 = wheel1(angle)
      val pt2 = wheel2(angle)
      val pt3 = pt2 + pt1.toVec

      val c1 = dot.at(pt1).on(Picture.circle(75 * 2))
      val c2 = dot.at(pt3).on(Picture.circle(32 * 2).at(pt1))

      c2.on(c1).on(Picture.path(OpenPath.empty.lineTo(pt1).lineTo(pt3)))
    }

    animateCurve(700, on(wheel1, wheel2), picture, dot).animate(
      Frame(id).withSize(300, 300)
    )
  }
}
