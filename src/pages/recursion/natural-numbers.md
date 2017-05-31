## The Natural Numbers

```tut:invisible
import doodle.core._
import doodle.core.Image._
import doodle.syntax._
import doodle.jvm.Java2DFrame._
import doodle.backend.StandardInterpreter._
val aBox = Image.rectangle(20, 20).fillColor(Color.royalBlue)
```

The natural numbers are the whole numbers, or integers, greater than or equal to zero. In other words the numbers 0, 1, 2, 3, ... (Some people define the natural numbers as starting at 1, not 0. It doesn't greatly matter for our purposes which definition you choose, but here we'll assume they start at 0.)

One interesting property of the natural numbers is that we can define them recursively. That is, we can define them in terms of themselves. This kind of circular definition seems like it would lead to nonsense. We avoid this by including in the definition a *base case* that ends the recursion. Concretely, the definition is:

A natural number `n` is

- 0; or
- 1 + `m`, where `m` is a natural number.

The case for 0 is the base case, whilst the other case is recursive as it defines a natural number `n` in terms of a natural number `m`. Because `m` is always smaller than `n`, and the base case is the smallest possible natural number, this definition defines all of the natural numbers.

Given a natural number, say, 3, we can break it down using the definition above as follows:

3 = 1 + 2 = 1 + (1 + 1) = 1 + (1 + (1 + 0))

We use the recursive rule to expand the equation, until we cannot use it any more. We then use the base case to stop the recursion.


## Structural Recursion

Now onto structural recursion. The structural recursion pattern for the natural numbers gives us two things:

- a reusable code skeleton for processing any natural number; and
- the guarantee that we can use this skeleton to implement *any* computation on natural numbers.

Remember we wrote `boxes` as

```tut:book
def boxes(count: Int): Image =
  count match {
    case 0 => Image.empty
    case n => aBox beside boxes(n-1)
  }
```

When we developed `boxes` we just seemed to stumble upon this pattern. 
Here we see that this pattern follows directly from the definition of the natural numbers.
Remember the recursive definition of the natural numbers: a natural number `n` is

- 0; or
- 1 + `m`, where `m` is a natural number.

The patterns in the `match` expression exactly match this definition. The expression

```scala
count match {
  case 0 => ???
  case n => ???
}
```

means we're checking `count` for two cases, the case when `count` is 0, and the case when `count` is any other natural number `n` (which is `1 + m`).

The right hand side of the `match` expression says what we do in each case. The case for zero is `Image.empty`. The case for `n` is `aBox beside boxes(n-1)`.

Now for the really important point. 
Notice that the structure of the right-hand side mirrors the structure of the natural number we match. 
When we match the base case 0, our result is the base case `Image.empty`. When we match the recursive case `n` the structure of the right hand side matches the structure of the recursive case in the definition of natural numbers. 
The definition states that `n` is `1 + m`. 
On the right-hand side we replace 1 with `aBox`, we replace + with `beside`, and we recursively call `boxes` with `m` (which is `n-1`) where the definition recurses.

```tut:book
def boxes(count: Int): Image =
  count match {
    case 0 => Image.empty
    case n => aBox beside boxes(n-1)
  }
```

To reiterate, the left hand side of the `match` expression exactly matches the definition of natural numbers. The right-hand also matches the definition but we replace natural numbers with images. The image that is equivalent to zero is `Image.empty`. The image that is equivalent to `1 + m` is `aBox beside boxes(m)`.

This general pattern holds for anything we care to write that transforms the natural numbers into some other type.
We always have a `match` expression.
We always have the two patterns, corresponding to the base and recursive cases.
The right hand side expressions always consist of the base case, and the recursive case which itself hasa result specific substitute for `1` and `+`, and a recursive call for `n-1`.

<div class="callout callout-info">
#### Structural Recursion over Natural Numbers Pattern {-}

The general pattern for structural recursion over the natural numbers is

```scala
def name(count: Int): Result =
  count match {
    case 0 => resultBase
    case n => resultUnit add name(n-1)
  }
```

where `Result`, `resultBase`, `resultUnit`, and `add` are specific to the problem we're solving. 
So to implement a structural recursion over the natural numbers we must

 - recognise the method we're writing has a natural number as it's input;
 - work out the result type; and
 - decide what should be the base, unit, and addition for the result.
</div>

We're now ready to go explore the fun that can be had with this simple but powerful tool.

<div class="callout callout-info">
### Proofs and Programs

If you've studied maths you have probably come across proof by induction.
The general pattern of a proof by induction looks very much like the general pattern of a structural recursion over the natural numbers.
This is no coincidence; there is a deep relationship between the two.
We can view a structural recursion over the natural numbers as exactly a proof by induction.
When we claim the ability to write any transformation on the natural numbers in terms of the structural recursion skeleton, this claim is backed up by the mathematical foundation we're implicitly using.
We can also prove properties of our code by using the connection between the two: any structural recursion is implicitly defining a proof of some property.

This general connection between proofs and programs is known as the *Howard-Curry Isomorphism*.
</div>

### Exercises {-}

#### Cross {-}

Our first exercise is to create a method `cross` that will generate cross images. [@fig:recursion:cross] shows four cross images, which correspond to calling the method `cross` with `0` to `3`.

The method skeleton is

```tut:book
def cross(count: Int): Image =
  ???
```

![Crosses generated by `count` from 0 to 3.](./src/pages/recursion/cross.pdf+svg){#fig:recursion:cross}

What pattern will we use to fill in the body of `cross`? Write out the pattern.

<div class="solution">
It's structural recursion over the natural numbers. You should end up with something like

```scala
def cross(count: Int): Image =
  count match {
    case 0 => <resultBase>
    case n => <resultUnit> <add> cross(n-1)
  }
```
</div>

Now we've identified the pattern we're using, we need to fill in the problem specific parts:

 - the base case; and
 - the unit and addition operators.

Hint: use [@fig:recursion:cross] to identify the elements above.

<div class="solution">
From the picture we can work out that the base case is a circle.

Successive elements in the picture add circles to the top, bottom, left, and right of the image. So our unit is the same as the base, a circle, but the addition operator is not a simple `beside` or `above` like we've seen before but `unit beside (unit above cross(n-1) above unit) beside unit`.
</div>

Now finish the implementation of `cross`.

<div class="solution">
Here's what we wrote.

```scala
def cross(count: Int): Image = {
  val unit = Image.circle(20)
  count match {
    case 0 => unit
    case n => unit beside (unit above cross(n-1) above unit) beside unit
  }
}
```
</div>


#### Chessboard {-}

We saw in the cross exercise that the hard part was identifying the recursive structure in what we were trying to create. Once we'd done that filling in the structural recursion pattern was straightforward.

In this exercise and the next we're trying to sharpen you eye for recursive structure. 
Your mission in this exercise to identify the recursive structure in a chessboard, and implement a method to draw chessboards.
The method skeleton isn't

```tut:silent:book
def chessboard(count: Int): Image =
  ???
```

In [@fig:recursion:chessboards] we have example chessboards drawn with `count` ranging from `0` to `2`.
Hint: note that now `count` does not give us the width of the chessboard, but tells us the number of atomic "chessboard units"  we combine.

![Chessboards generated by `count` from 0 to 2.](./src/pages/recursion/chessboards.pdf+svg){#fig:recursion:chessboards}

Implement `chessboard`.

<div class="solution">
`chessboard` is a structural recursion over the natural numbers, so right away we can write down the skeleton for this pattern.

```scala
def chessboard(count: Int): Image =
  count match {
    case 0 => resultBase
    case n => resultUnit add cross(n-1)
  }
```

As before we must decide on the base, unit, and addition for the result.
We've given you a hint by showing the progression of chessboards in [@fig:recursion:chessboards].
From this we can see that the base is a two-by-two chessboard.

```tut:silent:book
val blackSquare = Image.rectangle(30, 30) fillColor Color.black
val redSquare   = Image.rectangle(30, 30) fillColor Color.red

val base =
  (redSquare beside blackSquare) above (blackSquare beside redSquare)
```

Now to work out the unit and addition. 
Here we see a change from previous examples.
The unit is the value we get from the recursive call `chessboard(n-1)`.
The addition operation is `(unit beside unit) above (unit beside unit)`.

Putting it all together we get

```tut:silent:book
def chessboard(count: Int): Image = {
  val blackSquare = Image.rectangle(30, 30) fillColor Color.black
  val redSquare   = Image.rectangle(30, 30) fillColor Color.red
  
  val base =
    (redSquare   beside blackSquare) above (blackSquare beside redSquare)
  count match {
    case 0 => base
    case n =>
      val unit = chessboard(n-1)
      (unit beside unit) above (unit beside unit)
  }
}
```

If you have prior programming experience you might have immediately thought of creating a chessboard via two nested loops.
Here we're taking a different approach by defining a larger chessboard as a composition of smaller chessboards.
Grasping this different approach to decomposing problems is a key step in becoming proficient in functional programming.
</div>


#### Sierpinkski Triangle {-}

The Sierpinski triangle, show in [@fig:recursion:sierpinski], is a famous fractal. (Technically, [@fig:recursion:sierpinski] shows a Sier*pink*ski triangle.)

![The Sierpinski triangle.](./src/pages/recursion/sierpinski.pdf+svg){#fig:recursion:sierpinski}

Although it looks complicated we can break the structure down into a form that we can generate with structural recursion over the natural numbers.
Implement a method with skeleton

```tut:book
def sierpinski(count: Int): Image =
  ???
```

No hints this time. 
We've already seen everything we need to know.

<div class="solution">
The key step is to recognise that the basic unit of the Sierpinski triangle is `triangle above (triangle beside triangle)`.
Once we've worked this out, the code has exactly the same structure as `chessboard`.
Here's our implementation.

```tut:book

def sierpinski(count: Int): Image = {
  val triangle = Image.triangle(10, 10) lineColor Color.magenta
  count match {
    case 0 => triangle above (triangle beside triangle)
    case n =>
      val unit = sierpinski(n-1)
      unit above (unit beside unit)
  }
}
```

</div>
