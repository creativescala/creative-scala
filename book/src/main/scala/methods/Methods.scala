package creativescala
package methods

import doodle.core.*
import doodle.image.*
import doodle.syntax.all.*
import doodle.image.syntax.all.*
import doodle.java2d.*

object Methods {
  val box =
    Image
      .rectangle(40, 40)
      .strokeWidth(5.0)
      .strokeColor(Color.royalBlue.spin(30.degrees))
      .fillColor(Color.royalBlue)

  val sequentialBoxes = box.beside(box).beside(box).beside(box).beside(box)

  sequentialBoxes.save("methods/sequential-boxes")
}
