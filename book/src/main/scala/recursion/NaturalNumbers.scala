package creativescala
package recursion

import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._

object NaturalNumbers {
  val star = Image
    .star(5, 30, 15)
    .strokeColor(Color.teal)
    .on(Image.star(5, 12, 7).strokeColor(Color.royalBlue))
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
          .star(7, (10 * n), (7 * n))
          .strokeColor(Color.orangeRed.spin((5 * n).degrees))
          .strokeWidth(7.0)
          .beside(funRow(n - 1))
    }
  }

  funRow(5).save("recursion/fun-row")

  def cross(count: Int): Image = {
    count match {
      case 0 =>
        Image
          .regularPolygon(6, 10)
          .strokeColor(Color.deepSkyBlue.spin(-180.degrees))
          .strokeWidth(5.0)
      case n =>
        val circle = Image
          .circle(20)
          .strokeColor(Color.deepSkyBlue)
          .on(Image.circle(15).strokeColor(Color.deepSkyBlue.spin(-15.degrees)))
          .on(Image.circle(10).strokeColor(Color.deepSkyBlue.spin(-30.degrees)))
          .strokeWidth(5.0)
        circle.beside(circle.above(cross(n - 1)).above(circle)).beside(circle)
    }
  }
  val spacer = Image.square(30).noFill.noStroke

  cross(0)
    .beside(spacer)
    .beside(cross(1))
    .beside(spacer)
    .beside(cross(2))
    .beside(spacer)
    .beside(cross(3))
    .save("recursion/cross")
}
