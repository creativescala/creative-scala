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

![A circle](src/pages/expressions/circle.png)

Doodle supports two "primitive" images: circles and rectangles.
Let's try drawing a rectangle:

~~~ scala
scala> draw(Rectangle(50, 100))
~~~

![A rectangle](src/pages/expressions/rectangle.png)

### Layout

We can combine images to produce more complex images.
Try the following code---you should see a circle and a rectangle
displayed next to one another:

~~~ scala
scala> draw(Circle(10) beside Rectangle(10, 20))
~~~

![A circle beside a rectangle](src/pages/expressions/circle-beside-rectangle.png)

Doodle contains several layout operators for combining images.
Try them out now to see what they do:

----------------------------------------------------------------------------------------
Operator              Type    Description                Example
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

### Exercise: Compilation Target

Create a line drawing of an archery target with three concentric scoring bands.
For bonus credit add a stand so we can place the target on a range.

<div class="solution">
The simplest solution is to create three concentric circles using the `on` operator:

~~~ scala
draw(Circle(10) on Circle(20) on Circle(30))
~~~

![Simple archery target](src/pages/expressions/target1.png)

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

![Archery target with a stand](src/pages/expressions/target2.png)
</div>

### Colour

In addition to layout, Doodle has some simple operators
to add a splash of colour to our images.
Try these out to see how they work:

---------------------------------------------------------------------------------------------
Operator                Type    Description                 Example
----------------------- ------- --------------------------- ---------------------------------
`Image fillColor Color` `Image` Fills the image with        `Circle(10) fillColor Color.red`
                                the specified colour.

`Image lineColor Color` `Image` Outlines the image with     `Circle(10) lineColor Color.blue`
                                the specified colour.

`Image lineWidth Int`   `Image` Outlines the image with     `Circle(10) lineWIdth 3`
                                the specified stroke width.
--------------------------------------------------------------------------------------------

Doodle has various ways of creating colours.
The simplest are the predefined colours in
`shared/src/main/scala/doodle/core/CommonColors.scala`.
Here are a few of the most important:

------------------------------------------------------------------
Color                   Type    Example
----------------------- ------- ----------------------------------
`Color.red`             `Color` `Circle(10) fillColor Color.red`

`Color.blue`            `Color` `Circle(10) fillColor Color.blue`

`Color.green`           `Color` `Circle(10) fillColor Color.green`

`Color.black`           `Color` `Circle(10) fillColor Color.black`

`Color.white`           `Color` `Circle(10) fillColor Color.white`

`Color.gray`            `Color` `Circle(10) fillColor Color.gray`

`Color.brown`           `Color` `Circle(10) fillColor Color.brown`
------------------------------------------------------------------

### Exercise: Stay on Target

Colour your target red and white, the stand in brown (if applicable),
and if you're feeling adventurous add some ground in green.

<div class="solution">
The trick here is using parentheses to control the order of composition.
Colours and line widths apply to anything that isn't already explicitly styled,
so we must make sure we only apply styles where necessary:

~~~ scala
draw(
  ( Circle(10) fillColor Color.red   ) on
  ( Circle(20) fillColor Color.white ) on
  ( Circle(30) fillColor Color.red lineWidth 2 ) above
  ( Rectangle(6, 20) above Rectangle(20, 6) fillColor Color.brown ) above
  ( Rectangle(80, 25) lineWidth 0 fillColor Color.green )
)
~~~

![Colour archery target](src/pages/expressions/target3.png)
</div>

## TODO: Statements/Unit/Side-Effects?
