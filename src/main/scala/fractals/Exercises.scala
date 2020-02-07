package creativescala
package fractals

import doodle.core._
import doodle.image._
import doodle.image.syntax._
import doodle.image.syntax.core._
import doodle.java2d._

object Exercises {
  def chessboard(count: Int, color: Color): Image =
    count match {
      case 0 =>
        val contrast = color.spin(180.degrees)
        val box = Image.square(20)
        box
          .fillColor(color)
          .beside(box.fillColor(contrast))
          .above(box.fillColor(contrast).beside(box.fillColor(color)))

      case n =>
        chessboard(n - 1, color.spin(17.degrees))
          .beside(chessboard(n - 1, color.spin(-7.degrees)))
          .above(
            chessboard(n - 1, color.spin(-19.degrees))
              .beside(chessboard(n - 1, color.spin(3.degrees)))
          )
    }

  chessboard(3, Color.royalBlue).save("fractals/chessboard-2")
}
