## Parametric Curves

```scala mdoc:invisible
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._
```

We're now going to learn another tool for creating interesting shapes, called a *parametric curve* or *parametric equation*. 
A parametric curve is a function from some input (the parameter in "parametric") to a point.
For example, a parametric equation for a circle might have as its input an angle and it would give us the point on the circle at that angle.
In Scala we could write

```scala mdoc
def parametricCircle(angle: Angle): Point =
  Point(100, angle)
```

choosing the arbitrary value of `100` as the radius. 

The input to a parametric curve tells us how far along the curve we are. In the example of a circle above, the input would start at 0 degrees and finish at 360 degrees.

If we choose lots of different values for the input, and then draw a shape at each point we get back from the parametric equation, we can suggest the shape of the parametric curve. The example below shows this, choosing twelve evenly spaced angles and drawing a circle at each point. As you should notice, this is very similar to how we went about creating polygons.

@:doodle("parametric-circle", "HofParametricCircle.circle")

@:exercise(Drawing a Parametric Curve)
Implement a method `drawCurve` with the skeleton below.

```scala
def drawCurve(points: Int, marker: Image, curve: Angle => Point): Image =
  ???
```

The parameters have the following meaning:

- `points` is the number of evenly spaced points around the circle from which we're sampling the parametric curve;
- `marker` is the `Image` we draw at each point we sample from the circle; and
- `curve` is the parametric curve.

Use this to draw points around the parametric circle, as shown above.
@:@

@:solution
This is a modification of code we wrote in the previous chapter. It's also a structural recursion over the natural numbers.

```scala mdoc:silent
def drawCurve(points: Int, marker: Image, curve: Angle => Point): Image = {
  // Angle.one is one complete turn. I.e. 360 degrees
  val turn = Angle.one / points
  def loop(count: Int): Image = {
    count match {
      case 0 => marker.at(curve(Angle.zero))
      case n =>
        marker.at(curve(turn * count)).on(loop(n - 1))
    }
  }
  
  loop(points)
}
```
@:@

@:exercise(Parametric Spirals)
Let's now see a little bit of what we can do with parametric curves. To create a circle we keep the radius constant as the angle increases. If, instead, the radius increases as the angle increases we'll get a spiral. (How quickly should the radius increase? It's up to you! Different choices will give you different spirals.)

Implement a function or method `parametricSpiral` that creates a spiral.
@:@

@:solution
Here's a type of spiral, known as a logarithmic spiral, that has a particularly pleasing shape. Draw it and see for yourself!

```scala mdoc:silent
def parametricSpiral(angle: Angle): Point =
  Point((Math.exp(angle.toTurns) - 1) * 200, angle)
```
@:@

@:exercise(Expressive Drawing)
Modify `drawCurve` so that `marker` is a function from `Point` to `Image`. The point passed to `marker` should be the point produced by the parametric curve. This allows the marker image to depend on the point so we can, for example, make the marker image bigger as the radius increases, or change its color in response to the angle. The picture below shows an example using this new expressivity.

@:doodle("changing-spiral", "HofParametricCurve.spiral")
@:@

@:solution
This is a small modification to `drawCurve`.

```scala mdoc:reset:invisible
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._

def parametricSpiral(angle: Angle): Point =
  Point((Math.exp(angle.toTurns) - 1) * 200, angle)
```
```scala mdoc:silent
def drawCurve(
    points: Int,
    marker: Point => Image,
    curve: Angle => Point
): Image = {
  // Angle.one is one complete turn. I.e. 360 degrees
  val turn = Angle.one / points
  def loop(count: Int): Image = {
    count match {
      case 0 =>
        val pt = curve(Angle.zero)
        marker(pt).at(pt)
      case n =>
        val pt = curve(turn * count)
        marker(pt).at(pt).on(loop(n - 1))
    }
  }

  loop(points)
}
```

Here's how I created the example image using the new version of `drawCurve`.

```scala mdoc:silent
val marker = (point: Point) =>
  Image
    .circle(point.r * 0.125 + 7)
    .fillColor(Color.red.spin(point.angle / -4.0))
    .noStroke

val image = drawCurve(20, marker, parametricSpiral)
```
@:@
