package creativescala
package expressive

import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._

object ColorSection {
  Image
    .circle(100)
    .fillColor(Color.wheat)
    .strokeColor(Color.midnightBlue)
    .strokeWidth(7)
    .save("expressive-expressions/color-example")

  Image
    .circle(30)
    .fillColor(Color.black)
    .on(Image.circle(60).fillColor(Color.cornflowerBlue))
    .on(Image.circle(90).fillColor(Color.white))
    .on(Image.circle(150).fillColor(Color.darkBlue))
    .save("expressive-expressions/evil-eye")
}
