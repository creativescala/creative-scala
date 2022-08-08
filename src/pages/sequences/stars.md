## My God, It's Full of Stars!

```scala mdoc:invisible
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._
```

Let's use our new tools to draw some stars.
For the purpose of this exercise let's assume that a star is a polygon with `p` points.
However, instead of connecting each point to its neighbours,
we'll connect them to the `nth` point around the circumference.

For example, [@fig:sequences:stars] shows stars with `p=11` and `n=1 to 5`.
`n=1` produces a regular polygon while
values of `n` from `2` upwards produce stars with increasingly sharp points:

![Stars with `p=11` and `n=1 to 5`](./src/pages/sequences/stars.pdf+svg){#fig:sequences:stars}

Write code to draw the diagram above.
Start by writing a method to draw a `star` given `p` and `n`:

```scala mdoc:silent
def star(p: Int, n: Int, radius: Double): Image =
  ???
```

*Hint:* use the same technique we used for `polygon` previously.

<div class="solution">
Here's the `star` method. We've renamed `p` and `n` to `points` and `skip` for clarity:

```scala mdoc:reset:invisible
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._
```
```scala mdoc:silent
def star(sides: Int, skip: Int, radius: Double): Image = {
  import Point._
  import PathElement._

  val rotation = 360.degrees * skip / sides

  val start = moveTo(polar(radius, 0.degrees))
  val elements = (1 until sides).toList map { index =>
    val point = polar(radius, rotation * index)
    lineTo(point)
  }

  Image.closedPath(start :: elements) strokeWidth 2
}
```
</div>

Using structural recursion and `beside` write a method `allBeside` with the signature

```scala mdoc
def allBeside(images: List[Image]): Image =
  ???
```

We'll use `allBeside` to create the row of stars.
To create the picture we only need to use values of `skip`
from `1` to `sides/2` rounded down. For example:

```scala mdoc:reset:invisible
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._
def star(sides: Int, skip: Int, radius: Double): Image = {
  import Point._
  import PathElement._

  val rotation = 360.degrees * skip / sides

  val start = moveTo(polar(radius, 0.degrees))
  val elements = (1 until sides).toList map { index =>
    val point = polar(radius, rotation * index)
    lineTo(point)
  }

  Image.closedPath(start :: elements) strokeWidth 2
}
```
```scala mdoc:invisible
def allBeside(imgs: List[Image]): Image =
  imgs match {
    case Nil => Image.empty
    case hd :: tl => hd beside allBeside(tl)
  }
```

```scala mdoc:silent
allBeside(
  (1 to 5).toList map { skip =>
    star(11, skip, 100)
  }
)
```

<div class="solution">
We can use the structural recursion skeleton to write this method.

We start with

```scala mdoc:reset:invisible
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._
```
```scala mdoc:silent
def allBeside(images: List[Image]): Image =
  images match {
    case Nil => ???
    case hd :: tl => ???
  }
```

Remembering the recursion gives us 

```scala mdoc:reset:invisible
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._
```
```scala mdoc:silent
def allBeside(images: List[Image]): Image =
  images match {
    case Nil => ???
    case hd :: tl => /* something here */ allBeside(tl)
  }
```

Finally we can fill in the base and recursive cases.

```scala mdoc:reset:invisible
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._
def star(sides: Int, skip: Int, radius: Double): Image = {
  import Point._
  import PathElement._

  val rotation = 360.degrees * skip / sides

  val start = moveTo(polar(radius, 0.degrees))
  val elements = (1 until sides).toList map { index =>
    val point = polar(radius, rotation * index)
    lineTo(point)
  }

  Image.closedPath(start :: elements) strokeWidth 2
}
```
```scala mdoc:silent
def allBeside(images: List[Image]): Image =
  images match {
    case Nil => Image.empty
    case hd :: tl => hd.beside(allBeside(tl))
  }
```
</div>

When you've finished your row of stars,
try constructing a larger image from different values of `p` and `n`.
There is an example in [@fig:sequences:all-star]. *Hint:* You will need to create a method `allAbove` similar to `allBeside`.

![Stars with `p=3 to 33 by 2` and `n=1 to p/2`](src/pages/sequences/all-star.pdf+svg){#fig:sequences:all-star}

<div class="solution">
To create the image in [@fig:sequences:stars2] we started by creating a method to style a star.

```scala mdoc:silent
def style(img: Image, hue: Angle): Image = {
  img.
    strokeColor(Color.hsl(hue, 1.0, 0.25)).
    fillColor(Color.hsl(hue, 1.0, 0.75))
}
```

We then created `allAbove`, which you will notice is very similar to `allBeside` (wouldn't it be nice if we could abstract this pattern?)

```scala mdoc:silent
def allAbove(imgs: List[Image]): Image =
  imgs match {
    case Nil => Image.empty
    case hd :: tl => hd above allAbove(tl)
  }
```

The updated scene then becomes:

```scala mdoc:silent
allAbove((3 to 33 by 2).toList map { sides =>
  allBeside((1 to sides/2).toList map { skip =>
    style(star(sides, skip, 20), 360.degrees * skip / sides)
  })
})
```
</div>
