package creativescala
package pictures

import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._

object Images {
  import doodle.core.Color

  Image.circle(10).save("expressive-expressions/circle")
  Image.rectangle(100, 50).save("expressive-expressions/rectangle")
  Image.triangle(60, 40).save("expressive-expressions/triangle")
}
