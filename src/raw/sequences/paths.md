## Paths

```tut:invisible
import doodle.core._
import doodle.core.Image._
import doodle.syntax._
import doodle.jvm.Java2DCanvas._
import doodle.backend.StandardInterpreter._
```

All shapes in Doodle are ultimately represented as paths. You can think of a path as giving a sequence of movements for an imaginary pen, starting from the origin. Pen movements come in three varieties:

- moving the pen to the point without drawing a line;

- drawing a straight line from the current position to a point; and

- drawing a [Bezier curve][bezier-curve] from the current position to a point, with the shape of the curve determined by two *control points*.

Paths themselves come in two varieties:

- open paths, where the end of the path is not necessarily the starting point; and
- closed paths, that end where they begin---and if the path doesn't end where it started a line will be inserted to make it so.

The picture in [@fig:sequences:open-closed-paths] illustrates the components that can make up a path, and shows the difference between open and closed paths.

![The same paths draw as open (top) and closed (bottom) paths. Notice how the open triangle is not properly joined at the bottom left, and the closed curve inserts a straight line to close the shape.](./src/pages/sequences/open-closed-paths.png){#fig:sequences:open-closed-paths}

[bezier-curve]: https://en.wikipedia.org/wiki/Bézier_curve

### Creating Paths

Now we know about paths, how do we create them in Doodle? Here's the code that created [@fig:pictures:open-closed-paths].

```tut:book
import doodle.core.Point._

val triangle =
  List(
    LineTo(cartesian(50, 100)),
    LineTo(cartesian(100, 0)),
    LineTo(cartesian(0, 0))
  )

val curve =
  List(BezierCurveTo(cartesian(50, 100), cartesian(100, 100), cartesian(150, 0)))

def style(image: Image): Image =
  image.
    lineWidth(6.0).
    lineColor(Color.royalBlue).
    fillColor(Color.skyBlue)

val openPaths =
  style(openPath(triangle) beside openPath(curve))

val closedPaths =
  style(closedPath(triangle) beside closedPath(curve))

val paths = openPaths above closedPaths
```

From this code we can see we create paths using the `openPath` and `closePath` methods on `Image`, just we create other shapes. A paths is created from a `List` of `PathElement`. The different kinds of `PathElement` are described in [@tbl:sequences:path-element].


---------------------------------------------------------------------------------------------
Constructor                          Description                 Example
------------------------------------ --------------------------- ----------------------------
`MoveTo(Point)`                      Move the pen to `Point`     `MoveTo(cartesian(1, 1))`
                                     without drawing.

`LineTo(Point)`                      Draw a straight line to     `LineTo(cartesian(2, 2))`
                                     `Point`

`BezierCurveTo(Point, Point, Point)` Draw a curve. The first two `BezierCurveTo(cartesian(1,0), cartesian(0,1), cartesian(1,1))`
                                     points specify control
                                     points and the last point is
                                     where the curve ends.

---------------------------------------------------------------------------------------------

: How to create the three different types of `PathElement`. {#tbl:sequences:path-element}

Constructing a `List` is straight-forward: we just call `List` with the elements we want the list to contain. Here are some examples.

```tut:book
// List of Int
List(1, 2, 3)

// List of Image
List(circle(10), circle(20), circle(30))

// List of Color
List(Color.paleGoldenrod, Color.paleGreen, Color.paleTurquoise)
```

#### Exercises {-}

##### Polygons {-}

Using as few lines as possible, create paths to define a triangle, square, and pentagon. Your image might look like [@fig:sequences:polygons]. *Hint:* you might find it easier to use polar coordinates to define the polygons.

![A triangle, square, and pentagon, defined using paths.](./src/pages/sequences/polygons.png){#fig:sequences:polygons}

<div class="solution">
Using polar coordinates makes it much simpler to define the location of the "corners" (vertices) of the polygons. The interior angle of the vertex for an equilateral triangle is 60 degrees, for a square it is 90 degrees, and for a polygon it is 72 degrees. If we assume each polygon is centered at the origin, each vertex is a (180 - interior angle) degrees rotation from the previous one.

For example, if we're defining a triangle and we decide to draw the first vertex at 0 degrees, the second vertex is at 0 + (180 - 60) = 120 degrees, and the final vertex is at (120 + 120) = 240 degrees. We don't have to draw a line between the final vertex and the start---by using a closed path this will be done for us.

Here's our code to draw [@fig:sequences:polygons], which uses this idea.

```tut:book
import doodle.core.Point._
import doodle.core.Color._

val triangle =
  closedPath(List(
               MoveTo(polar(50, 0.degrees)),
               LineTo(polar(50, 120.degrees)),
               LineTo(polar(50, 240.degrees))
             ))

val square =
  closedPath(List(
               MoveTo(polar(50, 45.degrees)),
               LineTo(polar(50, 135.degrees)),
               LineTo(polar(50, 225.degrees)),
               LineTo(polar(50, 315.degrees))
             ))

val pentagon =
  closedPath((List(
                MoveTo(polar(50, 72.degrees)),
                LineTo(polar(50, 144.degrees)),
                LineTo(polar(50, 216.degrees)),
                LineTo(polar(50, 288.degrees)),
                LineTo(polar(50, 360.degrees))
              )))

val spacer =
  rectangle(10, 100).noLine.noFill

def style(image: Image): Image =
  image.lineWidth(6.0).lineColor(paleTurquoise).fillColor(turquoise)

val image = 
  style(triangle) beside spacer beside style(square) beside spacer beside style(pentagon)
```
</div>

##### Curves {-}

Repeat the exercise above, but this time use curves instead of straight lines to create some interesting shapes. Our curvy polygons are shown in [@fig:sequences:curved-polygons]. *Hint:* you'll have an easier time if you abstract into a method your code for creating a curve.

![A curvy triangle, square, and polygon, defined using paths.](./src/pages/sequences/curved-polygons.png){#fig:sequences:curved-polygons}

<div class="solution">
The core of the exercise is to replace the `LineTo` expressions with `BezierCurveTo`. We can abstract curve creation into a method that takes the starting angle and the angle increment, and constructs control points at predetermined points along the rotation. This is what we did in the method `curve` below, and it gives us consistent looking curves without having to manually repeat the calculations each time. Making this abstraction also makes it easier to play around with different control points to create different shapes.

```tut:book
import doodle.core.Point._
import doodle.core.Color._

def curve(radius: Int, start: Angle, increment: Angle): PathElement = {
  BezierCurveTo(
    polar(radius *  .8, start + (increment * .3)),
    polar(radius * 1.2, start + (increment * .6)),
    polar(radius, start + increment)
  )
}

val triangle =
  closedPath(List(
               MoveTo(polar(50, 0.degrees)),
               curve(50, 0.degrees, 120.degrees),
               curve(50, 120.degrees, 120.degrees),
               curve(50, 240.degrees, 120.degrees)
             ))

val square =
  closedPath(List(
               MoveTo(polar(50, 45.degrees)),
               curve(50, 45.degrees, 90.degrees),
               curve(50, 135.degrees, 90.degrees),
               curve(50, 225.degrees, 90.degrees),
               curve(50, 315.degrees, 90.degrees)
             ))

val pentagon =
  closedPath((List(
                MoveTo(polar(50, 72.degrees)),
                curve(50, 72.degrees, 72.degrees),
                curve(50, 144.degrees, 72.degrees),
                curve(50, 216.degrees, 72.degrees),
                curve(50, 288.degrees, 72.degrees),
                curve(50, 360.degrees, 72.degrees)
              )))

val spacer =
  rectangle(10, 100).noLine.noFill

def style(image: Image): Image =
  image.lineWidth(6.0).lineColor(paleTurquoise).fillColor(turquoise)

val image = style(triangle) beside spacer beside style(square) beside spacer beside style(pentagon)
```
</div>
