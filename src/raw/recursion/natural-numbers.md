## The Natural Numbers

```tut:invisible
import doodle.core._
import doodle.core.Image._
import doodle.syntax._
import doodle.jvm.Java2DCanvas._
import doodle.backend.StandardInterpreter._
val aBox = Image.rectangle(20, 20).fillColor(Color.royalBlue)
```

The natural numbers are the whole numbers, or integers, greater than or equal to zero. In other words the numbers 0, 1, 2, 3, ... (Some people define the natural numbers as starting at 1, not 0. It doesn't greatly matter for our purposes which definition you choose, but here we'll assume they start at 0.)

One interesting property of the natural numbers is that we can define them recursively. That is, we can define them in terms of themselves. This kind of circular definition seems like it would lead to nonsense. We avoid this by including in the definition a *base case* that ends the recursion. Concretely, the definition is:

A natural number `n` is

- 0; or
- 1 + `m`, where `m` is a natural number.

The case for 0 is the base case, whilst the other case is recursive. Because `m` is always smaller than `n`, and the base case is the smallest possible natural number, this definition defines all of the natural numbers.

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

The right hand side of the `match` expression says what we do in each case. The case for zero is `Image.empty`. The case for `n` is to construct a box and recurse.

Now for the really important point. 
Notice that the structure of the right-hand side mirrors the structure of the natural number we match. 
When we match the base case 0, our result is the base case `Image.empty`. When we match the recursive case `n` the structure of the right hand side matches the structure of the recursive case in the definition of natural numbers. 
The definition states that `n` is 1 + `m`. 
On the right-hand side we replace 1 with `aBox`, we replace + with `beside`, and we recursively call `boxes` with `m` (which is `n - 1`) where the definition recurses.

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

The general pattern is

```scala
def <doSomething>(count: Int): <Result> =
  count match {
    case 0 => <resultBase>
    case n => <resultUnit> <add> <doSomething>(n-1)
  }
```

where `<Result>`, `<resultBase>`, `<resultUnit>`, and `<add>` are specific to the problem we're solving.

We're now ready to go explore the fun that can be had with this simple but powerful tool.

### Exercises {-}

#### Gradient Boxes {-}

In this exercise we're going to draw a picture like that in [@fig:recursion:gradient-boxes].
We already know how to draw a line of boxes.
The challenge in this exercise is to make the color change at each step.

Hint: make the color a function of `n`, such as `Color.royalBlue.spin((15 * n).degrees)`

![Five boxes filled with changing colors starting from Royal Blue](./src/pages/recursion/gradient-boxes.pdf+svg){#fig:recursion:gradient-boxes}

<div class="solution">

There are two ways to implement a solution. 
The first method, which we are hinting at, is shown below.

```tut:book
def gradientBoxes(n: Int): Image =
  n match {
    case 0 => Image.empty
    case n => aBox.fillColor(Color.royalBlue.spin(15.degrees)) beside gradientBoxes(n-1)
  }
```

An alternative is to add an extra parameter to `gradientBoxes` and pass the `Color` through the structural recursion.

```tut:book
def gradientBoxes(n: Int, color: Color): Image =
  n match {
    case 0 => Image.empty
    case n => aBox.fillColor(color) beside gradientBoxes(n-1, color.spin(15.degrees))
  }
```
</div>

#### Concentric Circles {-}

Now let's try a variation on the theme, drawing concentric circles as shown in [@fig:recursion:concentric-circles]. Here we are changing the size rather than the color of the image at each step. Otherwise the pattern stays the same. Have a go at implementing it.

![Concentric circles, colored Royal Blue](./src/pages/recursion/concentric-circles.pdf+svg){#fig:recursion:concentric-circles}

<div class="solution">
The trick here is that the size of the circle is a function of `n`. There are many possible choices here. We've used the simple choice of `50 + (5 * n)`.

```tut:book
def concentricCircles(count: Int): Image =
  count match {
    case 0 => Image.empty
    case n => Image.circle(50 + (5 * n)) on concentricCircles(n-1)
  }
```
</div>

#### Once More, With Feeling {-}

Now let's combine both techniques to change size and color on each step, giving results like those shown in [@fig:recursion:colorful-circles]. Experiment until you find something you like.

![Concentric circles with interesting color variations](./src/pages/recursion/colorful-circles.pdf+svg){#fig:recursion:colorful-circles}

<div class="solution">
Here's our solution, where we've tried to break the problem into reusable parts and reduce the amount of repeated code. We still have a lot of repitition as we don't yet have the tools to get rid of more. We'll come to that soon.

```tut:book
def singleCircle(n: Int): Image =
  Image.circle(50 + 7 * n).lineWidth(3.0)

def fade(n: Int): Image =
  singleCircle(n).lineColor(Color.red fadeOut (n / 20.0).normalized)

def gradient(n: Int): Image =
  singleCircle(n).lineColor(Color.royalBlue.spin((n * 15).degrees))

def concentricCircles(n: Int): Image =
  n match {
    case 0 => singleCircle(n)
    case n => singleCircle(n) on concentricCircles(n - 1)
  }

def fadeCircles(n: Int): Image =
  n match {
    case 0 => fade(n)
    case n => fade(n) on fadeCircles(n - 1)
  }

def gradientCircles(n: Int): Image =
  n match {
    case 0 => gradient(n)
    case n => gradient(n) on gradientCircles(n - 1)
  }

def image: Image =
  concentricCircles(20).lineColor(Color.royalBlue)

def fade: Image =
  fadeCircles(20)

def gradient: Image =
  gradientCircles(20)
```
</div>

#### Chessboard

In this exercise and the next we're trying to sharpen you eye for recursive structure. 
Your mission is to identify the 


#### Sierpinkski Triangle

Identify the unit and recursive pattern
