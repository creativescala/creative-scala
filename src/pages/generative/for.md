## For Comprehensions

```scala mdoc:invisible
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._
```

<div class="callout callout-info">
In addition to the standard imports given at the start of the chapter, in this section we're assuming the following:

```scala mdoc:silent
import doodle.random._
```
</div>

Scala provides some special syntax, called a *for comprehension*, that makes it simpler to write long sequences of `flatMap` and `map`.

For example, the code for `randomConcentricCircles` has a call to `flatMap` and `map`.

```scala mdoc:invisible
def randomAngle: Random[Angle] =
  Random.double.map(x => x.turns)

def randomColor(s: Double, l: Double): Random[Color] =
  randomAngle map (hue => Color.hsl(hue, s, l))

val randomPastel = randomColor(0.7, 0.7)

def randomCircle(r: Double, color: Random[Color]): Random[Image] =
  color.map(fill => Image.circle(r).fillColor(fill))
```

```scala mdoc:silent
def randomConcentricCircles(count: Int, size: Int): Random[Image] =
  count match {
    case 0 => Random.always(Image.empty)
    case n => 
      randomCircle(size, randomPastel).flatMap{ circle =>
        randomConcentricCircles(n-1, size + 5).map{ circles =>
          circle.on(circles)
        }
      }
  }
```

This can be replaced with a for comprehension.

```scala mdoc:reset:invisible
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._
import doodle.random._
def randomAngle: Random[Angle] =
  Random.double.map(x => x.turns)
def randomColor(s: Double, l: Double): Random[Color] =
  randomAngle.map(hue => Color.hsl(hue, s, l))
val randomPastel = randomColor(0.7, 0.7)
def randomCircle(r: Double, color: Random[Color]): Random[Image] =
  color map (fill => Image.circle(r) fillColor fill)
```
```scala mdoc:silent
def randomConcentricCircles(count: Int, size: Int): Random[Image] =
  count match {
    case 0 => Random.always(Image.empty)
    case n => 
      for {
        circle  <- randomCircle(size, randomPastel) 
        circles <- randomConcentricCircles(n-1, size + 5)
      } yield circle.on(circles) 
  }
```

The for comprehension is often easier to read than direct use of `flatMap` and `map`.

A general for comprehension

```scala mdoc:invisible
val a: Seq[Int] = Seq.empty
val b: Seq[Int] = Seq.empty
val c: Seq[Int] = Seq.empty
val e: Int = 0
```

```scala mdoc:silent
for {
  x <- a
  y <- b
  z <- c
} yield e
```

translates to:

```scala mdoc:silent
a.flatMap(x => b.flatMap(y => c.map(z => e)))
```

Which is to say that every `<-`, except the last, turns into a `flatMap`, and the last `<-` becomes a `map`.

For comprehensions are translated by the compiler into uses of `flatMap` and `map`.
There is no magic going on. 
It is just a different way of writing code that would use `flatMap` and `map` that avoids excessive nesting.

Note that the for comprehension syntax is more flexible than what we have presented here.
For example, you can drop the `yield` keyword from a for comprehension and the code will still compile.
It just won't return a result.
We're not going to use any of these extensions in Creative Scala, however.
