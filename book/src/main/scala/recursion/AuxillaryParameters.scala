package creativescala
package recursion

import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._

object AuxillaryParameters {
  val aBox = Image.square(20).fillColor(Color.royalBlue)

  def gradientBoxes(n: Int, color: Color): Image =
    n match {
      case 0 => Image.empty
      case n =>
        aBox
          .fillColor(color)
          .beside(gradientBoxes(n - 1, color.spin(15.degrees)))
    }
}
