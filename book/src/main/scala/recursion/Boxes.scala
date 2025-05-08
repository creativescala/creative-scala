package creativescala
package recursion

import doodle.core.*
import doodle.image.*
import doodle.syntax.all.*
import doodle.image.syntax.all.*
import doodle.java2d.*

object Boxes {
  val aBox = Image.square(30).fillColor(Color.royalBlue)

  val sequentialBoxes =
    aBox.beside(aBox).beside(aBox).beside(aBox).beside(aBox)

  sequentialBoxes.save("recursion/sequential-boxes")

  def stackedBoxes(count: Int): Image =
    count match {
      case 0 => Image.empty
      case n => aBox.above(stackedBoxes(n - 1))
    }

  stackedBoxes(5).save("recursion/stacked-boxes")
}
