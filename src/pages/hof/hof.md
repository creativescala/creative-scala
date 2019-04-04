## Higher Order Methods and Functions

```scala mdoc:invisible
import doodle.core._
import doodle.core.Image._
import doodle.syntax._
```

Why are functions useful?
We can already use methods to package up and name reusable fragments of code.
What other advantages do we get from treating code as values? 
We've said we can

 - pass functions as parameters to other functions and methods; and
 - create methods that return functions as their results
 
but we haven't used this ability yet. Let's do that now.

Let's consider the pattern from the concentric circles exercise as an example:

```scala mdoc:silent
def concentricCircles(count: Int, size: Int): Image =
  count match {
    case 0 => Image.empty
    case n => Image.circle(size) on concentricCircles(n-1, size + 5)
  }
```

This pattern allows us to create many different images
by changing the use of `Image.circle` to another shape.
However, each time we provide a new replacement for `Image.circle`,
we also need a new definition of `concentricCircles` to go with it.

We can make `concentricCircles` completely general by supplying
the replacement for `Image.circle` as a parameter.
Here we've renamed the method to `concentricShapes`, as we're no longer restricted to drawing circles,
and made `singleShape` responsible for drawing an appropriately sized shape.

```scala mdoc:reset:invisible
import doodle.core._
import doodle.core.Image._
import doodle.syntax._
```
```scala mdoc:silent
def concentricShapes(count: Int, singleShape: Int => Image): Image =
  count match {
    case 0 => Image.empty
    case n => singleShape(n) on concentricShapes(n-1, singleShape)
  }
```

Now we can re-use the same definition of `concentricShapes`
to produce plain circles, squares of different hue,
circles with different opacity, and so on.
All we have to do is pass in a suitable definition of `singleShape`:

```scala mdoc:reset:invisible
import doodle.core._
import doodle.core.Image._
import doodle.syntax._
def concentricShapes(count: Int, singleShape: Int => Image): Image =
  count match {
    case 0 => Image.empty
    case n => singleShape(n) on concentricShapes(n-1, singleShape)
  }
```
```scala mdoc:silent
// Passing a function literal directly:
val blackCircles: Image =
  concentricShapes(10, (n: Int) => Image.circle(50 + 5*n))

// Converting a method to a function:
def redCircle(n: Int): Image =
  Image.circle(50 + 5*n) lineColor Color.red

val redCircles: Image =
  concentricShapes(10, redCircle _)
```

### Exercises {-}

#### The Colour and the Shape {-}

Starting with the code below, write color and shape functions
to produce the image shown in [@fig:hof:colors-and-shapes.png].

![Colors and Shapes](src/pages/hof/colors-and-shapes.pdf+svg){#fig:hof:colors-and-shapes.png}

```scala mdoc:reset:invisible
import doodle.core._
import doodle.core.Image._
import doodle.syntax._
```
```scala mdoc:silent
def concentricShapes(count: Int, singleShape: Int => Image): Image =
  count match {
    case 0 => Image.empty
    case n => singleShape(n) on concentricShapes(n-1, singleShape)
  }
```

The `concentricShapes` method is equivalent to the
`concentricCircles` method from previous exercises.
The main difference is that we pass in
the definition of `singleShape` as a parameter.

Let's think about the problem a little.
We need to do two things:

 1. write an appropriate definition of `singleShape` for each
    of the three shapes in the target image; and

 2. call `concentricShapes` three times,
    passing in the appropriate definition of `singleShape` each time
    and putting the results `beside` one another.

Let's look at the definition of the `singleShape` parameter in more detail.
The type of the parameter is `Int => Image`,
which means a function that accepts an `Int` parameter and returns an `Image`.
We can declare a method of this type as follows:

```scala mdoc:silent
def outlinedCircle(n: Int) =
  Image.circle(n * 10)
```

We can convert this method to a function, and pass it to `concentricShapes` to create
an image of concentric black outlined circles:

```scala mdoc:silent
concentricShapes(10, outlinedCircle _)
```

This produces the output shown in [@fig:hof:colors-and-shapes-step1].

![Many outlined circles](src/pages/hof/colors-and-shapes-step1.pdf+svg){#fig:hof:colors-and-shapes-step1}

The rest of the exercise is just a matter of copying, renaming,
and customising this function to produce
the desired combinations of colours and shapes:

```scala mdoc:silent
def circleOrSquare(n: Int) =
  if(n % 2 == 0) Image.rectangle(n*20, n*20) else Image.circle(n*10)

(concentricShapes(10, outlinedCircle) beside concentricShapes(10, circleOrSquare))
```

See [@fig:hof:colors-and-shapes-step2] for the output.

![Many outlined circles beside many circles and squares](src/pages/hof/colors-and-shapes-step2.pdf+svg){#fig:hof:colors-and-shapes-step2}

For extra credit, when you've written your code to
create the sample shapes above, refactor it so you have two sets
of base functions---one to produce colours and one to produce shapes.
Combine these functions using a *combinator* as follows,
and use the result of the combinator as an argument to `concentricShapes`

```scala mdoc:silent
def colored(shape: Int => Image, color: Int => Color): Int => Image =
  (n: Int) => ???
```

<div class="solution">
The simplest solution is to define three `singleShapes` as follows:

```scala mdoc:reset:invisible
import doodle.core._
import doodle.core.Image._
import doodle.syntax._
```
```scala mdoc:silent
def concentricShapes(count: Int, singleShape: Int => Image): Image =
  count match {
    case 0 => Image.empty
    case n => singleShape(n) on concentricShapes(n-1, singleShape)
  }

def rainbowCircle(n: Int) = {
  val color = Color.blue desaturate 0.5.normalized spin (n * 30).degrees
  val shape = Image.circle(50 + n*12)
  shape lineWidth 10 lineColor color
}

def fadingTriangle(n: Int) = {
  val color = Color.blue fadeOut (1 - n / 20.0).normalized
  val shape = Image.triangle(100 + n*24, 100 + n*24)
  shape lineWidth 10 lineColor color
}

def rainbowSquare(n: Int) = {
  val color = Color.blue desaturate 0.5.normalized spin (n * 30).degrees
  val shape = Image.rectangle(100 + n*24, 100 + n*24)
  shape lineWidth 10 lineColor color
}

val answer =
  (concentricShapes(10, rainbowCircle) beside
   concentricShapes(10, fadingTriangle) beside
   concentricShapes(10, rainbowSquare))
```

However, there is some redundancy here:
`rainbowCircle` and `rainbowTriangle`, in particular,
use the same definition of `color`.
There are also repeated calls to `lineWidth(10)` and
`lineColor(color)` that can be eliminated.
The extra credit solution factors these out into their own functions
and combines them with the `colored` combinator:

```scala mdoc:reset:invisible
import doodle.core._
import doodle.core.Image._
import doodle.syntax._
```
```scala mdoc
def concentricShapes(count: Int, singleShape: Int => Image): Image =
  count match {
    case 0 => Image.empty
    case n => singleShape(n) on concentricShapes(n-1, singleShape)
  }

def colored(shape: Int => Image, color: Int => Color): Int => Image =
  (n: Int) =>
    shape(n) lineWidth 10 lineColor color(n)

def fading(n: Int): Color =
  Color.blue fadeOut (1 - n / 20.0).normalized

def spinning(n: Int): Color =
  Color.blue desaturate 0.5.normalized spin (n * 30).degrees

def size(n: Int): Double =
  50 + 12 * n

def circle(n: Int): Image =
  Circle(size(n))

def square(n: Int): Image =
  Image.rectangle(2*size(n), 2*size(n))

def triangle(n: Int): Image =
  Image.triangle(2*size(n), 2*size(n))

val answer =
  (concentricShapes(10, colored(circle, spinning)) beside
   concentricShapes(10, colored(triangle, fading)) beside
   concentricShapes(10, colored(square, spinning)))
```
</div>
