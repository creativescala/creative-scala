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
}
