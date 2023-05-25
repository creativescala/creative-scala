# Epicycles

```scala mdoc:invisible
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._
```

We're now going to see a different example of composition, in creating a type of curve known as an epicycle. An epicycle is produced when we trace a point on a circle rotating on another circle. We can stack circles on top of circles, and change the speed at which they rotate, to produce many different curves. Here's an example using two circles.

@:doodle("epicycle", "CyclesEpicycles.epicycleTwoWheels")

The parametric curve that produces this result is

```scala mdoc:silent
val twoWheels: Angle => Point =
  (angle: Angle) =>
    Point(75, angle * 6) + Point(32, angle * 11).toVec
```

Let's walk through this code. We know that a parametric circle is

```scala mdoc:silent
(angle: Angle) => Point(1.0, angle)
```

where I've chosen the radius to be 1. So

```scala mdoc:silent
(angle: Angle) => Point(75, angle * 6)
```

is a parametric circle with radius 75, which is spinning six times faster.
By the same reasoning

```scala mdoc:silent
(angle: Angle) => Point(32, angle * 11)
```

is a parametric circle with radius 32 spinning eleven times faster.

The remaining portion of the code is a conversion from `Point` to `Vec` (the call to `toVec`) and addition of a `Point` and a `Vec`. 
This moves the center of the smaller circle to the current point on the larger circle. 
The conversion from `Point` to `Vec` is needed for technical reasons which I'll attempt to briefly explain. 
A point specifies a location relative to a fixed origin. 
A vector represents a displacement relative to some arbitrary starting point. 
Therefore we can add a vector to a point to get a new point that is shifted by the vector.
Adding a point to a point, however, is not possible as points are both locations relative to the same origin, not displacements like a vector is.
If this doesn't make sense there are many online resources that describe vectors.


For our example we are going to fix the number and radius of the circles and allow their speed of rotation to vary. Here is the code:

```scala
def epicycle(a: Int, b: Int, c: Int): Angle => Point =
  (angle: Angle) =>
    (Point(75, angle * a).toVec + Point(32, angle * b).toVec + Point(15, angle * c).toVec).toPoint
```

You might notice this code converts points to vectors and back again. This is a little technical detail (we cannot add points but we can add vectors) that isn't important if you aren't familiar with vectors.

Below are three examples created by choosing the parameters a, b, c, as (1, 6, 14), (7, 13, 25), and (1, 7, -21) respectively.

@:figure{ img = "./epicycle.svg", key = "#fig:cycles:epicycle", caption = "Examples of epicycle curves, with the parameters chosen as (1, 6, 14), (7, 13, 25), and (1, 7 -21)." }
