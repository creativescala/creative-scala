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

@JSExportTopLevel("FlowersFold")
object Fold {
  @JSExport
  def stack(id: String): Unit = {
    def fold(count: Int, build: (Int, Image) => Image): Image =
      count match {
        case 0 => Image.empty
        case n => build(n, fold(count - 1, build))
      }
    val aBox =
      Image.square(20).fillColor(Color.royalBlue).strokeColor(Color.crimson)

    val stack = (count: Int, image: Image) => aBox.beside(image)

    fold(5, stack).drawWithFrame(Frame(id))
  }
}
