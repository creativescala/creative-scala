## Value Declarations

The simplest type of declaration binds a name to the result an expression.
These are called *value declarations*.
They are similar to *variable declarations*,
except we cannot assign new values to them after declaration:

~~~ scala
val blackSquare = Rectangle(30, 30) fillColor Color.black
// blackSquare: doodle.Image = // ...

val redSquare = Rectangle(30, 30) fillColor Color.red
// redSquare: doodle.Image = // ...
~~~

**Exercise: Chess Board**

Create an 8x8 square chess board without loops or comprehensions,
using the definitions of `redSquare` and `blackSquare` above.
Use intermediate value declarations to make your code as compact as possible:

![Chess board](src/pages/declarations/chessboard.png)

<div class="solution">
An 8x8 chess board can be decomposed into four 4x4 boards,
each consisting four 2x2 boards, each consisting four squares:

~~~ scala
val blackSquare = Rectangle(30, 30) fillColor Color.black
val redSquare   = Rectangle(30, 30) fillColor Color.red

val twoByTwo =
  (redSquare   beside blackSquare) above
  (blackSquare beside redSquare)

val fourByFour =
  (twoByTwo beside twoByTwo) above
  (twoByTwo beside twoByTwo)

val chessBoard =
  (fourByFour beside fourByFour) above
  (fourByFour beside fourByFour)
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
