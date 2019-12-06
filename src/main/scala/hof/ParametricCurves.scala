package creativescala
package hof

import cats.implicits._
import doodle.core._
import doodle.image._
import doodle.image.syntax._
import doodle.image.syntax.core._
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
      .beside(sampleCircle(List(0.degrees, 45.degrees, 90.degrees, 135.degrees, 180.degrees, 225.degrees, 270.degrees, 315.degrees)))
      .beside(Image.square(10).noStroke.noFill)
      .beside(sampleCircle(List(0.degrees, (22.5).degrees, 45.degrees, (67.5).degrees, 90.degrees, 112.5.degrees, 135.degrees, 157.5.degrees, 180.degrees, 202.5.degrees, 225.degrees, 247.5.degrees, 270.degrees, 292.5.degrees, 315.degrees, 337.5.degrees)))
      .save("hof/parametric-circles")
}
