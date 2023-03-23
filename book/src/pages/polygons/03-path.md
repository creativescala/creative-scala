# Paths

```scala mdoc:invisible
import doodle.core.*
import doodle.syntax.all.*
import doodle.image.*
import doodle.image.syntax.all.*
```

In the previous section we learned how to use polar coordinates to place images at the vertices of a regular polygon. In this section we'll learn how to draw the polygon itself, using paths.

A path specifies how to control a virtual pen to draw a shape. There are three different commands that a path can contain:

- straight lines;
- bezier curves; and
- straight line movement that doesn't draw to the screen.

Here's an example. First we create a path, which you may recognize as defining a hexagon using polar coordinates.

```scala mdoc:silent
val hexagonPath =
  ClosedPath.empty
    .moveTo(100, 0.degrees)
    .lineTo(100, 60.degrees)
    .lineTo(100, 120.degrees)
    .lineTo(100, 180.degrees)
    .lineTo(100, 240.degrees)
    .lineTo(100, 300.degrees)
```

Now we can create an `Image` from it.

```scala mdoc:silent
val hexagonImage = Image.path(hexagonPath)
```

This produces the drawing below.

@:doodle("hexagon", "Paths.hexagon")

For more on paths, see the [Doodle documentation](http://www.creativescala.org/doodle/pictures/path.html).

@:exercise("Paths to Polygons")
Implement a method `regularPolygon` with skeleton

```scala
def regularPolygon(sides: Int, radius: Double): Image =
  ???
```

Given the number of sides and the radius is should produce a regular polygon using a `ClosedPath`.

The drawing below shows the output of 
```scala
regularPolygon(3, 25)
  .on(regularPolygon(5, 50))
  .on(regularPolygon(7, 75))
```

@:doodle("regular-polygon", "Paths.regularPolygonExercise")
@:@
@:solution
The structure of this method is the same as `polygonPoints`: a structural recursion over the natural numbers, with an auxillary helper method. There is a little wrinkle where we convert the `ClosedPath` to an `Image` after calling the helper.

```scala mdoc:silent
def regularPolygon(sides: Int, radius: Double): Image = {
  val turn = (1.0 / sides).turns
  def loop(count: Int): ClosedPath =
    count match {
      case 0 => ClosedPath.empty.moveTo(radius, 0.degrees)
      case n => loop(n - 1).lineTo(radius, turn * n)
    }

  Image.path(loop(sides))
}
```
@:@
