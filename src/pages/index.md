<!--
*Functional programming concepts*

 - Composition
 - Evaluation model
 - Names/values
 - Recursion
 - Functions/methods
 - Comprehensions
 - Types
 - Functions (animation: Int => Shape)

Two formats: 2 hours and 8 hours
-->

# Expressions, Values, and Types

Scala programs have three fundamental building blocks:
*expressions*, *values*, and *types*:

An *expression* is a fragment of Scala code that we write in an a text editor.
Valid expressions have a *type* and calculate a *value*. For example,
this expression has the type `String` and calculates the value `HELLO WORLD!`

~~~ scala
scala> "Hello world!".toUpperCase
res0: String = "HELLO WORLD!"
~~~

A Scala program goes through two distinct stages. First it is *compiled*;
if compiles successfully it can then be *executed*.

The most important distinction between types and values is that
types are determined at compile time,
whereas values can only be determined at run time.
Values can change each time we run the code, whereas types are fixed.

For example, the following expression is certainly of type `String`,
but its value depends on the the user input each time it is run:

~~~ scala
scala> readLine.toUpperCase
~~~

<!--
<div class="callout callout-info">
*Understanding the Scala Console*

Let's look at the text in the example above to see what it all means.
The text `scala>` is the prompt.
`"Hello world!".toUpperCase` is the user input---our expression.

When we enter a valid expression, it is compiled and evaluated.
`String` is the type, `"HELLO WORLD!`" is the value, and
`res0` is a variable name that we can use in subsequent expressions:

~~~ scala
scala> res0 + "?"
res1: String = "HELLO WORLD!?"
~~~
</div>
-->

## Literals

The simplest kinds of expressions are *literals*.
These are fragments of code that "stand for themselves".
Scala supports a similar set of literals to Java:

~~~ scala
// Integers:
scala> 1
res0: Int = 1

// Floating point numbers:
scala> 0.1
res1: Double = 0.1

// Booleans:
scala> true
res2: Boolean = true

// Strings:
scala> "Hello world!"
res3: String = Hello world!

// And so on...
~~~

## Method Calls

Scala is a completely object-oriented programming language,
so all values are objects with methods that we can call.
Method calls are another type of expression:

~~~ scala
scala> 123.4.ceil
res4: Double = 124.0

scala> true.toString
res5: String = "true"
~~~

## Operator expressions

Operators in Scala are actually method calls under the hood.
Scala has a set of convenient syntactic shorthands
to allow us to write normal-looking code
without excessive numbers of parentheses.
The most common of these is *infix syntax*,
which allows us to write any expression of the form `a.b(c)`
as `a b c`, without the full stop or parentheses:

~~~ scala
scala> 1 .+(2).+(3).+(4) // the space prevents `1` being treated as a double
res0: Int = 10

scala> 1 + 2 + 3 + 4
res1: Int = 10
~~~

## Conditionals

Many other syntactic constructs are expressions in Scala,
including some that are statements in Java.
Conditionals ("if expressions") are a great example:

~~~ scala
// Conditionals ("if expressions"):
scala> if(123 > 456) "Higher!" else "Lower!"
res6: String = Lower!
~~~

There are other examples of expressions in Scala
including `for` comprehensions, `try`/`catch` expressions, and blocks.
We'll see more of these later.

## Compound Expressions

Many of the expressions we have seen so far are made up of smaller expressions.
For example, the expression `1 + 2` is built from
the sub-expressions `1` and `2`, combined using the method `+`.

The type and value of a compound expression are
determined in part by the types and values of its sub-expressions.
However, sometimes only a subset of the sub-expressions is considered.
For example, the type of an `if` expression is determined
by its positive and negative arms (but not its test expression),
and its value is determined by evaluating one arm but not the other.

## TODO: Statements/Unit/Side-Effects?

## TODO: Images

Numbers and strings are boring. Let's switch to images.

Examples. E.g. Circle(20)

We can combine images with methods like on, beside, and above.

Checkerboard example

~~~ scala
(
  ( square(10) fillColor red   ) beside
  ( square(10) fillColor black ) beside
  ( square(10) fillColor red   ) beside
  ( square(10) fillColor black )
) above (
  ( square(10) fillColor red   ) beside
  ( square(10) fillColor black ) beside
  ( square(10) fillColor red   ) beside
  ( square(10) fillColor black )
)
~~~

## TODO: Exercises

Concentric circles

Stick figure Vitruvian man

# Declarations

Not all programs are single expressions.
Sometimes it is useful to bind expressions to a name and re-use them later.
These constructs are called *declarations*.
Declarations themselves don't have a type or a value.
However, they do bind names that can be used as expressions.
There are several types of declaration in Scala as we shall see below.

## Value declarations

The simplest type of declaration binds a name to the result an expression.
These are called *value declarations*.
They are similar to *variable declarations*
except that they cannot be assigned to:

~~~ scala
scala> val blackSquare = Rectangle(10, 10) fillColour Colour.rgb(0, 0, 0)
blackSquare: doodle.Image = // ...

scala> val redSquare = Rectangle(10, 10) fillColour Colour.rgb(1, 0, 0)
redSquare: doodle.Image = // ...
~~~

Values can be used as sub-expressions within other expressions and declarations:

~~~ scala
scala> val twoByTwoGrid = {
     |   (blackSquare beside redSquare) above
     |   (redSquare beside blackSquare)
     | }
twoByTwoGrid: doodle.Image = // ...
~~~

<!--
## Class declarations

We can define our own types of data by defining new `classes`.
Scala also provides a short syntax for defining `case classes`,
which are regular classes with automatically generated features such as
constructors, accessor methods, `toString` methods, and equality tests.

~~~ scala
scala> case class Rectangle(width: Double, height: Double)
defined class Rectangle
~~~

Like value definitions, class definitions are not expressions
because they don't evaluate to a value.
Class definitions bind names to classes,
which are also types in Scala.

We can use the constructors defined on our classes create objects.
Constructor calls are expressions that evaluate
to values of the corresponding class:

~~~ scala
scala> val a = Rectangle(3, 4)
a: Rectangle = Rectangle(3.0,4.0)

scala> val b = Rectangle(5, 6)
b: Rectangle = Rectangle(5.0,6.0)

scala> a.width
res1: Double = 3.0

scala> b.height
res2: Double = 6.0
~~~
-->

## Method and function declarations

We can define *methods* to produce values given certain inputs.
Again, method definitions themselves are not expressions.
However, method calls *are* expressions with corresponding types and values:

~~~ scala
scala> def twoByTwo(a: Image, b: Image): Image =
     |   (a beside b) above (b beside a)
twoByTwo: (a: doodle.Image, b: doodle.Image)doodle.Image

scala> twoByTwo(redSquare, blackSquare)
res0: doodle.Image = // ...

scala> twoByTwo(redSquare, Circle(10) fillColour Colour.rgb(0, 1, 0))
res1: doodle.Image = // ...
~~~

Scala also allows us to create *functions*,
which are similar to methods but can be treated as values:

~~~ scala
scala> val outline = (image: Image) =>
     |   image lineWidth 2 lineColour Colour.rgb(0, 0, 0)
outline: doodle.Image => doodle.Image = <function1>

scala> outline(redSquare)
res5: doodle.Image = // ...
~~~

You might ask: if functions are simply methods that are also values,
why have methods at all?
The answer is an implementation detail:
there is no such thing as a first class function on the JVM.
The Scala compiler implements functions as objects with an `apply` method,
but methods still exist for efficiency and Java compatibility.

We can treat any Scala method as a function---the compiler
will insert an invisible conversion into our code.
We can make the conversion explicit by following a method with a `_`:

~~~ scala
scala> val twoByTwoFunc = (twoByTwo _)
twoByTwoFunc: (doodle.Image, doodle.Image) => doodle.Image = <function2>

scala> twoByTwoFunc(redSquare, blackSquare)
res6: doodle.Image = // ...
~~~

## Functions as values

**TODO: This isn't a compelling example!**

Why have functions that can be treated as first class values at all?
This property allows us to write *higher order* functions and methods
that consume and generate functions as parameters or return values.

Consider the following code that fills and outlines shapes
without first class functions:

~~~ scala
scala> import doodle.syntax.normalised._
import doodle.syntax.normalised._

scala> def fill(image: Image, colour: Colour): Image =
     |   image fillColour colour
fill: (image: doodle.Image, colour: doodle.Colour)doodle.Image

scala> def outline(image: Image, colour: Colour): Image =
     |   image lineWidth 2 lineColour colour
outline: (image: doodle.Image, colour: doodle.Colour)doodle.Image

scala> def fillAndOutline(image: Image, colour: Colour): Image =
     |   outline(fill(image, colour), colour darken 0.1.clip)
fillAndOutline: (image: doodle.Image, colour: doodle.Colour)doodle.Image
~~~

to this code that does uses first class functions
to represent and compose the fill and outline transformations:

~~~ scala
scala> def fill(colour: Colour) = (image: Image) =>
     |   image fillColour colour
fill: (colour: doodle.Colour)doodle.Image => doodle.Image

scala> def outline(colour: Colour) = (image: Image) =>
     |   image lineWidth 2 lineColour colour
outline: (colour: doodle.Colour)doodle.Image => doodle.Image

scala> def fillAndOutline(colour: Colour) =
     |   fill(colour) andThen outline(colour darken 0.1.clip)
fillAndOutline: (colour: doodle.Colour)doodle.Image => doodle.Image
~~~

In this case, the functional style is much more declarative:
define blocks of functionality, name them,
and compose them to produce larger blocks.

## Substitution

In the absence of side-effects such as variable assignment and input/output,
an expression will always evaluate to the same value.
For example, the expression `3 + 4` will always evaluate to the value `7`,
no matter how many times we compile or run the code.

Given these restrictions, the expressions `3 + 4` and `7`
become interchangeable from a user's point of view.
This is known as the *subs􏰂titution model* of evaluation,
although you may remember it as "simplifying formulae"
from your maths class at school.

As programmers we must develop a mental model of how our code operates.
In the absence of side-effects, the subs􏰂tutionn model always works.
If we know the types and values of each component of an expression,
we know the type and value of the expression as a whole.
In functional programming we aim to avoid side-effects for this reason:
it makes our programs easy to reason about
without having to look beyond the current block of code.

## TODO: Exercises

Checkerboard using names:

- define 2x2
- 4x4 is built from 2x2
- 8x8 is built from 4x4

## TODO: Color

Lines and fills

RGB and HSL

Checkerboard using colour

## TODO: For comprehensions? Map and flatMap? First class functions?

## TODO: Exercises
Checkerboard without recursion?

## Recursion
Structural recursion over the natural numbers

## Exercises
Sierpinski triangle

Concentric circles

Checkerboards

# Extended Exercise: Color palettes
Interesting sequences of colours
- Analagous colours, complements, triads, tetrads.
- Period of repetition
- GCD
- Linear congruential generator

# Extended Exercise: Fractals
Trees? Or Sierpinkski stuff?
