package creativescala
package hof

import cats.implicits._
import doodle.core._
import doodle.syntax.all._
import doodle.image._
import doodle.image.syntax.all._
import doodle.java2d._

object Hof {
  val dropShadow = (image: Image) =>
    image.on(image.strokeColor(Color.black).fillColor(Color.black).at(5, -5))

  val mirrored = (image: Image) =>
    image.beside(image.transform(Transform.horizontalReflection))

  val composed = mirrored.andThen(dropShadow)

  val star = Image
    .star(5, 150, 50)
    .fillColor(Color.fireBrick)
    .strokeColor(Color.dodgerBlue)
    .strokeWidth(7.0)
  dropShadow(star)
    .beside(mirrored(star))
    .beside(composed(star))
    .save("hof/composed")

  val parametricCircle: Angle => Point =
    (angle: Angle) => Point(1.0, angle)

  val parametricSpiral: Angle => Point =
    (angle: Angle) => Point(Math.exp(angle.toTurns - 1), angle)

  def scale(factor: Double): Point => Point =
    (point: Point) => point.scaleLength(factor)

  val circle100 = parametricCircle.andThen(scale(100))
  val circle200 = parametricCircle.andThen(scale(200))
  val circle300 = parametricCircle.andThen(scale(300))

  val growingDot: Point => Image =
    (pt: Point) => Image.circle(pt.angle.toTurns * 20).at(pt)

  val growingCircle = parametricCircle
    .andThen(scale(100))
    .andThen(growingDot)

  def sample(samples: Int, curve: Angle => Image): Image = {
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

  sample(32, growingCircle).save("hof/growing-circle")

  def concentricShapes(count: Int, singleShape: Int => Image): Image =
    count match {
      case 0 => Image.empty
      case n => singleShape(n).on(concentricShapes(n - 1, singleShape))
    }

  def colored(shape: Int => Image, color: Int => Color): Int => Image =
    (n: Int) => shape(n).strokeWidth(10).strokeColor(color(n))

  def fading(n: Int): Color =
    Color.blue.fadeOut((1 - n / 20.0).normalized)

  def spinning(n: Int): Color =
    Color.blue.desaturate(0.5.normalized).spin((n * 30).degrees)

  def size(n: Int): Double =
    100 + 24 * n

  def circle(n: Int): Image =
    Image.circle(size(n))

  def square(n: Int): Image =
    Image.square(size(n))

  def triangle(n: Int): Image =
    Image.triangle(size(n), size(n))

  concentricShapes(10, colored(circle, spinning))
    .beside(
      concentricShapes(10, colored(triangle, fading))
        .beside(concentricShapes(10, colored(square, spinning)))
    )
    .save("hof/colors-and-shapes")

  def dottyCircle(n: Int): Image =
    sample(
      72,
      parametricCircle.andThen(scale(100 + n * 24)).andThen(growingDot)
    )

  concentricShapes(10, colored(dottyCircle, spinning))
    .save("hof/concentric-dotty-circle")
}
