package flowers

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

@JSExportTopLevel("FlowersSierpinski")
object FlowersSierpinski {
  def fold(count: Int, empty: Image, build: (Int, Image) => Image): Image =
    count match {
      case 0 => empty
      case n => build(n, fold(count - 1, empty, build))
    }

  @JSExport
  def sierpinskiExample(id: String): Unit = {
    val sierpinski: (Int, Image) => Image =
      (count, image) => image.above(image.beside(image))

    fold(
      5,
      Image.equilateralTriangle(10).strokeColor(Color.hotpink),
      sierpinski
    ).drawWithFrame(Frame(id))
  }
}
