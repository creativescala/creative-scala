## Layout

```scala mdoc:invisible
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._
```

We can seen how to create primitive images. We can combine together images using layouts methods to create more complex images. Try the following code---you should see a circle and a rectangle displayed beside one another, as in @:fref(picture:circle-rect).

```scala
(Image.circle(10).beside(Image.rectangle(10, 20))).draw()
```

@:figure{ img = "src/pages/pictures/circle-beside-rectangle.pdf+svg", key = "#fig:picture:circle-rect", caption = "A circle beside a rectangle" }

`Image` contains several layout methods for combining images, described in @:tref(pictures:layout). Try them out now to see what they do.

+---------------+-----------+----------------------------+------------------------------+
| Method        | Parameter | Description                | Example                      |
+===============+===========+============================+==============================+
| `beside`      |`Image`    | Places images horizontally | `Image.circle(10)            |
|               |           | next to one another        |    .beside(Image.circle(10))`|
+---------------+-----------+----------------------------+------------------------------+
| `above`       |`Image`    | Places images vertically   | `Image.circle(10)            |
|               |           | next to one another        |    .above(Image.circle(10))` |
+---------------+-----------+----------------------------+------------------------------+
| `below`       |`Image`    | Places images vertically   | `Image.circle(10)            |
|               |           | next to one another        |    .below(Image.circle(10))` |
+---------------+-----------+----------------------------+------------------------------+
| `on`          |`Image`    | Places images centered     | `Image.circle(10)            |
|               |           | on top of one another      |    .on(Image.circle(10))`    |
+---------------+-----------+----------------------------+------------------------------+
| `under`       |`Image`    | Places images centered     | `Image.circle(10)            |
|               |           | on top of one another      |    .under(Image.circle(10))` |
+---------------+-----------+----------------------------+------------------------------+

: Layout methods available in Doodle @:tref(pictures:layout)

### Exercises {-}

#### The Width of a Circle {-}

Create the picture @:fref(picture:width-of-a-circle) using the layout methods and basic images we've covered so far.

@:figure{ img = "src/pages/pictures/width-of-a-circle.pdf+svg", key = "#fig:picture:width-of-a-circle", caption = "The width of a circle" }

<div class="solution">
It's three small circles on top of a bigger circle, and we can just about state this as is in code.

```scala mdoc
(Image
   .circle(20)
   .beside(Image.circle(20))
   .beside(Image.circle(20))).on(Image.circle(60))
```
</div>
