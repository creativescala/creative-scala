# Fun with Composition

```scala mdoc:invisible
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._
```

We can do a lot with composition. Let's see another example that shows a bit more of its power. Remember the concentric circles exercise we used as an example:

```scala mdoc:silent
def concentricCircles(count: Int, size: Int): Image =
  count match {
    case 0 => Image.empty
    case n => Image.circle(size).on(concentricCircles(n-1, size + 10))
  }
```

This pattern allows us to create many different images
by changing the use of `Image.circle` to another shape.
However, each time we provide a new replacement for `Image.circle`,
we also need a new definition of `concentricCircles` to go with it.

We can make `concentricCircles` completely general by supplying
the replacement for `Image.circle` as a parameter.
Here we've renamed the method to `concentricShapes`, as we're no longer restricted to drawing circles,
and made the `singleShape` parameter responsible for drawing an appropriately sized shape.

```scala mdoc:reset:invisible
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._
```
```scala mdoc:silent
def concentricShapes(count: Int, singleShape: Int => Image): Image =
  count match {
    case 0 => Image.empty
    case n => singleShape(n).on(concentricShapes(n-1, singleShape))
  }
```

Now we can re-use the same definition of `concentricShapes`
to produce plain circles, squares of different hue,
circles with different opacity, and so on.
All we have to do is pass in a suitable definition of `singleShape`:

```scala mdoc:silent
// Passing a function literal directly:
val blackCircles: Image =
  concentricShapes(10, (n: Int) => Image.circle(100 + 24*n))

// Converting a method to a function:
def redCircle(n: Int): Image =
  Image.circle(100 + 24*n).strokeColor(Color.red)

val redCircles: Image =
  concentricShapes(10, redCircle _)
```

@:figure{ img = "./red-black-circles.svg", key = "#fig:cycles:red-black-circles", caption = "Black and Red Concentric Circles" }

You might notice two things about this example: we're not using function composition, and we duplication in the definitions. 
In both cases we draw circles, but they differ in color.
No problem.
This feels like a problem we can solve with function composition.
Let's give it a go.

In both cases the radius of the circle is `100 + 24*n`, where `n` is the iteration count.
We can extract this into a function.

```scala mdoc:silent
val size: Int => Int = n => 100 + 24*n
```

Now we can build a function that creates a circle of size computed from `n`.

```scala mdoc:silent
val circle: Int => Image = size => Image.circle(size)
```

Now we just need a function that can change the stroke color.

```scala mdoc:silent
def strokeColor(color: Color): Image => Image =
  image => image.strokeColor(color)
```

With this we can construct the functions we need.

```scala mdoc:silent
val blackCircle = circle // Black is the default stroke
val redCircle = size.andThen(circle).andThen(strokeColor(Color.red))
```

Using `andThen` is *a* way to compose functions, but not *the* only way to compose functions.
We can write all sorts of functions that compose functions. 
Sometimes we use the term *combinator* for a method or function whose job is to compose its parameters.
For example, the `on`, `beside`, and `above` combinators compose `Images`.
Here's an example of a function combinator that combines two `Int => Image` functions to produce another `Int => Image` function.

```scala mdoc:silent
def withSizeOn(f: Int => Image, g: Int => Image): Int => Image =
  size => f(size).on(g(size))
```

With this definition we can produce the image below.

@:figure{ img = "./combinator.svg", key = "#fig:cycles:combinator", caption = "Using the `withSizeOn` combinator" }

To create it we define a function to make a square given a size, and then compose it with our existing `circle` function as shown below.

```scala mdoc:silent
def square(n: Int): Image =
  Image.square(n)
  
concentricShapes(
  10,
  size
    .andThen(withSizeOn(circle, square))
    .andThen(strokeColor(Color.royalBlue))
)
```


@:exercise(The Colour and the Shape)

Your mission is to write code to produce the image below.

@:figure{ img = "./colors-and-shapes.svg", key = "#fig:cycles:colors-and-shapes", caption = "Colors and Shapes" }

Let's think about the problem a little.
We need to do two things:

 1. write an appropriate definition of `singleShape` for each
    of the three shapes in the target image; and

 2. call `concentricShapes` three times,
    passing in the appropriate definition of `singleShape` each time
    and putting the results `beside` one another.

We also want to use function composition, 
to create the definitions of `singleShape` from small, reusable components.

The variation in the image comes from changing the shape and the color.
For shapes we have circle, triangle, and square.
The color changes by spinning the hue of the circle and square,
and fading the color for the triangle.
This suggests functions for each shape,
and functions for each color manipulation.

That's probably enough guidance.
It's now over to you to finish up the code.
@:@

@:solution
```scala mdoc:reset:invisible
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._

def concentricShapes(count: Int, singleShape: Int => Image): Image =
  count match {
    case 0 => Image.empty
    case n => singleShape(n).on(concentricShapes(n-1, singleShape))
  }
```

We can start with the basic functions for shapes we've already used, and add a function for triangles.

```scala mdoc:silent
def circle(n: Int): Image =
  Image.circle(n)

def square(n: Int): Image =
  Image.square(n)

def triangle(n: Int): Image =
  Image.triangle(n, n)
```

We also need functions that produce the color as a function of the iteration count.

```scala mdoc:silent
def fading(n: Int): Color =
  Color.blue.fadeOut((1 - n / 20.0).normalized)

def spinning(n: Int): Color =
  Color.blue.desaturate(0.5.normalized).spin((n * 30).degrees)
```

Now we need to put these functions together. 
We have one group of functions of type `Int => Image` and another with type `Int => Color`.
We cannot compose these with `andThen` as the types don't work out.
However we can create our own combinator that does what we want.

```scala mdoc:silent
def colored(shape: Int => Image, color: Int => Color): Int => Image =
  (n: Int) => shape(n).strokeWidth(10).strokeColor(color(n))
```

Putting it all together gets us the output we want.

```scala mdoc:silent
concentricShapes(10, colored(size.andThen(circle), spinning))
  .beside(
    concentricShapes(10, colored(size.andThen(triangle), fading))
      .beside(concentricShapes(10, colored(size.andThen(square), spinning)))
  )
```
@:@


@:exercise(More Shapes)
The `concentricShapes` methods takes an `Int => Image` function. We can make a function with this type using `drawCurve`, the parametric curves we created earlier, and the various utilities we have created along the way. There is an example below.

@:figure{ img = "./concentric-dotty-circle.svg", key = "#fig:cycles:concentric-dotty-circle", caption = "Concentric dotty circles" }

The code to create this is below.

```scala mdoc:reset:invisible
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._

def concentricShapes(count: Int, singleShape: Int => Image): Image =
  count match {
    case 0 => Image.empty
    case n => singleShape(n) on concentricShapes(n-1, singleShape)
  }

def colored(shape: Int => Image, color: Int => Color): Int => Image =
  (n: Int) =>
    shape(n).strokeWidth(10).strokeColor(color(n))

def fading(n: Int): Color =
  Color.blue.fadeOut((1 - n / 20.0).normalized)

def spinning(n: Int): Color =
  Color.blue.desaturate(0.5.normalized).spin((n * 30).degrees)

val parametricCircle: Angle => Point = 
  (angle: Angle) => Point(1.0, angle)

def scale(factor: Double): Point => Point =
  (point: Point) => Point(point.r * factor, point.angle)

val growingDot: Point => Image = 
  (pt: Point) => Image.circle(pt.angle.toTurns * 20).at(pt)

def drawCurve(points: Int, curve: Angle => Image): Image = {
  val step = Angle.one / points
  def loop(count: Int): Image = {
    val angle = step * count
    count match {
      case 0 => Image.empty
      case n =>
        curve(angle).on(loop(n - 1))
    }
  }
  
  loop(points)
}
```
```scala mdoc:silent
def dottyCircle(n: Int): Image =
  drawCurve(
    72,
    parametricCircle.andThen(scale(100 + n * 24)).andThen(growingDot)
  )

concentricShapes(10, colored(dottyCircle, spinning))
```

Use the techniques we've seen so far to create a picture of your choosing (perhaps using the flowers we have seen earlier). No solution here; there is no right or wrong answer.
@:@
