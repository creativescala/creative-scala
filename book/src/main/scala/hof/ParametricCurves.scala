package creativescala
package hof

import cats.implicits._
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._

object ParametricCurves {
  val location = Point(80, 150)

  val point =
    Image.circle(5).fillColor(Color.black).at(location)

  val axes =
    Image
      .line(200, 0)
      .at(80, 0)
      .on(Image.line(0, 200).at(0, 80))
      .strokeWidth(2.0)

  val yIntercept =
    Image
      .line(location.x, 0)
      .at(location.x / 2, location.y)
      .strokeColor(Color.gray)

  val xIntercept =
    Image
      .line(0, location.y)
      .at(location.x, location.y / 2)
      .strokeColor(Color.gray)

  val r =
    Image
      .line(location.x, location.y)
      .at(location.x / 2, location.y / 2)
      .strokeColor(Color.gray)

  val cartesian =
    point
      .on(axes)
      .on(yIntercept)
      .on(xIntercept)

  val pad = Image.square(40).noStroke.noFill

  val polar =
    point
      .on(axes)
      .on(r)

  val representation =
    cartesian.beside(pad).beside(polar).save("hof/representation")

  def circle(angle: Angle): Point =
    Point(100, angle)

  def sampleCircle(angles: List[Angle]): Image =
    angles
      .map(circle _)
      .map(pt => Image.circle(10).at(pt))
      .allOn
      .fillColor(Color.crimson)
      .strokeColor(Color.fireBrick)
      .strokeWidth(3.0)

  val parametricCircles =
    sampleCircle(List(0.degrees, 90.degrees, 180.degrees, 270.degrees))
      .beside(Image.square(10).noStroke.noFill)
      .beside(
        sampleCircle(
          List(
            0.degrees,
            45.degrees,
            90.degrees,
            135.degrees,
            180.degrees,
            225.degrees,
            270.degrees,
            315.degrees
          )
        )
      )
      .beside(Image.square(10).noStroke.noFill)
      .beside(
        sampleCircle(
          List(
            0.degrees,
            (22.5).degrees,
            45.degrees,
            (67.5).degrees,
            90.degrees,
            112.5.degrees,
            135.degrees,
            157.5.degrees,
            180.degrees,
            202.5.degrees,
            225.degrees,
            247.5.degrees,
            270.degrees,
            292.5.degrees,
            315.degrees,
            337.5.degrees
          )
        )
      )
      .save("hof/parametric-circles")

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

  val triangleCircle =
    sample(
      72,
      Image
        .triangle(10, 10)
        .fillColor(Color.limeGreen)
        .strokeColor(Color.lawngreen)
    ) { angle =>
      Point(200, angle)
    }.save("hof/triangle-circle")

  val dottedRose =
    sample(
      144,
      Image
        .regularPolygon(6, 9)
        .fillColor(Color.magenta)
        .strokeColor(Color.aqua)
        .strokeWidth(3.0)
        .on(Image.regularPolygon(6, 14).fillColor(Color.black))
    ) { angle =>
      Point((angle * 7).cos * 300, angle)
    }.save("hof/rose")

  // Debug
  val debug = {
    val c = Image.circle(40)
    val c1 = c.beside(c.at(10, 10)).beside(c.at(10, -10)).debug
    val c2 = c.debug.beside(c.at(10, 10).debug).beside(c.at(10, -10).debug)
    val c3 = c.debug.beside(c.debug.at(10, 10)).beside(c.debug.at(10, -10))
    c1.above(c2).above(c3)
  }
  debug.save("hof/debug")

  def parametricCircle(angle: Angle): Point =
    Point.polar(200, angle)

  def parametricSpiral(angle: Angle): Point =
    Point((Math.exp(angle.toTurns) - 1) * 200, angle)

  // Using color palette from https://www.color-hex.com/color-palette/1138
  val psychedelicSpirals =
    sample(100, Image.circle(5).fillColor(Color.rgb(232, 208, 25)))(
      parametricSpiral
    ).on(
      sample(100, Image.circle(5).fillColor(Color.rgb(4, 102, 46)))(
        parametricSpiral
      ).rotate(90.degrees)
        .on(
          sample(100, Image.circle(5).fillColor(Color.rgb(44, 137, 179)))(
            parametricSpiral
          ).rotate(180.degrees)
            .on(
              sample(100, Image.circle(5).fillColor(Color.rgb(51, 29, 8)))(
                parametricSpiral
              ).rotate(270.degrees)
                .on(
                  sample(
                    100,
                    Image.circle(5).fillColor(Color.rgb(222, 40, 45))
                  )(parametricCircle)
                )
            )
        )
    )

  psychedelicSpirals.save("hof/psychedelic-spirals")
}
