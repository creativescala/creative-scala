package polygons

import cats.implicits.*
import doodle.core.*
import doodle.syntax.all.*
import doodle.interact.animation.Interpolation
import doodle.interact.syntax.all.*
import doodle.svg.*
import scala.concurrent.duration.*
import cats.effect.unsafe.implicits.global
import scala.scalajs.js.annotation.*

@JSExportTopLevel("Polygons")
object Polygons {
  def makeFrame(id: String): Frame =
    Frame(id).withSize(200, 200)

  @JSExport
  def points(id: String): Unit = {
    val background = Picture.regularPolygon(6, 100)

    background.drawWithFrame(makeFrame(id))
  }
}
