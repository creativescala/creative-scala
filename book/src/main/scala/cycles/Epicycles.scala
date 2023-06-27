package creativescala
package cycles

import cats.implicits._
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._

object Epicycles {

  def drawCurve(samples: Int, dot: Image)(f: Angle => Point): Image = {
    // Angle.one is one complete turn. I.e. 360 degrees
    val step = Angle.one / samples
    def loop(count: Int): Image = {
      val angle = step * count
      count match {
        case 0 => Image.empty
        case n =>
          dot.at(f(angle)).on(loop(n - 1))
      }
    }

    loop(samples)
  }

  def sampleCurve(samples: Int, curve: Angle => Point): List[Point] = {
    // Angle.one is one complete turn. I.e. 360 degrees
    val step = Angle.one / samples
    def loop(count: Int): List[Point] = {
      count match {
        case 0 => List.empty
        case n =>
          val angle = step * count
          curve(angle) :: loop(n - 1)
      }
    }

    loop(samples)
  }

  def epicycle(a: Int, b: Int, c: Int): Angle => Point =
    (angle: Angle) =>
      (Point(75, angle * a).toVec + Point(32, angle * b).toVec + Point(
        15,
        angle * c
      ).toVec).toPoint

  {
    val nSamples = 700
    val dot = Image.circle(5).noStroke.fillColor(Color.darkBlue)

    drawCurve(nSamples, dot)(epicycle(1, 6, 14))
      .beside(drawCurve(nSamples, dot)(epicycle(7, 13, 25)))
      .beside(drawCurve(nSamples, dot)(epicycle(1, 7, -21)))
  }.save("cycles/epicycle")

  {
    val nSamples = 700

    Image
      .interpolatingSpline(sampleCurve(nSamples, epicycle(1, 6, 14)))
      .beside(
        Image.interpolatingSpline(sampleCurve(nSamples, epicycle(7, 13, 25)))
      )
      .beside(
        Image.interpolatingSpline(sampleCurve(nSamples, epicycle(1, 7, -21)))
      )
  }.save("cycles/epicurve")
}
