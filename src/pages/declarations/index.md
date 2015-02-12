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
They are similar to *variable declarations*,
except we cannot assign new values to them after declaration:

~~~ scala
scala> val blackSquare = Rectangle(30, 30) fillColor Color.black
blackSquare: doodle.Image = // ...

scala> val redSquare = Rectangle(30, 30) fillColor Color.red
redSquare: doodle.Image = // ...
~~~

**Exercise: Chess Board**

Create an 8x8 square chess board without loops or comprehensions,
using the definitions of `redSquare` and `blackSquare` above.
Use intermediate value declarations to make your code as compact as possible:

![Chess board](src/pages/declarations/chessboard.png)

<div class="solution">
An 8x8 chess board can be decomposed into four 2x2 boards,
each consisting 2x2 squares:

~~~ scala
val blackSquare = Rectangle(30, 30) fillColor Color.black
val redSquare   = Rectangle(30, 30) fillColor Color.red

val twoByTwo =
  (redSquare   beside blackSquare) above
  (blackSquare beside redSquare)

val chessBoard =
  (twoByTwo beside twoByTwo) above
  (twoByTwo beside twoByTwo)
~~~

This is significantly clearer and more compact
than creating the whole board in one expression:

~~~ scala
val b = Rectangle(30, 30) fillColor Color.black
val r = Rectangle(30, 30) fillColor Color.red

val chessBoard =
  (r beside b beside r beside b beside r beside b beside r beside b) above
  (r beside b beside r beside b beside r beside b beside r beside b) above
  (r beside b beside r beside b beside r beside b beside r beside b) above
  (r beside b beside r beside b beside r beside b beside r beside b) above
  (r beside b beside r beside b beside r beside b beside r beside b) above
  (r beside b beside r beside b beside r beside b beside r beside b) above
  (r beside b beside r beside b beside r beside b beside r beside b) above
  (r beside b beside r beside b beside r beside b beside r beside b)
~~~
</div>

## Method declarations

Sometimes we want to repeat a process, but each time we do it we change some part of what we're doing. For example, imagine creating chess boards where each chess board has a different combination of colors. It would be extremely tedious to declaration each chess board as we have above for each choice of colors. What we'd like is to be able to define some process for making chess boards that allows the user to specify the color choice for the particular chess board we're making. This is what *methods* allow us to do.

We have already seen method calls. In this section we are going to see how we can create our own methods, using *method declarations*. Like value declarations, method declarations define a name. Instead of giving a name to a value, a method declaration gives a name to a process for creating values. Let's see an example.

~~~ scala
def twoByTwo(color1: Color, color2: Color): Image = {
  val square1 = Rectangle(30, 30) fillColor color1
  val square2 = Rectangle(30, 30) fillColor color2

  (square1 beside square2) above
  (square2 beside square1)
}
~~~ 

This declares a method called `twoByTwo`. The method has two parameters, called `color1` and `color2`, which we have declared to have type `Color`. We have also declared the type of the value the method returns when called, which is `Image`. The body of the method, which is enclosed with optional braces (the `{` and `}` pair) defines how we create the `Image`. This mirrors the process for creating a two by two chess board that we saw above, but in this case we are using the colors we are passed as parameters.

**Syntax summary here**

**Substition model?**

**Exercise: Chess Board**

Declare a method called `fourByFour` that constructs a four-by-four chess board using `twoByTwo` declared above. The method should have two parameters, both `Color`s, that it passes on to `twoByTwo`. 

You should be able to call `fourByFour` like so

~~~ scala
fourByFour(Color.cornflowerBlue, Color.seaGreen) beside
fourByFour(Color.chocolate, Color.darkSalmon)
~~~

to create this picture:

![Two Chessboards](src/pages/declarations/two-chessboards.png)

<div class="solution">
The structure of `fourByFour` is identical to `twoByTwo` except that we use `twoByTwo` to construct the squares we build the chessboard from.

~~~ scala
def fourByFour(color1: Color, color2: Color): Image = {
  val square = twoByTwo(color1, color2)

  (square beside square) above
  (square beside square)
}
~~~
</div>

**TODO: Color exercises**
 - RGB and HSL
 - Complimentary colours, analogous, triads, tetrads
 - GCD
 - Linear congruential generators

## Recursive algorithms

Let's put our new-found skills with colour to use on some recursive algorithms.
Recursion is a natural part of functional programming.
The classic functional data structure---the linked list---is recursive in nature.
Before we start looking at lists, however, let's create some recursive pictures.

**TODO: Concentric circles**
- Draw it (structural recursion over natural numbers)
- Supply fixed colour-changing operation to palette cycle

**TODO: Sierpinski triangle**

## Functions as values

**TODO: Higher order functions**

**TODO: Stripey wormholes**
- Use palette functions to abstract colour changes

**TODO: Rainbow sierpinski**
- "Sierpinkski"
