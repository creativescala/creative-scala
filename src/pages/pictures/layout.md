## Layout

```scala mdoc:invisible
import doodle.core._
import doodle.image._
import doodle.image.syntax._
import doodle.image.syntax.core._
import doodle.java2d._
```

We can seen how to create primitive images. We can combine together images using layouts methods to create more complex images. Try the following code---you should see a circle and a rectangle displayed beside one another, as in [@fig:picture:circle-rect].

~~~ scala
(Image.circle(10).beside(rectangle(10, 20))).draw()
~~~

![A circle beside a rectangle](src/pages/pictures/circle-beside-rectangle.pdf+svg){#fig:picture:circle-rect}

Doodle contains several layout methods for combining images, described in [@tbl:pictures:layout]. Try them out now to see what they do.

----------------------------------------------------------------------------------------
Operator              Type    Description                Example
--------------------- ------- -------------------------- -------------------------------
`Image.beside(Image)`  `Image` Places images horizontally `Image.circle(10).beside(circle(20))`
                              next to one another.

`Image.above(Image)`   `Image` Places images vertically   `Image.circle(10).above(circle(20))`
                              next to one another.

`Image.below(Image)`   `Image` Places images vertically   `Image.circle(10).below(circle(20))`
                              next to one another.

`Image.on(Image)`      `Image` Places images centered     `Image.circle(10).on(circle(20))`
                              on top of one another.

`Image.under(Image)`   `Image` Places images centered     `Image.circle(10).under(circle(20))`
                              on top of one another.
----------------------------------------------------------------------------------------

: Layout methods available in Doodle {#tbl:pictures:layout}

### Exercises {-}

#### The Width of a Circle {-}

Create the picture [@fig:picture:width-of-a-circle] using the layout methods and basic images we've covered so far.

![The width of a circle](src/pages/pictures/width-of-a-circle.pdf+svg){#fig:picture:width-of-a-circle}

<div class="solution">
It's three small circles on top of a bigger circle, and we can just about state this as is in code.

```scala mdoc
(Image.circle(20).beside(Image.circle(20)).beside(Image.circle(20)))
  .on(Image.circle(60))
```
</div>
