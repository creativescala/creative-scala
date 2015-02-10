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
*expressions*, *values*, and *types*.
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

We are used to thinking of types that refer to sets of literals
such as `Int`, `Boolean`, and `String`,
but in general a type is defined as
*anything we can infer about a program without running it*.
Scala developer use types to gain assurances about our programs
before we put them into production.

## Simple Scala Expressions

Let's look at some of the basic kinds of expressions we can write in Scala:

### Literals

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

### Method calls

Scala is a completely object-oriented programming language,
so all values are objects with methods that we can call.
Method calls are another type of expression:

~~~ scala
scala> 123.4.ceil
res4: Double = 124.0

scala> true.toString
res5: String = "true"
~~~

### Constructor calls

**TODO: Do we need this section?
I've included it to cover expressions `Circle(10)` later on.**

Scala only has literals for a small set of types
(`Int`, `Boolean`, `String`, and so on).
To create values of other types we either need to call a method,
or we need to call a *constructor* using the `new` keyword.
This behaves similarly to `new` in Java:

~~~ scala
scala> import java.util.Date
import java.util.Date

scala> new Date()
res4: java.util.Date = Tue Feb 10 10:30:21 GMT 2015
~~~

For reasons we shall see later on,
Scala libraries typically provide factory methods to wrap constructor calls.
We don't often see the `new` operator in Scala code unless
we are interacting with Java libraries:

~~~ scala
scala> List(1, 2, 3)
res6: List[Int] = List(1, 2, 3)
~~~

### Operators

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

### Conditionals

Many other syntactic constructs are expressions in Scala,
including some that are statements in Java.
Conditionals ("if expressions") are a great example:

~~~ scala
// Conditionals ("if expressions"):
scala> if(123 > 456) "Higher!" else "Lower!"
res6: String = Lower!
~~~

<!--
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
-->

## Images

Numbers and text are boring.
Let's switch to something more interesting---images!
Grab the *Doodle* project from [https://github.com/underscoreio/doodle]().
This toy drawing library will provide the basis for
most of the exercises in this course.
Start a Scala console to try Doodle out:

~~~ bash
bash$ git clone https://github.com/underscoreio/doodle.git
# Cloning ...

bash$ cd doodle

bash$ ./sbt.sh console
[info] Loading project definition from /.../doodle/project
[info] Set current project to doodle (in build file:/.../doodle/)
[info] Compiling 1 Scala source to /.../doodle/jvm/target/scala-2.11/classes...
[info] Starting scala interpreter...
[info]
import doodle.core._
import doodle.syntax._
import doodle.jvm._
Welcome to Scala version 2.11.5 (Java HotSpot(TM) 64-Bit Server VM, Java 1.7.0_45).
Type in expressions to have them evaluated.
Type :help for more information.

scala>
~~~

### Primitive Images

The Doodle project gives us access to some useful drawing tools
as well as the regular Scala core library. Try creating a simple shape:

~~~ scala
scala> Circle(10)
res0: doodle.core.Circle = Circle(10.0)
~~~

Notice the type and value of the expression we just entered.
The type is `doodle.core.Circle`
and the value is `Circle(10.0)`---a circle with a 10 pixel radius.

We can draw this circle (and other shapes) using Doodle's `draw()` function,
which should have been brought into scope automatically.
Try drawing the circle now.

~~~ scala
scala> draw(res0)
~~~

A window should appear containing the following:

![A circle](src/pages/circle.png)

Doodle supports two "primitive" images: circles and rectangles.
Let's try drawing a rectangle:

~~~ scala
scala> draw(Rectangle(50, 100))
~~~

![A rectangle](src/pages/rectangle.png)

### Simple Layout

We can combine images to produce more complex images.
Try the following code---you should see a circle and a rectangle
displayed next to one another:

~~~ scala
scala> draw(Circle(10) beside Rectangle(10, 20))
~~~

![A circle beside a rectangle](src/pages/circle-beside-rectangle.png)

Doodle contains several layout operators for combining images.
Try them out now to see what they do:

----------------------------------------------------------------------------------------
Operator              Result  Description                Example
--------------------- ------- -------------------------- -------------------------------
`Image beside Image`  `Image` Places images horizontally `Circle(10) beside Circle(20)`
                              next to one another.

`Image above Image`   `Image` Places images vertically   `Circle(10) above Circle(20)`
                              next to one another.

`Image below Image`   `Image` Places images vertically   `Circle(10) below Circle(20)`
                              next to one another.

`Image on Image`      `Image` Places images centered     `Circle(10) on Circle(20)`
                              on top of one another.

`Image under Image`   `Image` Places images centered     `Circle(10) under Circle(20)`
                              on top of one another.
----------------------------------------------------------------------------------------

### Exercise: Stay on Target

Create a line drawing of an archery target with three concentric scoring bands.
For bonus credit add a stand so we can place the target on a range.

<div class="solution">
The simplest solution is to create three concentric circles using the `on` operator:

~~~ scala
draw(Circle(10) on Circle(20) on Circle(30))
~~~

![Simple archery target](src/pages/target1.png)

For the extra credit we can create a stand using two rectangles:

~~~ scala
draw(
  Circle(10) on
  Circle(20) on
  Circle(30) above
  Rectangle(6, 20) above
  Rectangle(20, 6)
)
~~~

![Archery target with a stand](src/pages/target2.png)

</div>

## TODO: Statements/Unit/Side-Effects?

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
scala> val blackSquare = Rectangle(10, 10) fillColor Color.rgb(0, 0, 0)
blackSquare: doodle.Image = // ...

scala> val redSquare = Rectangle(10, 10) fillColor Color.rgb(1, 0, 0)
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

scala> twoByTwo(redSquare, Circle(10) fillColor Color.rgb(0, 1, 0))
res1: doodle.Image = // ...
~~~

Scala also allows us to create *functions*,
which are similar to methods but can be treated as values:

~~~ scala
scala> val outline = (image: Image) =>
     |   image lineWidth 2 lineColor Color.rgb(0, 0, 0)
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

scala> def fill(image: Image, color: Color): Image =
     |   image fillColor color
fill: (image: doodle.Image, color: doodle.Color)doodle.Image

scala> def outline(image: Image, color: Color): Image =
     |   image lineWidth 2 lineColor color
outline: (image: doodle.Image, color: doodle.Color)doodle.Image

scala> def fillAndOutline(image: Image, color: Color): Image =
     |   outline(fill(image, color), color darken 0.1.clip)
fillAndOutline: (image: doodle.Image, color: doodle.Color)doodle.Image
~~~

to this code that does uses first class functions
to represent and compose the fill and outline transformations:

~~~ scala
scala> def fill(color: Color) = (image: Image) =>
     |   image fillColor color
fill: (color: doodle.Color)doodle.Image => doodle.Image

scala> def outline(color: Color) = (image: Image) =>
     |   image lineWidth 2 lineColor color
outline: (color: doodle.Color)doodle.Image => doodle.Image

scala> def fillAndOutline(color: Color) =
     |   fill(color) andThen outline(color darken 0.1.clip)
fillAndOutline: (color: doodle.Color)doodle.Image => doodle.Image
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

Checkerboard using color

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
Interesting sequences of colors
- Analagous colors, complements, triads, tetrads.
- Period of repetition
- GCD
- Linear congruential generator

# Extended Exercise: Fractals
Trees? Or Sierpinkski stuff?
