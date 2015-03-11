## Functions as Values

The defining feature of a functional programming programming language
is the ability to define *functions that are first class values*.
Scala has special syntax for functions and function types.
Here's a function that calculates

~~~ scala
(a: Double, b: Double) => math.sqrt(a*a + b*b)
// res0: (Double, Double) => Double = <function2>

res0(3, 4)
// res1: Double = 5.0
~~~

Because Scala is an object oriented language,
all first class values are objects.
This means functions are objects, not methods!
In fact, functions themselves have useful methods for composition:

~~~ scala
(a: Int) => a + 10
// res0: Int => Int = <function1>

(a: Int) => a * 2
// res1: Int => Int = <function1>

res0 andThen res1 // this composes the two functions
// res2: Int => Int = <function1>

res2(5)
// res3: Int = 30
~~~

It may seem surprising and restrictive that Scala methods are not values.
We can prove this by attempting to refer to a method without invoking it:

~~~ scala
Color.rgb
// <console>:20: error: missing arguments for method rgb in object Color;
// follow this method with `_' if you want to treat it as a partially applied function
//               Color.rgb
//                     ^
~~~

Fortunately, as the error message above suggests,
we can convert any method to a function using the `_` operator
and call it with the same parameters:

~~~ scala
Color.rgb _
// res4: (Int, Int, Int) => doodle.core.Color = <function3>

res4(255, 0, 0)
// res5: doodle.core.Color = ...
~~~

## Higher Order Methods and Functions

Why are functions useful?
We can already use methods to package up and name reusable fragments of code.
What other advantages do we get from treating code as values?

 - we can pass functions as parameters to other functions and methods;
 - we can create methods that return functions as their results.

Let's consider the pattern from the concentric circles exercise as an example:

~~~ scala
def manyShapes(n: Int): Image =
  if(n == 1) {
    singleShape
  } else {
    singleShape on manyShapes(n - 1)
  }

def singleShape: Image = ???
~~~

This pattern allows us to create many different images
by changing the definition of `singleShape`.
However, each time we provide a new definition of `singleShape`,
we also need a new definition of `manyShapes` to go with it.

We can make `manyShapes` completely general by supplying
`singleShape` as a parameter:

~~~ scala
def manyShapes(n: Int, singleShape: Int => Image): Image =
  if(n == 1) {
    singleShape(n)
  } else {
    singleShape(n) on manyShapes(n - 1, singleShape)
  }
~~~

Now we can re-use the same definition of `manyShapes`
to produce plain circles, circles of different hue,
circles with different opacity, and so on.
All we have to do is pass in a suitable definition of `singleShape`:

~~~ scala
// Passing a function literal directly:

val blackCircles: Image =
  manyShapes(10, (n: Int) => Circle(50 + 5*n))

// Converting a function to a method:

def redCircle(n: Int): Image =
  Circle(50 + 5*n) lineColor Color.red

val redCircles: Image =
  manyShapes(10, redCircle _)
~~~

<div class="callout callout-info">
*Function Syntax*

We're introducing a lot of syntax here!
There's a dedicated section on function syntax in the
[quick reference](#quick-reference) if you get lost!
</div>

**Exercise: The Colour and the Shape**

Starting with the code below, write color and shape functions
to produce the following image:

![Colours and Shapes](src/pages/fp/colours-and-shapes.png)

~~~ scala
def manyShapes(n: Int, singleShape: Int => Image): Image =
  if(n == 1) {
    singleShape(n)
  } else {
    singleShape(n) on manyShapes(n - 1, singleShape)
  }
~~~

The `manyShapes` method is equivalent to the
`concentricCircles` method from previous exercises.
The main difference is that we pass in
the definition of `singleShape` as a parameter.

Let's think about the problem a little.
We need to do two things:

 1. write an appropriate definition of `singleShape` for each
    of the three shapes in the target image;

 2. call `manyShapes` three times,
    passing in the appropriate definition of `singleShape` each time
    and putting the results `beside` one another.

Let's look at the definition of the `singleShape` parameter in more detail.
The type of the parameter is `Int => Image`,
which means a function that accepts an `Int` parameter and returns an `Image`.
We can declare a method of this type as follows:

~~~ scala
def outlinedCircle(n: Int) =
  Circle(n * 10)
~~~

We can pass a reference to this method to `manyShapes` to create
an image of concentric black outlined circles:

~~~ scala
draw(manyShapes(10, outlinedCircle))
~~~

![Many outlined circles](src/pages/fp/colors-and-shapes-step1.png)

The rest of the exercise is just a matter of copying, renaming,
and customising this function to produce
the desired combinations of colours and shapes:

~~~ scala
def circleOrSquare(n: Int) =
  if(n % 2 == 0) Rectangle(n*20, n*20) else Circle(n*10)

draw(manyShapes(10, outlinedCircle) beside manyShapes(10, circleOrSquare))
~~~

![Many outlined circles beside many circles and squares](src/pages/fp/colors-and-shapes-step2.png)

For extra credit, when you've written your code to
create the sample shapes above, refactor it so you have two sets
of base functions---one to produce colours and one to produce shapes.
Combine these functions using a *combinator* as follows,
and use the result of the combinator as an argument to `manyShapes`

~~~ scala
  def colored(shape: Int => Image, color: Int => Color): Int => Image =
    (n: Int) => ???
~~~

<div class="solution">
The simplest solution is to define three `singleShapes` as follows:

~~~ scala
def manyShapes(n: Int, singleShape: Int => Image): Image =
  if(n == 1) {
    singleShape(n)
  } else {
    singleShape(n) on manyShapes(n - 1, singleShape)
  }

def rainbowCircle(n: Int) = {
  val color = Color.blue desaturate 0.5.normalized spin (n * 30).degrees
  val shape = Circle(50 + n*12)
  shape lineWidth 10 lineColor color
}

def fadingTriangle(n: Int) = {
  val color = Color.blue fadeOut (1 - n / 20.0).normalized
  val shape = Triangle(100 + n*24, 100 + n*24)
  shape lineWidth 10 lineColor color
}

def rainbowSquare(n: Int) = {
  val color = Color.blue desaturate 0.5.normalized spin (n * 30).degrees
  val shape = Rectangle(100 + n*24, 100 + n*24)
  shape lineWidth 10 lineColor color
}

val answer =
  manyShapes(10, rainbowCircle) beside
  manyShapes(10, fadingTriangle) beside
  manyShapes(10, rainbowSquare)
~~~

However, there is some redundancy here:
`rainbowCircle` and `rainbowTriangle`, in particular,
use the same definition of `color`.
There are also repeated calls to `lineWidth(10)` and
`lineColor(color)` that can be eliminated.
The extra credit solution factors these out into their own functions
and combines them with the `colored` combinator:

~~~ scala
def manyShapes(n: Int, singleShape: Int => Image): Image =
  if(n == 1) {
    singleShape(n)
  } else {
    singleShape(n) on manyShapes(n - 1, singleShape)
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
  Rectangle(2*size(n), 2*size(n))

def triangle(n: Int): Image =
  Triangle(2*size(n), 2*size(n))

val answer =
  manyShapes(10, colored(circle, spinning)) beside
  manyShapes(10, colored(triangle, fading)) beside
  manyShapes(10, colored(square, spinning))
~~~
</div>
