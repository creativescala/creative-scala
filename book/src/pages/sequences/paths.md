## Paths

```scala mdoc:invisible
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._
```

All shapes in Doodle are ultimately represented as paths. 
You can think of a path as giving a sequence of movements for an imaginary pen, starting from the local origin. 
Pen movements come in three varieties:

- moving the pen to a point without drawing a line;

- drawing a straight line from the current position to a point; and

- drawing a [Bezier curve][bezier-curve] from the current position to a point, with the shape of the curve determined by two *control points*.

Paths themselves come in two varieties:

- open paths, where the end of the path is not necessarily the starting point; and
- closed paths, that end where they begin---and if the path doesn't end where it started a line will be inserted to make it so.

The picture in @:fref(sequences:open-closed-paths) illustrates the components that can make up a path, and shows the difference between open and closed paths.

@:figure{ img = "./src/pages/sequences/open-closed-paths.pdf+svg", key = "#fig:sequences:open-closed-paths", caption = "The same paths draw as open (top) and closed (bottom) paths. Notice how the open triangle is not properly joined at the bottom left, and the closed curve inserts a straight line to close the shape." }

[bezier-curve]: https://en.wikipedia.org/wiki/Bézier_curve


### Creating Paths

Now we know about paths, how do we create them in Doodle? Here's the code that created @:fref(pictures:open-closed-paths).

```scala
import doodle.core.PathElement._

val triangle =
  List(
    lineTo(Point(50, 100)),
    lineTo(Point(100, 0)),
    lineTo(Point(0, 0))
  )

val curve =
  List(curveTo(Point(50, 100), Point(100, 100), Point(150, 0)))

def style(image: Image): Image =
  image.strokeWidth(6.0)
    .strokeColor(Color.royalBlue)
    .fillColor(Color.skyBlue)

val openPaths =
  style(Image.openPath(triangle).beside(Image.openPath(curve)))

val paths =
  style(Image.path(triangle).beside(Image.closedPath(curve)))

val paths = openPaths.above(paths)
```

From this code we can see we create paths using the `openPath` and `closePath` methods on `Image`, just as we create other shapes. 
A path is created from a `List` of `PathElement`. 
The different kinds of `PathElement` are created by calling methods on the `PathElement` object, as described in @:tref(sequences:path-element).
In the code above we used the declaration `import doodle.core.PathElement._` to make all the methods on `PathElement` available in the local scope.


---------------------------------------------------------------------------------------------------
Method                                     Description                 Example
------------------------------------------ --------------------------- ----------------------------
`PathElement.moveTo(Point)`                Move the pen to `Point`     `PathElement.moveTo(Point(1, 1))`
                                           without drawing.

`PathElement.lineTo(Point)`                Draw a straight line to     `PathElement.lineTo(Point(2, 2))`
                                     `     Point`

`PathElement.curveTo(Point, Point, Point)` Draw a curve. The first two `PathElement.curveTo(Point(1,0), Point(0,1), Point(1,1))`
                                           points specify control
                                           points and the last point is
                                           where the curve ends.

---------------------------------------------------------------------------------------------

: How to create the three different types of `PathElement`. @:tref(sequences:path-element)

Constructing a `List` is straight-forward: we just call `List` with the elements we want the list to contain. Here are some examples.

```scala mdoc
// List of Int
List(1, 2, 3)

// List of Image
List(Image.circle(10), Image.circle(20), Image.circle(30))

// List of Color
List(Color.paleGoldenrod, Color.paleGreen, Color.paleTurquoise)
```

Notice the type of a `List` includes the type of the elements, written in square brackets. So the type of a list of integers is written `List[Int]` and a list of `PathElement` is written `List[PathElement]`.

### Exercises {-}

#### Polygons {-}

Create paths to define a triangle, square, and pentagon. Your image might look like @:fref(sequences:polygons). 
*Hint:* you might find it easier to use polar coordinates to define the polygons.

@:figure{ img = "./src/pages/sequences/polygons.pdf+svg", key = "#fig:sequences:polygons", caption = "A triangle, square, and pentagon, defined using paths." }

<div class="solution">
Using polar coordinates makes it much simpler to define the location of the "corners" (vertices) of the polygons. 
Each vertex is located a fixed rotation from the previous vertex, and after we've marked all vertices we must have done a full rotation of the circle. 
This means, for example, that for a pentagon each vertex is (360 / 5) = 72 degrees from the previous one. 
If we start at 0 degrees, vertices are located at 0, 72, 144, 216, and 288 degrees. 
The distance from the origin is fixed in each case. 
We don't have to draw a line between the final vertex and the start---by using a closed path this will be done for us.

Here's our code to draw @:fref(sequences:polygons), which uses this idea. 
In some cases we haven't started the vertices at 0 degrees so we can rotate the shape we draw.

```scala mdoc:reset:invisible
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._
```
```scala 
import doodle.core.PathElement._
import doodle.core.Point._
import doodle.core.Color._
val triangle =
  Image.path(List(
                     moveTo(polar(50, 0.degrees)),
                     lineTo(polar(50, 120.degrees)),
                     lineTo(polar(50, 240.degrees))
                   ))

val square =
  Image.path(List(
                     moveTo(polar(50, 45.degrees)),
                     lineTo(polar(50, 135.degrees)),
                     lineTo(polar(50, 225.degrees)),
                     lineTo(polar(50, 315.degrees))
                   ))

val pentagon =
  Image.path(List(
                     moveTo(polar(50, 72.degrees)),
                     lineTo(polar(50, 144.degrees)),
                     lineTo(polar(50, 216.degrees)),
                     lineTo(polar(50, 288.degrees)),
                     lineTo(polar(50, 360.degrees))
                   ))

val spacer =
  Image.rectangle(10, 100).noStroke.noFill

def style(image: Image): Image =
  image.strokeWidth(6.0).strokeColor(paleTurquoise).fillColor(turquoise)

val image = 
  style(triangle).beside(spacer).beside(style(square)).beside(spacer).beside(style(pentagon))
```
</div>

#### Curves {-}

Repeat the exercise above, but this time use curves instead of straight lines to create some interesting shapes. 
Our curvy polygons are shown in @:fref(sequences:curved-polygons). 
*Hint:* you'll have an easier time if you generalise into a method your code for creating a curve.

@:figure{ img = "./src/pages/sequences/curved-polygons.pdf+svg", key = "#fig:sequences:curved-polygons", caption = "A curvy triangle, square, and polygon, defined using paths." }

<div class="solution">
The core of the exercise is to replace the `lineTo` expressions with `curveTo`. 
We can generalise curve creation into a method that takes the starting angle and the angle increment, and constructs control points at predetermined points along the rotation. 
This is what we did in the method `curve` below, and it gives us consistent looking curves without having to manually repeat the calculations each time. 
Making this generalisation also makes it easier to play around with different control points to create different outcomes.

```scala mdoc:reset:invisible
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._
```
```scala 
import doodle.core.Point._
import doodle.core.PathElement._
import doodle.core.Color._

def curve(radius: Int, start: Angle, increment: Angle): PathElement = {
  curveTo(
    polar(radius *  .8, start + (increment * .3)),
    polar(radius * 1.2, start + (increment * .6)),
    polar(radius, start + increment)
  )
}

val triangle =
  Image.path(List(
                     moveTo(polar(50, 0.degrees)),
                     curve(50, 0.degrees, 120.degrees),
                     curve(50, 120.degrees, 120.degrees),
                     curve(50, 240.degrees, 120.degrees)
                   ))

val square =
  Image.path(List(
                     moveTo(polar(50, 45.degrees)),
                     curve(50, 45.degrees, 90.degrees),
                     curve(50, 135.degrees, 90.degrees),
                     curve(50, 225.degrees, 90.degrees),
                     curve(50, 315.degrees, 90.degrees)
                   ))

val pentagon =
  Image.path((List(
                      moveTo(polar(50, 72.degrees)),
                      curve(50, 72.degrees, 72.degrees),
                      curve(50, 144.degrees, 72.degrees),
                      curve(50, 216.degrees, 72.degrees),
                      curve(50, 288.degrees, 72.degrees),
                      curve(50, 360.degrees, 72.degrees)
                    )))

val spacer =
  Image.rectangle(10, 100).noStroke.noFill

def style(image: Image): Image =
  image.strokeWidth(6.0).strokeColor(paleTurquoise).fillColor(turquoise)

val image = style(triangle).beside(spacer).beside(style(square)).beside(spacer).beside(style(pentagon))
```
</div>
