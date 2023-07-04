package creativescala
package expressive

import cats.implicits._
import doodle.core._
import doodle.syntax.all._
import doodle.image._
import doodle.image.syntax.all._
import doodle.java2d._

object Layout {
  Image
    .circle(100)
    .beside(Image.rectangle(100, 200))
    .save("expressive-expressions/circle-beside-rectangle")

  Image
    .circle(60)
    .beside(Image.circle(60))
    .beside(Image.circle(60))
    .on(Image.circle(180))
    .save("expressive-expressions/width-of-a-circle")
}
