## Images

```scala mdoc:invisible
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._
```

Let's start with some simple shapes, programming at the console as we've done before.

```scala mdoc
Image.circle(10)
```

What is happening here? `Image` is an object and `circle` a method on that object. We pass to `circle` a parameter, `10` that gives the diameter of the circle we're constructing. Note the type of the result---an `Image`.

```scala mdoc
Image.circle(10)
```

We draw the circle by calling the `draw` method.

```scala
Image.circle(10).draw()
```

A window should appear as shown in @:fref(pictures:circle).

@:figure{ img = "src/pages/pictures/circle.pdf+svg", key = "#fig:pictures:circle", caption = "A circle" }

Doodle supports a handful of "primitive" images: circles, rectangles, and triangles. Let's try drawing a rectangle.

```scala
Image.rectangle(100, 50).draw()
```

The output is shown in @:fref(pictures:rectangle).

@:figure{ img = "src/pages/pictures/rectangle.pdf+svg", key = "#fig:pictures:rectangle", caption = "A rectangle" }

Finally let's try a triangle, for which the output is shown in @:fref(pictures:triangle).


```scala
Image.triangle(60, 40).draw()
```

@:figure{ img = "src/pages/pictures/triangle.pdf+svg", key = "#fig:pictures:triangle", caption = "A triangle" }

### Exercises {-}

#### I Go Round in Circles {-}

Create circles that are 1, 10, and 100 units wide. Now draw them!

<div class="solution">
In this exercise we're checking that our Doodle install is working correctly and we're getting used to using the library. One of the important points in Doodle is we separate *defining the image* from *drawing the image*. We'll talk more about this throughout the book.

We can create circles with the code below.

```scala mdoc:silent
Image.circle(1)
Image.circle(10)
Image.circle(100)
```

We can draw the circles by calling the `draw` method on each circle.

```scala
Image.circle(1).draw()
Image.circle(10).draw()
Image.circle(100).draw()
```
</div>


#### My Type of Art {-}

What is the type of a circle? A rectangle? A triangle?

<div class="solution">
They all have type `Image`, as we can tell from the console.

```scala
:type Image.circle(10)
// doodle.core.Image
:type Image.rectangle(10, 10)
// doodle.core.Image
:type Image.triangle(10, 10)
// doodle.core.Image
```
</div>

#### Not My Type of Art {-}

What's the type of *drawing* an image? What does this mean?

<div class="solution">
Once again, we can ask the console this quesstion.

```scala
:type Image.circle(10).draw()
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
