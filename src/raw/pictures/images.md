## Images

<div class="callout callout-info">
If you run the examples from the SBT console within Doodle they will just work. If not, you will need to start your code with the following imports to make Doodle available.

```tut:book
import doodle.core._
import doodle.core.Image._
import doodle.syntax._
import doodle.jvm.Java2DCanvas._
import doodle.backend.StandardInterpreter._
```
</div>

Let's start with some simple shapes, programming at the console as we've done before.

```tut:book
Image.circle(10)
```

What is happening here? `Image` is an object and `circle` a method on that object. We pass to `circle` a parameter, `10` that gives the radius of the circle we're constructing. Note the type of the result---an `Image`.

(We can also write just `circle(10)`, as if you run the console within Doodle it automatically makes this and other methods to construct images available.)

We draw the circle by calling the `draw` method.

```scala
circle(10).draw
```

A window should appear containing the following:

![A circle](src/pages/expressions/circle.png)

Doodle supports a handful of "primitive" images:
circles, rectangles, and triangles.
Let's try drawing a rectangle:

``` scala
rectangle(50, 100).draw
```

![A rectangle](src/pages/expressions/rectangle.png)

Finally let's try a triangle:

~~~ scala
triangle(60, 40).draw
~~~

![A triangle](src/pages/expressions/triangle.png)

### Exercises

#### I Go Round in Circles

Create circles that are 1, 10, and 100 units wide. Now draw them!

<div class="solution">
In this exercise we're checking that our Doodle install is working correctly and we're getting used to using the library.

We can create circles with the code below.

```tut:book
circle(1)
circle(10)
circle(100)
```

We can draw the circles by calling the `draw` method on each circle.

```scala
circle(1).draw
circle(10).draw
circle(100).draw
```
</div>


#### My Type of Art

What is the type of a circle? A rectangle? A triangle?

<div class="solution">
They all have type `Image`, as we can tell from the console.

```scala
:type circle(10)
// doodle.core.Image
:type rectangle(10, 10)
// doodle.core.Image
:type triangle(10, 10)
// doodle.core.Image
```
</div>

#### Not My Type of Art

What's the type of *drawing* an image? What does this mean?

<div class="solution">
Once again, we can ask the console this quesstion.

```scala
:type circle(10).draw
// Unit
```

We see that the type of drawing an image is `Unit`. `Unit` is the type of expressions that have no interesting value to return. This is the case for `draw`; we call it because we want something to appear on the screen, not because we have a use for the value it returns. There is only one value with type `Unit`. This value is also called unit, which written as a literal expression is `()`

You'll note that the console doesn't print unit by default.

```scala
()
```

We can ask the console for the type to show that there really is unit here.

```scala
:type ()
// Unit
```
</div>
