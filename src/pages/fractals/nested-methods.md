## Nested Methods

```scala mdoc:invisible
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._
```

A method is a declaration.
The body of a method can contain declarations and expressions.
Therefore, a method declaration can contain other method declarations.

To see why this is useful, lets look at a method we wrote earlier:

```scala mdoc:silent
def cross(count: Int): Image = {
  val unit = Image.circle(20)
  count match {
    case 0 => unit
    case n => unit.beside(unit.above(cross(n-1)).above(unit)).beside(unit)
  }
}
```

We have declared `unit` inside the method `cross`.
This means the declaration of `unit` is only in scope within the body of `cross`.
It is good practice to limit the scope of declarations to the minimum needed, to avoid accidentally shadowing other declarations.
However, let's consider the runtime behavior of `cross` and we'll see that is has some undesirable characteristics.

We'll use our substitution model to expand `cross(1)`.

```scala
cross(1)
// Expands to
{
  val unit = Image.circle(20)
  1 match {
    case 0 => unit
    case n => unit.beside(unit.above(cross(n-1)).above(unit)).beside(unit)
  }
}
// Expands to
{
  val unit = Image.circle(20)
  unit.beside(unit.above(cross(0)).above(unit)).beside(unit)
}
// Expands to
{
  val unit = Image.circle(20)
  unit.beside(unit.above
  {
    val unit = Image.circle(20)
    0 match {
      case 0 => unit
      case n => unit.beside(unit.above(cross(n-1)).above(unit)).beside(unit)
    }
  }
  .above(unit)).beside(unit)
}
// Expands to
{
  val unit = Image.circle(20)
  unit.beside(unit.above
  {
    val unit = Image.circle(20)
    unit
  }
  .above(unit)).beside(unit)
}
```

The point of this enormous expansion is to demonstrate that we're recreating `unit` every time we recurse within `cross`.
We can prove this is true by printing something every time `unit` is created.

```scala mdoc:reset:invisible
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._
```
```scala mdoc
def cross(count: Int): Image = {
  val unit = {
    println("Creating unit")
    Image.circle(20)
  }
  count match {
    case 0 => unit
    case n => unit.beside(unit.above(cross(n-1)).above(unit)).beside(unit)
  }
}

cross(1)
```

This doesn't matter greatly for `unit` because it's very small, but we could be doing something that takes up a lot of memory or time, and it's undesirable to repeat it when we don't have to.

We could solve this by shifting `unit` outside of `cross`.

```scala mdoc:reset:invisible
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
```
```scala mdoc
val unit = {
  println("Creating unit")
  Image.circle(20)
}

def cross(count: Int): Image = {
  count match {
    case 0 => unit
    case n => unit beside (unit above cross(n-1) above unit) beside unit
  }
}

cross(1)
```

This is undesirable because `unit` now has a larger scope than needed.
A better solution it to use a nested or internal method.

```scala mdoc:reset:invisible
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._
```
```scala mdoc
def cross(count: Int): Image = {
  val unit = {
    println("Creating unit")
    Image.circle(20)
  }
  def loop(count: Int): Image = {
    count match {
      case 0 => unit
      case n => unit beside (unit above loop(n-1) above unit) beside unit
    }
  }

  loop(count)
}

cross(1)
```

This has the behavior we're after, creating `unit` only once while minimising its scope.
The internal method `loop` is using structural recursion exactly as before.
We just need to ensure that we call it in `cross`.
I usually name this sort of internal method `loop` or `iter` (short for iterate) to indicate that they're performing a loop.

This technique is just a small variation of what we've done already, but let's do a few exercises to make sure we've got the pattern.


### Exercises {-}

#### Chessboard {-}

Rewrite `chessboard` using a nested method so that `blackSquare`, `redSquare`, and `base` are only created once when `chessboard` is called.

```scala mdoc
def chessboard(count: Int): Image = {
  val blackSquare = Image.square(30) fillColor Color.black
  val redSquare   = Image.square(30) fillColor Color.red

  val base =
    (redSquare   beside blackSquare) above (blackSquare beside redSquare)
  count match {
    case 0 => base
    case n =>
      val unit = cross(n-1)
      (unit beside unit) above (unit beside unit)
  }
}
```

<div class="solution">

Here's how we did it. It has exactly the same pattern we used with `boxes`.

```scala mdoc:reset:invisible
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._
```
```scala mdoc
def chessboard(count: Int): Image = {
  val blackSquare = Image.square(30) fillColor Color.black
  val redSquare   = Image.square(30) fillColor Color.red
  val base =
    (redSquare   beside blackSquare) above (blackSquare beside redSquare)
  def loop(count: Int): Image =
    count match {
      case 0 => base
      case n =>
        val unit = loop(n-1)
        (unit beside unit) above (unit beside unit)
    }

  loop(count)
}
```
</div>

#### Boxing Clever {-}

Rewrite `boxes`, shown below, so that `aBox` is only in scope within `boxes` and only created once when `boxes` is called.

```scala mdoc:silent
val aBox = Image.square(20).fillColor(Color.royalBlue)

def boxes(count: Int): Image =
  count match {
    case 0 => Image.empty
    case n => aBox.beside(boxes(n-1))
  }
```

<div class="solution">

We can do this in two stages, first moving `aBox` within `boxes`.

```scala mdoc:reset:invisible
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._
```
```scala mdoc:silent
def boxes(count: Int): Image = {
  val aBox = Image.square(20).fillColor(Color.royalBlue)
  count match {
    case 0 => Image.empty
    case n => aBox beside boxes(n-1)
  }
}
```

Then we can use an internal method to avoid recreating `aBox` on every recursion.

```scala mdoc:reset:invisible
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._
```
```scala mdoc:silent
def boxes(count: Int): Image = {
  val aBox = Image.square(20).fillColor(Color.royalBlue)
  def loop(count: Int): Image =
    count match {
      case 0 => Image.empty
      case n => aBox beside loop(n-1)
    }

  loop(count)
}
```
</div>
