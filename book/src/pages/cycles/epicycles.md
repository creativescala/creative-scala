# Epicycles

```scala mdoc:invisible
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._
```

We're now going to see a different example of composition, in creating a type of curve known as an epicycle. 
An epicycle is produced when we trace a point on a circle rotating on another circle. 
We can stack circles on top of circles, and change the speed at which they rotate, to produce many different curves. 
Here's an example using two circles.

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

is a parametric circle with radius 75, which is spinning six times faster than normal.
By the same reasoning

```scala mdoc:silent
(angle: Angle) => Point(32, angle * 11)
```

is a parametric circle with radius 32 spinning eleven times faster than normal.

The remaining portion of the code is a conversion from `Point` to `Vec` (the call to `toVec`) and addition of a `Point` and a `Vec`. 

```scala
Point(..., ...) + Point(..., ...).toVec
```

This moves the center of the smaller circle to the current point on the larger circle. 
The conversion from `Point` to `Vec` is needed for technical reasons which I'll attempt to briefly explain. 
A point specifies a location relative to a fixed origin. 
A vector represents a displacement relative to some arbitrary starting point. 
Therefore we can add a vector to a point to get a new point that is shifted by the vector.
Adding a point to a point, however, is not possible as points are both locations relative to the same origin, not displacements like a vector is.
(If this explanation doesn't work for you, there are many online resources that describe vectors.)

This is everything we need to build our own curves based on epicycles.
The more circles we add, the more complex the curve we can create.
The examples below are created using the following code. 
It uses three circles with fixed radius, but allows us to change the speed of rotation.

```scala
def epicycle(a: Int, b: Int, c: Int): Angle => Point =
  (angle: Angle) =>
    (Point(75, angle * a).toVec + Point(32, angle * b).toVec + Point(15, angle * c).toVec).toPoint
```

The examples are created by choosing the parameters a, b, c, as (1, 6, 14), (7, 13, 25), and (1, 7, -21) respectively.

@:figure{ img = "./epicurve.svg", key = "#fig:cycles:epicycle", caption = "Examples of epicycle curves, with the parameters chosen as (1, 6, 14), (7, 13, 25), and (1, 7 -21)." }


## Composing Epicycles

Let's return to the theme of this chapter, function composition.
Using function composition we can do what we've done before,
and break the epicycle curves into small reusuable parts.

The first component we need is a parametric circle that allows us to change the speed of rotation.
I called this a `wheel`.

```scala
def wheel(speed: Int): Angle => Point =
  ???
```

We can use `scale`, which we've already defined, to change the radius.

The other component we need is a way to position a wheel relative to another wheel. 
(This is the point / vector addition part.)
I called this `on`.

```scala
def on(wheel1: Angle => Point, wheel2: Angle => Point): Angle => Point =
  ???
```


@:exercise(Wheels on Wheels)
Implement `wheel` and `on`, described above. 
@:@

@:solution
`wheel` is a parametric circle that rotates at the given speed.

```scala mdoc:silent
def wheel(speed: Int): Angle => Point =
  angle => Point(1.0, angle * speed)
```

`on` implements the point and vector addition we discussed earlier.

```scala mdoc:silent
def on(wheel1: Angle => Point, wheel2: Angle => Point): Angle => Point =
  angle => wheel1(angle) + wheel2(angle).toVec
```

With these we can easily construct epicycle curves. 
The example we started this section with

```scala
val twoWheels: Angle => Point =
  (angle: Angle) =>
    Point(75, angle * 6) + Point(32, angle * 11).toVec
```

can be written as

```scala mdoc:invisible
def scale(factor: Double): Point => Point =
  (point: Point) => Point(point.r * factor, point.angle)
```
```scala mdoc:silent
val wheel1 = wheel(6).andThen(scale(75))
val wheel2 = wheel(11).andThen(scale(32))

val combined = on(wheel1, wheel2)
```
@:@

@:exercise(Put It Together)
Explore the curves you can create using epicycles.

Below are some examples to get you started.
These examples all use three wheels. 
It can be helpful to place some limits (in my case the number of wheels) to reduce the number of possibilities you consider.

This example shows 7-fold symmetry. The speeds of the wheels (from largest to smallest) are 1, 8, and 22. 
Notice that these values are all equal to \\((7 \times n) + 1\\), for different choices of \\(n\\).
In mathematics, we'd say they are [congruent modulo 7](https://en.wikipedia.org/wiki/Modular_arithmetic).
It is this fact that creates the 7-fold symmetry.

@:figure{ img = "./epicycle-7fold.svg", key = "#fig:cycles:epicycle-7fold", caption = "An epicycle showing 7-fold symmetry, with speeds 1, 8, and 22." }

We don't have to always use positive speeds. 
A negative speed means the wheel rotates in the opposite direction.
In the example below I've used speeds of 1, -8, and 22.

@:figure{ img = "./epicycle-7fold-reverse.svg", key = "#fig:cycles:epicycle-7fold-reverse", caption = "An epicycle showing 7-fold symmetry, with speeds 1, 8, and 22." }

We don't need to use speeds that are congruent modulo \\(n\\). 
This example has speeds of 7, 17, and 29, which are all prime numbers.
The examples below keep the number of samples constant,
but sample 1 turn, 3 turns, and 10 turns respectively from top to bottom.

@:figure{ img = "./epicycle-asymmetrical.svg", key = "#fig:cycles:epicycle-asymmetrical", caption = "An epicycle with prime numbers speeds, sampled over 1, 3, and 10 turns respectively." }

Hopefully these examples have given you some ideas of the different parameters you can explore.
Now go and make some shapes!
@:@
