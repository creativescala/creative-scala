package creativescala
package fractals

import doodle.core.*
import doodle.image.*
import doodle.syntax.all.*
import doodle.image.syntax.all.*
import doodle.java2d.*

object AuxillaryParameter {

  def growingBoxes(count: Int, size: Int): Image =
    count match {
      case 0 => Image.empty
      case n =>
        Image
          .square(size)
          .beside(growingBoxes(n - 1, size + 10))
    }
  growingBoxes(5, 20).save("fractals/growing-boxes")

  val aBox = Image.square(40).fillColor(Color.royalBlue)
  def gradientBoxes(count: Int): Image =
    count match {
      case 0 => Image.empty
      case n =>
        aBox
          .fillColor(Color.royalBlue.spin((15 * n).degrees))
          .beside(gradientBoxes(n - 1))
    }
  gradientBoxes(5).save("fractals/gradient-boxes")

  def concentricCircles(count: Int, size: Int): Image =
    count match {
      case 0 => Image.empty
      case n =>
        Image
          .circle(size)
          .on(concentricCircles(n - 1, size + 5))
    }
  concentricCircles(20, 50).save("fractals/concentric-circles")

  def circle(size: Int, color: Color): Image =
    Image.circle(size).strokeWidth(3.0).strokeColor(color)

  def fadeCircles(n: Int, size: Int, color: Color): Image =
    n match {
      case 0 => Image.empty
      case n =>
        circle(size, color)
          .on(fadeCircles(n - 1, size + 7, color.fadeOutBy(0.05.normalized)))
    }

  def gradientCircles(n: Int, size: Int, color: Color): Image =
    n match {
      case 0 => Image.empty
      case n =>
        circle(size, color)
          .on(gradientCircles(n - 1, size + 7, color.spin(15.degrees)))
    }

  def image: Image =
    fadeCircles(20, 50, Color.red)
      .beside(gradientCircles(20, 50, Color.royalBlue))

  image.save("fractals/colorful-circles")
}
