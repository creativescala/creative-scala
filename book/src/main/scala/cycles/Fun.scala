package creativescala
package cycles

import cats.implicits._
import doodle.core._
import doodle.syntax.all._
import doodle.image._
import doodle.image.syntax.all._
import doodle.java2d._

object Fun {
  val parametricCircle: Angle => Point =
    (angle: Angle) => Point(1.0, angle)

  def scale(factor: Double): Point => Point =
    (point: Point) => point.scaleLength(factor)

  val growingDot: Point => Image =
    (pt: Point) => Image.circle(pt.angle.toTurns * 20).at(pt)

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

  def concentricShapes(count: Int, singleShape: Int => Image): Image =
    count match {
      case 0 => Image.empty
      case n => singleShape(n).on(concentricShapes(n - 1, singleShape))
    }

  val blackCircles: Image =
    concentricShapes(10, (n: Int) => Image.circle(100 + 24 * n))

// Converting a method to a function:
  def redCircle(n: Int): Image =
    Image.circle(100 + 24 * n).strokeColor(Color.red)

  val redCircles: Image =
    concentricShapes(10, redCircle _)

  blackCircles.beside(redCircles).save("cycles/red-black-circles")

  def strokeColor(color: Color): Image => Image =
    image => image.strokeColor(color)

  def size(n: Int): Int =
    100 + 24 * n

  def withSizeOn(f: Int => Image, g: Int => Image): Int => Image =
    size => f(size).on(g(size))

  def circle(n: Int): Image =
    Image.circle(n)

  def square(n: Int): Image =
    Image.square(n)

  concentricShapes(
    10,
    size
      .andThen(withSizeOn(circle, square))
      .andThen(strokeColor(Color.royalBlue))
  ).save("cycles/combinator")

  def colored(shape: Int => Image, color: Int => Color): Int => Image =
    (n: Int) => shape(n).strokeWidth(10).strokeColor(color(n))

  def fading(n: Int): Color =
    Color.blue.fadeOut((1 - n / 20.0).normalized)

  def spinning(n: Int): Color =
    Color.blue.desaturate(0.5.normalized).spin((n * 30).degrees)

  def triangle(n: Int): Image =
    Image.triangle(n, n)

  concentricShapes(10, colored(size.andThen(circle), spinning))
    .beside(
      concentricShapes(10, colored(size.andThen(triangle), fading))
        .beside(concentricShapes(10, colored(size.andThen(square), spinning)))
    )
    .save("cycles/colors-and-shapes")

  def dottyCircle(n: Int): Image =
    drawCurve(
      72,
      parametricCircle.andThen(scale(100 + n * 24)).andThen(growingDot)
    )

  concentricShapes(10, colored(dottyCircle, spinning))
    .save("cycles/concentric-dotty-circle")

  def outlinedCircle(n: Int): Image =
    Image.circle(n * 10)

  concentricShapes(10, outlinedCircle _).save("cycles/colors-and-shapes-step1")
}
