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

def chessBoard =
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

## Method and function declarations

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
