package creativescala
package pictures

import doodle.core._
import doodle.image._
import doodle.image.syntax._
import doodle.image.syntax.core._
import doodle.java2d._

object Images {
  import doodle.core.Color

  Image.circle(10).save("pictures/circle")
  Image.rectangle(100, 50).save("pictures/rectangle")
  Image.triangle(60, 40).save("pictures/triangle")
}
