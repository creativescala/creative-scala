package creativescala
package expanding

import cats.implicits._
import doodle.core._
import doodle.syntax.all._
import doodle.image._
import doodle.image.syntax.all._
import doodle.java2d._

object Images {
  Image.circle(100).save("expanding-expressions/circle")
  Image.rectangle(100, 50).save("expanding-expressions/rectangle")
  Image.triangle(120, 80).save("expanding-expressions/triangle")
}
