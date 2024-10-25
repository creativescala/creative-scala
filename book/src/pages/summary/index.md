# Summary

In this text we have covered a handful of the essential
functional programming tools available in Scala.

## Representations and Interpreters

We started by writing expressions to create and compose images.
Each program we wrote went through two distinct phases:

 1. Build an `Image`
 2. Call the `draw` method to display the image

This process demonstrates two important functional programming patterns:
*building intermediate representations* of the result we want,
and *interpreting the representations* to produce output.

## Abstraction

Building an intermediate representation allows
us to only model the aspects of the result that we consider important
and *abstract* irrelevant details.

For example, Doodle directly represents the primitive shapes
and geometric relationships in our drawings,
without worrying about implementation details such as screen coordinates.
This keeps our code clear and maintainable,
and limits the number of "magic numbers" we need to write.
For example, it is a lot easier to determine
that this Doodle program produces a house:

```scala
def myImage: Image =
  Triangle(50, 50) above Rectangle(50, 50)
// myImage: Image = // ...
````

than this implementation in Java2D:

```scala
def drawImage(g: Graphics2D): Unit = {
  g.setStroke(new BasicStroke(1.0f))
  g.setPaint(new Color(0, 0, 0))
  val path = new Path2D.Double()
  path.moveTo(25, 0)
  path.lineTo(50, 50)
  path.lineTo(0, 50)
  path.lineTo(25, 0)
  path.closePath()
  g.draw(path)
  f.drawRect(50, 50, 50, 50)
}
````

It's important to realise that all of the imperative Java2D
code is still present in Doodle.
The difference is we have hidden it away into the `draw` method.
`draw` acts as *interpreter* for our `Images`,
filling in all of the details about coordinates, paths,
and graphics contexts that we don't want to think about in our code.

Separating the immediate value and the interpreter
also allows us to change how interpretation is performed.
Doodle already comes with two interpreters,
one of which draws in the Java2D framework
while the other draws in the HTML canvas.
You can image yet more interpreters to, for example,
achieve artistic effects such as drawing images in a hand-drawn style.

## Composition

In addition to making our programs clearer,
the functional approach employed by Doodle
allows us to *compose* images from other images.
For example, we can re-use our house to draw a street:

```scala
val house = Triangle(50, 50) above Rectangle(50, 50)
// house: Image = // ...

val street = house beside house beside house
// street: Image = // ...
````

The `Image` and `Color` values we create are immutable
so we can easily re-use a single `house` three times within the same image.

This approach allows us to break down a complex image into simpler parts
that we then combine together to create the desired result.

Reusing immutable data, a technique called *structure sharing*,
is the basis of many fast, memory efficient immutable data structures.
The quintissential example in Doodle is the Sierpinski triangle
where we re-used a single `Triangle` object to represent an image
containing nearly 20,000 distinct coloured triangles.

## Expression-Oriented Programming

Scala provides convenient syntax to simplify
creating data structures in a functional manner.
Constructs such as conditionals, loops, and blocks are *expressions*,
allowing us to write short method bodies without
declaring lots of intermediate variables.
We quickly adopt a pattern of writing short methods
whose main purpose is to return a value,
so omitting the `return` keyword is also a useful shorthand.

## Types are a Safety Net

Scala's type system helps us by checking our code.
Every expression has a type that is checked at compile time
to see if it matches up with its surroundings.
We can even define our own types with the explicit purpose
of stopping ourselves from making mistakes.

A simple example of this is Doodle's `Angle` type,
which prevents us confusing numbers and angles,
and degrees and radians:

```scala
90
// res0: Int = 90

90.degrees
// res1: doodle.core.Angle = Angle(1.5707963267948966)

90.radians
// res2: doodle.core.Angle = Angle(2.0354056994857643)

90.degrees + 90.radians
// res3: doodle.core.Angle = Angle(3.606202026280661)

90 + 90.degrees
// <console>:20: error: overloaded method value + with alternatives:
//   (x: Double)Double <and>
//   (x: Float)Float <and>
//   (x: Long)Long <and>
//   (x: Int)Int <and>
//   (x: Char)Int <and>
//   (x: Short)Int <and>
//   (x: Byte)Int <and>
//   (x: String)String
// cannot be applied to (doodle.core.Angle)
//              90 + 90.degrees
//                 ^
````

## Functions as Values

We spent a lot of time writing methods to produce values.
Methods let us abstract over parameters.
For example, the method below abstracts over colours
to produce different coloured dots:

```scala
def dot(color: Color): Image =
  Circle(10) strokeWidth 0 fillColor color
// dot: Color => Image = // ...
````

Coming from object oriented languages,
methods are nothing special.
More interesting is Scala's ability to turn methods into *functions*
that can be passed around as values:

```scala
def spectrum(shape: Color => Image): Image =
  shape(Color.red) beside shape(Color.blue) beside shape(Color.green)
// spectrum: (Color => Image) => Image = // ...

spectrum(dot)
// res0: Image = // ...
````

We wrote a number of programs that used functions as values,
but the quintissential example was the `map` method of `List`.
In the Collections chapter we saw
how `map` lets us transform sequences without allocating
and pushing values onto intermediate buffers:

```scala
List(1, 2, 3).map(x => x * 2)
// res0: List[Int] = List(2, 4, 6)
````

Functions, and their first class status as values,
are hugely important for writing simple, boilerplate-free code.

## Final Words

The intention of this book has been to introduce you
to the functional parts of Scala.
These are what differentiate Scala from
older commercial languages such as Java and C.
However, this is only part of Scala's story.
Many modern languages support functional programming,
including Ruby, Python, Javascript, and Clojure.
How does Scala relate to these languages,
and why would you want to choose it over
the other available options?

Perhaps the most significant draw to Scala is its type system.
This distinguishes Scala from popular languages
such as Ruby, Python, Javascript, and Clojure, which are dynamically typed.
Having static types in a language is undeniably a trade-off---writing
code is slower because we have to satisfy the compiler at every stage.
However, once our code compiles we gain
confidence about its quality.

Another major draw is Scala's blending of
object-oriented and functional programming paradigms.
We saw a little of this in the first chapter---every value is an object
with methods, fields, and a class (its type).
However, we haven't created any of our own data types in this book.
Creating types is synonymous with declaring classes,
and Scala supports a full gamut of features
such as classes, traits, interitance, and generics.

Finally, a major benefit of Scala is its compatibility with Java.
In many ways Scala can be seen as a superset of Java,
and interoperation between the two languages is quite straightforward.
This opens up a world of Java libraries to our Scala applications,
and allows flexibility when translating Java applications to Scala.

## Next Steps

We hope you enjoyed Creative Scala and drawing diagrams with Doodle.
If you would like to learn more about Scala,
we recommend that you pick one of the many great books available on the language.

Our own book, [Essential Scala][essential-scala], is available from our web site
and continues Creative Scala's approach of teaching Scala by
discussing and demonstrating core design patterns and the benefits they offer.

If you want to challenge yourself,
try drawing something more complex with Doodle and
sharing it with us via [Discord][creative-scala-discord].
There are lots of things you can try---check the `examples` directory
in the Doodle codebase for some suggestions:

@:figure{ img = "src/pages/summary/koch.png", caption = "Koch Triangle (Koch.scala)" }

@:figure{ img = "src/pages/summary/street.png", caption = "Suburban Scene (Street.scala)" }

@:figure{ img = "src/pages/summary/mandelbrot.png", caption = "Mandelbrot Fractal by Mat Moore (Mandelbrot.scala)" }
