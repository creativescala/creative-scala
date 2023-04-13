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

@JSExportTopLevel("PolygonsDebug")
object Debug {
  @JSExport
  def debug(id: String): Unit = {
    val c = Image.circle(40)
    val c1 = c.beside(c.at(10, 10)).beside(c.at(10, -10)).debug
    val c2 = c.debug.beside(c.at(10, 10).debug).beside(c.at(10, -10).debug)
    val c3 = c.debug.beside(c.debug.at(10, 10)).beside(c.debug.at(10, -10))
    val example = c1.above(c2).above(c3)

    example.drawWithFrame(Frame(id))
  }
}
