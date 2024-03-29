package creativescala
package cycles

import cats.implicits._
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._

object Interpolation {

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

  def scale(factor: Double): Point => Point =
    (point: Point) => Point(point.r * factor, point.angle)

  val parametricCircle: Angle => Point =
    (angle: Angle) => Point(1.0, angle)

  val parametricSpiral: Angle => Point =
    (angle: Angle) => Point(Math.exp(angle.toTurns - 1), angle)

  def lissajous(a: Int, b: Int, offset: Angle): Angle => Point =
    (angle: Angle) => Point(((angle * a) + offset).sin, (angle * b).sin)

  {
    val nSamples = 350

    Image
      .path(
        OpenPath.interpolatingSpline(
          sampleCurve(nSamples, parametricSpiral.andThen(scale(100)))
        )
      )
      .beside(
        Image
          .path(
            ClosedPath.interpolatingSpline(
              sampleCurve(nSamples, parametricCircle.andThen(scale(100)))
            )
          )
      )
      .strokeColor(Color.darkBlue)
  }.save("cycles/interpolation")

  {
    val nSamples = 350
    // Color palette from https://openprocessing.org/sketch/1643153
    val palette = Array(
      Color.rgb(146, 55, 77),
      Color.rgb(140, 83, 131),
      Color.rgb(107, 86, 142),
      Color.rgb(74, 88, 153),
      Color.rgb(110, 140, 178),
      Color.rgb(145, 191, 202),
      Color.rgb(169, 185, 187),
      Color.rgb(193, 178, 171)
    )
    val paths =
      for {
        a <- 1.to(4)
        b <- 2.to(5)
      } yield Image
        .path(
          ClosedPath
            .interpolatingSpline(
              sampleCurve(
                nSamples,
                lissajous(a, b, Angle.zero).andThen(scale(250))
              )
            )
        )
        .strokeColor(palette((a + b) % palette.length))
        .strokeDash(Array(a.toDouble, 1.0, 2.0, b.toDouble))

    paths.toList.allOn
  }.save("cycles/lissajous-stack")
}
