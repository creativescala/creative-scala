## Higher Order Methods and Functions

```scala mdoc:invisible
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._
```

In previous sections we have seen the utility of passing functions to methods and returning functions from methods. In this section we'll see the usefulness of *function composition**. Composition, in the mathematical rather than artistic sense, means creating something more complex by combining simpler parts. We could say we compose the numbers 1 and 1, using addition, to produce 2. By composing functions we mean to create a function that connects the output of one component function to the input of another component function.

Here's an example. We use the `andThen` method to create a function that connects the output of the first function to the input of the second function.

```scala mdoc:silent
val dropShadow = (image: Image) =>
  image.on(image.strokeColor(Color.black).fillColor(Color.black).at(5, -5))

val mirrored = (image: Image) =>
  image.beside(image.transform(Transform.horizontalReflection))

val composed = mirrored.andThen(dropShadow)
```

In @:fref(hof:composed) we see the output of the program

```scala mdoc:silent
val star = Image
  .star(100, 50, 5)
  .fillColor(Color.fireBrick)
  .strokeColor(Color.dodgerBlue)
  .strokeWidth(7.0)
dropShadow(star)
  .beside(mirrored(star))
  .beside(composed(star))
```

This shows how the composed function applies the output of the first function to the second function: we first mirror the function then add a drop shadow.

@:figure{ img = "src/pages/hof/composed.pdf+svg", key = "#fig:hof:composed", caption = "Illustrating function composition by showing the output of the individual components and the composition." }

Let's see how we can apply function composition to our examples of parametric curves. One limitation of the parametric cures we've created so far is that their size is fixed. For example when we defined `parametricCircle` we fixed the radius at 200.

```scala mdoc:silent
def parametricCircle(angle: Angle): Point =
  Point.polar(200, angle)
```

What if we want to create circles of different radius? We could use a method that returns a function like so.

```scala mdoc:reset:invisible
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._
```
```scala mdoc:silent
def parametricCircle(radius: Double): Angle => Point = 
  (angle: Angle) => Point.polar(radius, angle)
```

This would be a reasonable solution but we're going to explore a different approach using our new tool of function composition. Our approach will be this:

- each parametric curve will be of some default size that we'll loosely define as "usually between 0 and 1"; and

- we'll define a function `scale` that will change the size as appropriate.

A quick example will make this more concrete. Let's redefine `parametricCircle` so the radius is 1.

```scala mdoc:reset:invisible
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._
```
```scala mdoc:silent
val parametricCircle: Angle => Point = 
  (angle: Angle) => Point(1.0, angle)
```

Now we can define `scale`.

```scala mdoc:silent
def scale(factor: Double): Point => Point =
  (point: Point) => Point(point.r * factor, point.angle)
```

We can use function composition to create circles of different sizes as follows.

```scala mdoc:silent
val circle100 = parametricCircle.andThen(scale(100))
val circle200 = parametricCircle.andThen(scale(200))
val circle300 = parametricCircle.andThen(scale(300))
```

We can use the same approach for our spiral, adjusting the function slightly so the radius of the spiral varies from about 0.36 at 0 degrees to 1 at 360 degrees.

```scala mdoc:silent
val parametricSpiral: Angle => Point =
  (angle: Angle) => Point(Math.exp(angle.toTurns - 1), angle)
```

Then we can compose with `scale` to produce spirals of different size.

```scala mdoc:silent
val spiral100 = parametricSpiral.andThen(scale(100))
val spiral200 = parametricSpiral.andThen(scale(200))
val spiral300 = parametricSpiral.andThen(scale(300))
```

What else can we do with function composition? 

Our parametric functions have type `Angle => Point`. We can compose these with functions of type `Point => Image` and with this setup we can make the "dots" from which we build our images depend on the point.

Here's an example when the dots get bigger as the angle increases.

```scala mdoc:silent
val growingDot: Point => Image = 
  (pt: Point) => Image.circle(pt.angle.toTurns * 20).at(pt)
  
val growingCircle = parametricCircle
  .andThen(scale(100))
  .andThen(growingDot)
```

#### Exercise: Sample {-}

If we want to draw this function we'll need to change `sample` so the parametric has type `Angle => Image` instead of `Angle => Point`. In other words we want the following skeleton.

```scala mdoc:silent
def sample(samples: Int, curve: Angle => Image): Image =
  ???
```

Implement `sample`.

<div class="solution">
```scala mdoc:reset:invisible
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._
```

The answer is a small modification of the original `sample`. We drop the `dot` parameter and the type of the `curve` parameter changes. The rest follows from this.

```scala mdoc:silent
def sample(samples: Int, curve: Angle => Image): Image = {
  val step = Angle.one / samples
  def loop(count: Int): Image = {
    val angle = step * count
    count match {
      case 0 => Image.empty
      case n =>
        curve(angle).on(loop(n - 1))
    }
  }
  
  loop(samples)
}
```
</div>


Once we've implemented `sample` we can start drawing pictures. For example, in @:fref(hof:growing-circle) we have the output of `growingCircle` above.

@:figure{ img = "src/pages/hof/growing-circle.pdf+svg", key = "#fig:hof:growing-circle", caption = "A circle created by composing smaller components." }


### More Uses of Composition

At this point we can do a lot. Let's see another example. Remember the concentric circles exercise we used as an example:

```scala mdoc:silent
def concentricCircles(count: Int, size: Int): Image =
  count match {
    case 0 => Image.empty
    case n => Image.circle(size).on(concentricCircles(n-1, size + 5))
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
  concentricShapes(10, (n: Int) => Image.circle(50 + 5*n))

// Converting a method to a function:
def redCircle(n: Int): Image =
  Image.circle(50 + 5*n).strokeColor(Color.red)

val redCircles: Image =
  concentricShapes(10, redCircle _)
```


### Exercises {-}

#### The Colour and the Shape {-}

Starting with the code below we are going to write color and shape functions to produce the image shown in @:fref(hof:colors-and-shapes).

@:figure{ img = "src/pages/hof/colors-and-shapes.pdf+svg", key = "#fig:hof:colors-and-shapes", caption = "Colors and Shapes" }

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
def outlinedCircle(n: Int): Image =
  Image.circle(n * 10)
```

We can convert this method to a function, and pass it to `concentricShapes` to create
an image of concentric black outlined circles:

```scala mdoc:silent
concentricShapes(10, outlinedCircle _)
```

This produces the output shown in @:fref(hof:colors-and-shapes-step1).

@:figure{ img = "src/pages/hof/colors-and-shapes-step1.pdf+svg", key = "#fig:hof:colors-and-shapes-step1", caption = "Many outlined circles" }

The rest of the exercise is just a matter of copying, renaming,
and customising this function to produce
the desired combinations of colours and shapes:

```scala mdoc:silent
def circleOrSquare(n: Int) =
  if(n % 2 == 0) Image.rectangle(n*20, n*20) else Image.circle(n*10)

concentricShapes(10, outlinedCircle).beside(concentricShapes(10, circleOrSquare))
```

See @:fref(hof:colors-and-shapes-step2) for the output.

@:figure{ img = "src/pages/hof/colors-and-shapes-step2.pdf+svg", key = "#fig:hof:colors-and-shapes-step2", caption = "Many outlined circles beside many circles and squares" }

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

def rainbowCircle(n: Int) = {
  val color = Color.blue.desaturate(0.5.normalized).spin((n * 30).degrees)
  val shape = Image.circle(50 + n*12)
  shape.strokeWidth(10).strokeColor(color)
}

def fadingTriangle(n: Int) = {
  val color = Color.blue.fadeOut((1 - n / 20.0).normalized)
  val shape = Image.triangle(100 + n*24, 100 + n*24)
  shape.strokeWidth(10).strokeColor(color)
}

def rainbowSquare(n: Int) = {
  val color = Color.blue.desaturate(0.5.normalized).spin((n * 30).degrees)
  val shape = Image.rectangle(100 + n*24, 100 + n*24)
  shape.strokeWidth(10).strokeColor(color)
}

val answer =
  concentricShapes(10, rainbowCircle)
    .beside(
      concentricShapes(10, fadingTriangle)
        .beside(concentricShapes(10, rainbowSquare))
    )
```

However, there is some redundancy here:
`rainbowCircle` and `rainbowTriangle`, in particular,
use the same definition of `color`.
There are also repeated calls to `strokeWidth(10)` and
`strokeColor(color)` that can be eliminated.
The extra credit solution factors these out into their own functions
and combines them with the `colored` combinator:

```scala mdoc:reset:invisible
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._
```
```scala mdoc
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

def size(n: Int): Double =
  100 + 24 * n

def circle(n: Int): Image =
  Image.circle(size(n))

def square(n: Int): Image =
  Image.square(size(n))

def triangle(n: Int): Image =
  Image.triangle(size(n), size(n))

val answer =
  concentricShapes(10, colored(circle, spinning))
    .beside(
      concentricShapes(10, colored(triangle, fading))
        .beside(concentricShapes(10, colored(square, spinning)))
    )
```
</div>


#### More Shapes {-}

The `concentricShapes` methods takes an `Int => Image` function, and we can construct such as function using `sample`, the parametric curves we created earlier, and the various utilities we have created along the way. There is an example is @:fref(hof:concentric-dotty-circle).

@:figure{ img = "src/pages/hof/concentric-dotty-circle.pdf+svg", key = "#fig:hof:concentric-dotty-circle", caption = "Concentric dotty circles" }

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

def sample(samples: Int, curve: Angle => Image): Image = {
  val step = Angle.one / samples
  def loop(count: Int): Image = {
    val angle = step * count
    count match {
      case 0 => Image.empty
      case n =>
        curve(angle).on(loop(n - 1))
    }
  }
  
  loop(samples)
}
```
```scala mdoc:silent
def dottyCircle(n: Int): Image =
  sample(
    72,
    parametricCircle.andThen(scale(100 + n * 24)).andThen(growingDot)
  )

concentricShapes(10, colored(dottyCircle, spinning))
```

Use the techniques we've seen so far to create a picture of your choosing (perhaps similar to the flower with which we started the chapter). No solution here---there is no right or wrong answer.
