## Layout

```scala mdoc:invisible
import doodle.core._
import doodle.image._
import doodle.image.syntax._
import doodle.image.syntax.core._
import doodle.java2d._
```

We can seen how to create primitive images. We can combine together images using layouts methods to create more complex images. Try the following code---you should see a circle and a rectangle displayed beside one another, as in [@fig:picture:circle-rect].

```scala
(Image.circle(10).beside(Image.rectangle(10, 20))).draw()
```

![A circle beside a rectangle](src/pages/pictures/circle-beside-rectangle.pdf+svg){#fig:picture:circle-rect}

`Image` contains several layout methods for combining images, described in [@tbl:pictures:layout]. Try them out now to see what they do.

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

: Layout methods available in Doodle {#tbl:pictures:layout}

### Exercises {-}

#### The Width of a Circle {-}

Create the picture [@fig:picture:width-of-a-circle] using the layout methods and basic images we've covered so far.

![The width of a circle](src/pages/pictures/width-of-a-circle.pdf+svg){#fig:picture:width-of-a-circle}

<div class="solution">
It's three small circles on top of a bigger circle, and we can just about state this as is in code.

```scala mdoc
(Image
   .circle(20)
   .beside(Image.circle(20))
   .beside(Image.circle(20))).on(Image.circle(60))
```
</div>
