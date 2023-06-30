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

  def sampleCurve(samples: Int, stop: Angle = Angle.one)(
      curve: Angle => Point
  ): List[Point] = {
    // Angle.one is one complete turn. I.e. 360 degrees
    val step = stop / samples
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

  val nSamples = 350

  Image
    .path(
      ClosedPath.interpolatingSpline(
        sampleCurve(nSamples)(epicycle(1, 6, 14))
      )
    )
    .beside(
      Image.path(
        ClosedPath.interpolatingSpline(
          sampleCurve(nSamples)(epicycle(7, 13, 25))
        )
      )
    )
    .beside(
      Image.path(
        ClosedPath.interpolatingSpline(
          sampleCurve(nSamples)(epicycle(1, 7, -21))
        )
      )
    )
    .strokeColor(Color.darkBlue)
    .save("cycles/epicurve")

  Image
    .path(
      ClosedPath.interpolatingSpline(
        sampleCurve(nSamples, 1.turns)(epicycle(1, 8, 22))
      )
    )
    .strokeColor(Color.darkBlue)
    .save("cycles/epicycle-7fold")

  Image
    .path(
      ClosedPath.interpolatingSpline(
        sampleCurve(nSamples, 1.turns)(epicycle(1, -8, 22))
      )
    )
    .strokeColor(Color.darkBlue)
    .save("cycles/epicycle-7fold-reverse")

  Image
    .path(
      ClosedPath
        .interpolatingSpline(
          (sampleCurve(nSamples, 1.turns)(epicycle(7, 17, 29)))
        )
    )
    .above(
      Image
        .path(
          ClosedPath
            .interpolatingSpline(
              (sampleCurve(nSamples, 3.turns)(epicycle(7, 17, 29)))
            )
        )
    )
    .above(
      Image
        .path(
          ClosedPath
            .interpolatingSpline(
              (sampleCurve(nSamples, 10.turns)(epicycle(7, 17, 29)))
            )
        )
    )
    .strokeColor(Color.darkBlue)
    .save("cycles/epicycle-asymmetrical")
}
