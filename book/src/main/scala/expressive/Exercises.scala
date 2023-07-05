package creativescala
package expressive

import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._

object Exercises {
  Image
    .circle(40)
    .on(Image.circle(80))
    .on(Image.circle(120))
    .save("expressive-expressions/target1")

  Image
    .circle(40)
    .on(Image.circle(80))
    .on(Image.circle(120))
    .above(Image.rectangle(12, 40))
    .above(Image.rectangle(40, 12))
    .save("expressive-expressions/target2")

  Image
    .circle(40)
    .fillColor(Color.red)
    .on(Image.circle(80).fillColor(Color.white))
    .on(Image.circle(120).fillColor(Color.red))
    .above(Image.rectangle(12, 40).fillColor(Color.brown))
    .above(Image.rectangle(40, 12).fillColor(Color.brown))
    .above(Image.rectangle(160, 50).noStroke.fillColor(Color.green))
    .save("expressive-expressions/target3")
}
