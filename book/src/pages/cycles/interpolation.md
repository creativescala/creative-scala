# Interpolation

```scala mdoc:invisible
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._
```

Interpolation is our final topic before we can create the pictures we're after.
Interpolation means guessing at a value inbetween points.
In our case we're going to use interpolation to create smooth curves that connect the points we sample from our parametric curves.

The example below sampling between 3 and 15 points from a parametric circle, and then interpolating a curve between these points.

@:doodle("circle-interpolation", "CyclesInterpolation.circleInterpolation")

We can see that the curve more closely resembles a circle as we increase the number of points we base it off.

We use the `Image.interpolatingSpline` method to interpolate a curve. 
The method expects a `List[Point]`, which is a type that means a list of `Points`. 
We haven't worked with lists before, so it's time to learn a little bit about them.

A `List` is what's known as a container, meaning it holds other values.

We can construct a 
