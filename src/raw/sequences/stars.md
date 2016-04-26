## My God, It's Full of Stars!

```tut:invisible
import doodle.core._
import doodle.core.Image._
import doodle.syntax._
import doodle.jvm.Java2DCanvas._
import doodle.backend.StandardInterpreter._
```

Let's use our new tool to draw some stars.
For the purpose of this exercise let's assume that a star is a polygon with `p` points.
However, instead of connecting each point to its neighbours,
we'll connect them to the `nth` point around the circumference.

For example, [@fig:sequences:stars] shows stars with `p=11` and `n=1 to 5`.
`n=1` produces a regular polygon while
values of `n` from `2` upwards produce stars with increasingly sharp points:

![Stars with `p=11` and `n=1 to 5`](./src/pages/sequences/stars.png){#fig:sequences:stars}

Write code to draw the diagram above.
Start by writing a method to draw a `star` given `p` and `n`:

```tut:book
def star(p: Int, n: Int, radius: Double): Image =
  ???
```

*Hint:* use the same technique we used for `polygon` previously.

<div class="solution">
Here's the `star` method. We've renamed `p` and `n` to `points` and `skip` for clarity:

```tut:book
def star(sides: Int, skip: Int, radius: Double): Image = {
  val rotation = 360.degrees * skip / sides

  val start = MoveTo(polar(radius, 0.degrees))
  val elements = (1 until sides).toList map { index =>
    val point = polar(radius, rotation * index)
    LineTo(point)
  }

  closedPath(start :: elements) lineWidth 2
}
```
</div>

Using structural recursion and `beside` write a method `allBeside` with the signature

```tut:book
def allBeside(images: List[Image]): Image =
  ???
```

We'll use `allBeside` to create the row of stars.
We only need to use values of `skip`
from `1` to `sides/2` rounded down. For example:

```tut:book
allBeside(
  (1 to 5).toList map { skip =>
    star(11, skip, 100)
  }
)
```
</div>

When you've finished your row of stars,
try constructing a larger image from different values of `p` and `n`.
There is an example in [@fig:sequences:stars2]. *Hint:* You will need to create a method `allAbove` similar to `allBeside`.

![Stars with `p=3 to 33 by 2` and `n=1 to p/2`](src/pages/sequences/stars2.png){#fig:sequences:stars2}

<div class="solution">
To create the image in [@fig:sequences:stars2] we started by creating a method to style  a star.

```tut:book
def style(img: Image, hue: Angle): Image =
  img.
    lineColor(Color.hsl(hue, 1.normalized, .25.normalized)).
    fillColor(Color.hsl(hue, 1.normalized, .75.normalized))
```

We then created `allAbove`, which you will notice is very similar to `allBeside` (wouldn't it be nice if we could abstract this pattern?)

```tut:book
def allAbove(imgs: List[Image]): Image =
  imgs match {
    case Nil => Image.empty
    case hd :: tl => hd above allAbove(tl)
  }
```

The updated scene then becomes:

```tut:book
allAbove((3 to 33 by 2).toList map { sides =>
  allBeside((1 to sides/2).toList map { skip =>
    style(star(sides, skip, 20), 360.degrees * skip / sides)
  })
})
```
</div>
