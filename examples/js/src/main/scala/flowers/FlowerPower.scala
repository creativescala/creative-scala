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

@JSExportTopLevel("FlowersFlowerPower")
object FlowersFlowerPower {
  def sample(samples: Int, curve: Angle => Image): Image = {
    val step = Angle.one / samples
    def loop(count: Int): Image = {
      val angle = step * count
      count match {
        case 0 => Image.empty
        case n =>
          curve(angle).on(loop(n - 1))
      }
    }

    loop(samples)
  }
  val rose5 = (angle: Angle) => Point((angle * 5).cos, angle)

  def scale(r: Double): Point => Point = (pt: Point) =>
    Point(pt.r * r, pt.angle)

  def pinkDots(pt: Point): Image = {
    val color = Color.magenta.spin((-pt.r * 0.05).degrees)
    Image
      .circle(pt.r * 0.125 + 7)
      .on(Image.circle(pt.r * 0.085 + 3).noStroke.fillColor(color))
      .strokeColor(color)
      .at(pt)
  }

  def greenSquare(pt: Point): Image = {
    Image.square(7).rotate(pt.angle).noStroke.fillColor(Color.green).at(pt)
  }

  @JSExport
  def flowerPower(id: String): Unit = {
    val flower = sample(400, rose5.andThen(scale(275)).andThen(pinkDots))
    val leaves = sample(250, rose5.andThen(scale(225)).andThen(greenSquare))
      .rotate(37.5.degrees)
    val background = Image.square(600).fillColor(Color.black)
    val image = flower.on(leaves).on(background)

    image.drawWithFrame(Frame(id).withBackground(Color.black))
  }
}
