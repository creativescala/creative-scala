package creativescala
package hof

import cats.implicits._
import doodle.core._
import doodle.image._
import doodle.image.syntax._
import doodle.image.syntax.core._
import doodle.java2d._

object Flowers {

  def sample(samples: Int, dot: Image)(f: Angle => Point): Image = {
    // Angle.one is one complete turn. I.e. 360 degrees
    val step = Angle.one / samples
    def loop(count: Int): Image = {
      val angle = step * count
      count match {
        case 0 => Image.empty
        case n =>
          dot.at(f(angle)).on(loop(n - 1))
      }
    }

    loop(samples)
  }

  val dottedRose =
    sample(
      288,
      Image
        .regularPolygon(6, 5, 0.degrees)
        .fillColor(Color.magenta)
        .strokeColor(Color.aqua)
        .strokeWidth(3.0)
        .on(Image.regularPolygon(6, 9, 0.degrees).fillColor(Color.black))
    ) { angle =>
      Point((angle * 7).cos * 300, angle)
    }.save("hof/rose7")

  def rose(k: Int): Angle => Point =
    (angle: Angle) => Point((angle * k).cos * 150, angle)

  {
    val nSamples = 500
    val dot = Image.circle(5).noStroke.fillColor(Color.darkSlateBlue)

    sample(nSamples, dot)(rose(5))
      .beside(sample(nSamples, dot)(rose(8)))
      .beside(sample(nSamples, dot)(rose(9)))
  }.save("hof/rose")


  def lissajous(a: Int, b: Int, offset: Angle): Angle => Point =
    (angle: Angle) =>
      Point(100 * ((angle * a) + offset).sin, 100 * (angle * b).sin)

  {
    val nSamples = 300
    val dot = Image.circle(5).noStroke.fillColor(Color.crimson)

    (for (a <- List(1,2,3)) yield {
       (for(b <- List(1,2,3)) yield {
         sample(nSamples, dot)(lissajous(a,b, 90.degrees))
       }).foldLeft(Image.empty)(_.beside(_))
    }).foldLeft(Image.empty)(_.above(_))
  }.save("hof/lissajous")


  def epicycloid(a: Int, b: Int, c: Int): Angle => Point =
    (angle: Angle) =>
      (Point(75, angle * a).toVec + Point(32, angle * b).toVec + Point(15, angle * c).toVec).toPoint

  {
    val nSamples = 700
    val dot = Image.circle(5).noStroke.fillColor(Color.forestGreen)

    sample(nSamples, dot)(epicycloid(1, 6, 14))
      .beside(sample(nSamples, dot)(epicycloid(7, 13, 25)))
      .beside(sample(nSamples, dot)(epicycloid(1, 7, -21)))
  }.save("hof/epicycloid")
}
