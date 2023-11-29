package creativescala
package methods

import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._

object WritingMethods {
  def box(color: Color, spin: Angle): Image =
    Image
      .rectangle(40, 40)
      .strokeWidth(5.0)
      .strokeColor(color.spin(spin + 30.degrees))
      .fillColor(color.spin(spin))

  def gradientBoxes(color: Color): Image = {
    box(color, 0.degrees)
      .beside(box(color, 15.degrees))
      .beside(box(color, 20.degrees))
      .beside(box(color, 45.degrees))
      .beside(box(color, 60.degrees))
  }

  gradientBoxes(Color.royalBlue).save("methods/gradient-boxes")
}
