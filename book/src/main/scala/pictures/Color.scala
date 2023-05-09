package creativescala
package pictures

import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._

object ColorSection {
  Image
    .circle(10)
    .fillColor(Color.black)
    .on(Image.circle(20).fillColor(Color.cornflowerBlue))
    .on(Image.circle(30).fillColor(Color.white))
    .on(Image.circle(50).fillColor(Color.darkBlue))
    .save("expressive-expressions/evil-eye")
}
