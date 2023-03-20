# Polar Coordinates

```scala mdoc:invisible
import doodle.core._
import doodle.syntax.all._
```

A point in two dimensions is most commonly specified using x and y coordinates. These are known as *Cartesian coordinates* after their inventor, René Descartes. We can visualize a Cartesian coordinate as projecting a vertical line from the x-axis and a horizontal line from the y-axis. The intersection of these two lines is the chosen point. The animation below shows this.

@:doodle("cartesian", "Coordinates.cartesian")

An alternate representation is *polar coordinates*. In polar coordinates, a point is specified by a length and a rotation. The animation below illustrates this.

@:doodle("polar", "Coordinates.polar")

In Doodle we can construct points using either Cartesian or polar coordinates. To create a Cartesian point we pass the x and y values to the `Point` constructor.

```scala mdoc:silent
val cartesian = Point(3, 4)
```

To create a polar point we need a length and an angle. In Doodle angles are their own type, called `Angle`. We usually construct angles in terms of degrees. Here's an example.

```scala mdoc:silent
val ninety = 90.degrees
```

As well as degrees we can also use radians or turns. 2π radians make a full circle. A turn is the proportion of a full circle: 1.0 is a full circle (360 degrees), 0.5 is half a circle, and so on. Here are some examples.

```scala mdoc:silent
val radians = 2.radians
val fullTurn = 1.turns
```

Now we know how to create an `Angle` we can create a `Point` in polar coordinates.

```scala mdoc:silent
val polar = Point(5, 45.degrees)
```


## From Points to Polygons

@:doodle("points", "Polygons.points")
