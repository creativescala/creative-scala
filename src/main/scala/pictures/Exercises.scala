package creativescala
package pictures

import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._

object Exercises {
  Image
    .circle(20)
    .on(Image.circle(40))
    .on(Image.circle(60))
    .save("pictures/target1")

  Image
    .circle(20)
    .on(Image.circle(40))
    .on(Image.circle(60))
    .above(Image.rectangle(6, 20))
    .above(Image.rectangle(20, 6))
    .save("pictures/target2")

  Image
    .circle(20).fillColor(Color.red)
    .on(Image.circle(40).fillColor(Color.white))
    .on(Image.circle(60).fillColor(Color.red))
    .above(Image.rectangle(6, 20).fillColor(Color.brown))
    .above(Image.rectangle(20, 6).fillColor(Color.brown))
    .above(Image.rectangle(80, 25).noStroke.fillColor(Color.green))
    .save("pictures/target3")
}
