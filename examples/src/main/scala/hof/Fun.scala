package polygons

import cats.implicits.*
import doodle.core.*
import doodle.syntax.all.*
import doodle.image.*
import doodle.image.syntax.all.*
import doodle.interact.animation.*
import doodle.interact.syntax.all.*
import doodle.svg.*
import scala.concurrent.duration.*
import cats.effect.unsafe.implicits.global
import scala.scalajs.js.annotation.*

@JSExportTopLevel("HofFun")
object HofFractals {
  def fold(count: Int, build: (Int, Image) => Image): Image =
    count match {
      case 0 => Image.empty
      case n => build(n, fold(count - 1, build))
    }

  @JSExport
  def sierpinskiExample(id: String): Unit = {
    val sierpinski: (Int, Image) => Image =
      (count, image) => image.above(image.beside(image))
  }

  @JSExport
  def gradientBoxesExample(id: String): Unit = {
    val gradientBoxes: (Int, Image) => Image =
      (count, image) =>
        Image
          .square(50)
          .fillColor(Color.royalBlue.spin(10.degrees * count))
          .noStroke
          .beside(image)

    fold(5, gradientBoxes).drawWithFrame(Frame(id))
  }

  @JSExport
  def growingCirclesExample(id: String): Unit = {
    val growingCircles: (Int, Image) => Image =
      (count, image) =>
        Image
          .circle(20 * count)
          .fillColor(Color.crimson.spin(10.degrees * count))
          .noStroke
          .beside(image)

    fold(5, growingCircles).drawWithFrame(Frame(id))
  }
}
