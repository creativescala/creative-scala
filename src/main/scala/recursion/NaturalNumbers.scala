package creativescala
package recursion

import doodle.core._
import doodle.image._
import doodle.image.syntax._
import doodle.image.syntax.core._
import doodle.java2d._

object NaturalNumbers {
  val star = Image
    .star(5, 30, 15, 45.degrees)
    .strokeColor(Color.teal)
    .on(Image.star(5, 12, 7, 45.degrees).strokeColor(Color.royalBlue))
    .strokeWidth(5.0)

  val circle = Image
    .circle(60)
    .strokeColor(Color.midnightBlue)
    .on(Image.circle(24).strokeColor(Color.plum))
    .strokeWidth(5.0)

  def alternatingRow(count: Int): Image = {
    count match {
      case 0 => Image.empty
      case n =>
        if (n % 2 == 0) star.beside(alternatingRow(n - 1))
        else circle.beside(alternatingRow(n - 1))
    }
  }

  alternatingRow(5).save("recursion/alternating-row")

  def funRow(count: Int): Image = {
    count match {
      case 0 => Image.empty
      case n =>
        Image
          .star(7, (10 * n), (7 * n), 45.degrees)
          .strokeColor(Color.fireBrick.spin((5 * n).degrees))
          .strokeWidth(7.0)
          .beside(funRow(n - 1))
    }
  }

  funRow(5).save("recursion/fun-row")
}
