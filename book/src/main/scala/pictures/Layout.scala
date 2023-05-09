package creativescala
package pictures

import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._

object Layout {
  Image.circle(10)
    .beside(Image.rectangle(10, 20))
    .save("expressive-expressions/circle-beside-rectangle")

  Image.circle(20)
    .beside(Image.circle(20))
    .beside(Image.circle(20))
    .on(Image.circle(60))
    .save("expressive-expressions/width-of-a-circle")
}
