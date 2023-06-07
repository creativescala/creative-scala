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

The example below sampling between 3 and 15 points from a parametric circle, and interpolating a curve from these points.

@:doodle("circle-interpolation", "CyclesInterpolation.circleInterpolation")

We can see that the curve more closely resembles a circle as we increase the number of points it's based off.

We use the `Image.interpolatingSpline` method to interpolate a curve. 
The method expects a `List[Point]`, which is a type in two parts:

1. `List`, which is what's known as a container or collection, meaning it holds other values; and
2. `Point`, which is the type of the values inside the list.

Notice that all the elements of a list must have the same type.

Right now we only need to know how to create lists.
In later chapters we'll learn a lot more about how to work with lists and other collections.

The simplest way to construct a list is by calling the `List` constructor with the values we want in the list.
The example below creates a `List[Int]`, with the elements `1`, `2`, and `3`.

```scala mdoc
List(1, 2, 3)
```

The order of the elements in the list is important, so the following two lists are different.

```scala mdoc:silent
val oneToThree = List(1, 2, 3)
val threeToOne = List(3, 2, 1)
```
```scala mdoc
oneToThree == threeToOne
```

More often we won't know the elements of the list in advance,
and we'll want to compute them as part of our program.
This is exactly what we'll be doing here,
as the elements of the list will be the points from our parametric curve.

