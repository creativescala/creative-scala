## Functions as values

The defining feature of a functional programming programming language
is the ability to define *functions that are first class values*.
Scala has special syntax for functions and function types.
Here's a function that calculates

~~~ scala
scala> (a: Double, b: Double) => math.sqrt(a*a + b*b)
res0: (Double, Double) => Double = <function2>

scala> res0(3, 4)
res1: Double = 5.0
~~~

Because Scala is an object oriented language,
all first class values are objects.
This means functions are objects, not methods!
In fact, functions themselves have useful methods for composition:

~~~ scala
scala> (a: Int) => a + 10
res0: Int => Int = <function1>

scala> (a: Int) => a * 2
res1: Int => Int = <function1>

scala> res0 andThen res1 // this composes the two functions
res2: Int => Int = <function1>

scala> res2(5)
res3: Int = 30
~~~

It may seem surprising and restrictive that Scala methods are not values.
We can prove this by attempting to refer to a method without invoking it:

~~~ scala
scala> Color.rgb
<console>:20: error: missing arguments for method rgb in object Color;
follow this method with `_' if you want to treat it as a partially applied function
              Color.rgb
                    ^
~~~

Fortunately, as the error message above suggests,
we can convert any method to a function using the `_` operator
and call it with the same parameters:

~~~ scala
scala> Color.rgb _
res4: (Int, Int, Int) => doodle.core.Color = <function3>

scala> res4(255, 0, 0)
res5: doodle.core.Color = ...
~~~

## Higher order methods and functions

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

<div class="callout callout-danger">
**TODO: Exercise: Colors and Shapes**
</div>
