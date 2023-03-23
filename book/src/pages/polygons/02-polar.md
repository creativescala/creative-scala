# Polar Coordinates

```scala mdoc:invisible
import doodle.core._
import doodle.syntax.all._
import doodle.image._
```

In this section we'll learn about polar coordinates: what they are and how to create them in Doodle. We're interested in polar coordinates because they allow us to easily create regular polygons. We'll look at this in the final part of this section.


## Points in Two Dimensions

A point in two dimensions is most commonly specified using x and y coordinates. These are known as *Cartesian coordinates* after their inventor, René Descartes. We can visualize a Cartesian coordinate as projecting a vertical line from the x-axis and a horizontal line from the y-axis. The intersection of these two lines is the chosen point. The animation below shows this.

@:doodle("cartesian", "Coordinates.cartesian")

An alternate representation is *polar coordinates*. In polar coordinates, a point is specified by a distance from the origin and an angle. The animation below illustrates this.

@:doodle("polar", "Coordinates.polar")

In Doodle we can construct points using either Cartesian or polar coordinates. To create a Cartesian point we pass the x and y values to the `Point` constructor.

```scala mdoc:silent
val cartesian = Point(3, 4)
```

To create a polar point we need a length and an angle. The length is just a number, but in Doodle angles are their own type called `Angle`. We usually construct angles in terms of degrees. Here's an example.

```scala mdoc:silent
val ninety = 90.degrees
```

As well as degrees we can also use radians or turns. 2π radians make a full circle. A turn is a proportion of a full circle: 1.0 is a full circle (360 degrees), 0.5 is half a circle, and so on. Here are some examples.

```scala mdoc:silent
val radians = 2.radians
val fullTurn = 1.turns
```

Now we know how to create an `Angle` we can create a `Point` using polar coordinates.

```scala mdoc:silent
val polar = Point(5, 45.degrees)
```


## From Points to Polygons

Drawing polygons is our ultimate goal, and polar coordinates allow us to easily specify the corners (vertices) of regular polygons. Look at the hexagon below. To specify the vertices in Cartesian coordinates we'd have to do some involved geometry. With polar coordinates, however, it's very simple. Each vertex is the same distance from the center but differs in angle, as the animation shows. In the case of the hexagon, each vertex is a 60 degree rotation from the one before. (This is because a full turn of 360 degrees must visit all vertices, and there are 6 evenly spaced vertices, so each vertex is a turn of 360/60 = 60 degrees.)

@:doodle("points", "Polygons.points")

We can use this idea to draw circles, or another `Image`, at the vertices of a regular polygon. Here's an example demonstrating what I mean.

```scala mdoc:silent
val dot = Image.circle(5).fillColor(Color.darkViolet)
val vertices =
  dot.at(Point(100, 0.degrees))
    .on(dot.at(Point(100, 60.degrees)))
    .on(dot.at(Point(100, 120.degrees)))
    .on(dot.at(Point(100, 180.degrees)))
    .on(dot.at(Point(100, 240.degrees)))
    .on(dot.at(Point(100, 300.degrees)))
```

Drawing this gives the output shown below.

@:doodle("vertices", "Polygons.vertices")


@:exercise("Get To The Point")

Implement a method `polygonPoints` that produces an `Image` with circles (or something else of your choice) at the vertices of a regular polygon as shown above. The method should accept two parameters:

- the number of sides of the polygon; and
- the radius (distance of the vertices from the center)

Below shows the output of 

```scala
polygonPoints(3, 50)
  .fillColor(Color.crimson)
  .beside(polygonPoints(5, 50).fillColor(Color.lawngreen))
  .beside(polygonPoints(7, 50).fillColor(Color.dodgerBlue))
```

(I've used color to make it clearer which points belong to which polygon.)

@:doodle("polygon-points", "Polygons.polygonPointsExercise")

@:@
@:solution
This is a structural recursion over the natural numbers, but we need a helper method to actually do the counting. Here's my implementation. I used turns to specify the angle `turn`, because I felt was the most natural way to express it.

```scala mdoc:silent
def polygonPoints(sides: Int, radius: Double): Image = {
  val turn = (1.0 / sides).turns
  def loop(count: Int): Image =
    count match {
      case 0 => Image.empty
      case n =>
        Image
          .circle(5)
          .at(Point(radius, turn * n))
          .on(loop(n - 1))
    }

  loop(sides)
}
```
@:@
