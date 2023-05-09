package creativescala
package cycles

import cats.implicits._
import doodle.core._
import doodle.syntax.all._
import doodle.image._
import doodle.image.syntax.all._
import doodle.java2d._

object Composition {
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
    .save("cycles/composed")

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

  drawCurve(32, growingCircle).save("cycles/growing-circle")
}
