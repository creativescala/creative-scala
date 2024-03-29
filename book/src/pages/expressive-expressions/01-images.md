# Images

```scala mdoc:invisible
import cats.effect.unsafe.implicits.global
import doodle.core.*
import doodle.image.*
import doodle.syntax.all.*
import doodle.image.syntax.all.*
import doodle.java2d.*
```

Let's start with some simple shapes, programming in the worksheet as we've done before.

```scala mdoc
Image.circle(100)
```

What is happening here? `Image` is an object and `circle` a method on that object. We pass to `circle` a parameter, `100` that gives the diameter of the circle we're constructing. Note the type of the result is `Image`.

We draw the circle by calling the `draw` method.

```scala
Image.circle(100).draw()
```

A window should appear showing the picture below.

@:figure{ img = "circle.svg", key = "#fig:expanding-expressions:circle", caption = "A circle" }

Doodle supports a handful of "primitive" images: circles, rectangles, and triangles. Let's try drawing a rectangle.

```scala
Image.rectangle(100, 50).draw()
```

The output is shown below.

@:figure{ img = "rectangle.svg", key = "#fig:expanding-expressions:rectangle", caption = "A rectangle" }

Finally let's try a triangle, which we specify in terms of a width and a height. You should see the below image.


```scala
Image.triangle(120, 80).draw()
```

@:figure{ img = "triangle.svg", key = "#fig:expanding-expressions:triangle", caption = "A triangle" }


## Exercises

@:exercise(I Go Round in Circles)

Create circles that are 1, 10, and 100 units wide. Now draw them!
@:@

@:solution
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
@:@


@:exercise(My Type of Art)

What is the type of a circle? A rectangle? A triangle?
@:@

@:solution
They all have type `Image`, as we can tell from the worksheet.

```scala
Image.circle(10) // doodle.core.Image
Image.rectangle(10, 10) // doodle.core.Image
Image.triangle(10, 10) // doodle.core.Image
```
@:@


@:exercise(Not My Type of Art)

What's the type of *drawing* an image? What does this mean?
@:@

@:solution
In this case the worksheet doesn't print any type.

```scala
Image.circle(10).draw()
```

However, we can mouse over the expression, or use the trick of giving the expression an incorrect type, to find that the type is `Unit`. `Unit` is the type of expressions that have no interesting value to return. This is the case for `draw`; we call it because we want something to appear on the screen, not because we have a use for the value it returns. There is only one value with type `Unit`. This value is also called unit, which written as a literal expression is `()`
@:@
