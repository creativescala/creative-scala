## Algebraic Data Types

```scala mdoc:invisible
import doodle.core._
import doodle.image._
import doodle.image.syntax._
import doodle.image.syntax.core._
import doodle.java2d._
```

We've used algebraic data types throughout Creative Scala, 
but we've been a bit informal in how we describe them.
At this stage a bit more rigour is useful.

An algebraic data type is built from two components:
- *logical ors*; and
- *logical ands*.

The `List` data type is a great example of an algebraic data type, as it uses both patterns.
A `List` is `Nil` *or* a pair (the logical or pattern) and a pair has a head *and* a tail (the logical and pattern). 
`Point` is another example. A `Point` is either `Cartesian` or `Polar`. 
A `Cartesian` has an x and y coordinate, while a `Polar` has a radius and an angle.
Note it's not necessary to use both patterns to be an algebraic data type.

Being functional programmers we naturally have some fancy words for the logical or and logical and patterns.
They are:
- a *sum type* for the logical or; and
- a *product type* for the logical and.

The concept of an algebraic data type is not specific to Scala.
Let's get some practice working with the concept before we see how to write algebraic data types in Scala.

#### Exercises {-}

##### Path Elements {-}

The `PathElement` type, used to construct paths, is a simple algebraic data type.
You've used `PathElement` quite a bit so far.
How do you think `PathElement` is defined in terms of sum and product types?

<div class="solution">
A `PathElement` is a sum type, as it is:
- a `MoveTo`; or
- a `LineTo`; or
- a `CurveTo`.

A `MoveTo` is a product type that holds a single point (where to move to).

A `LineTo` is a product type that holds a single point (the end point of the line).

A `CurveTo` is a product type that holds three points: two control points and the end point of the line.
</div>

##### Totally Turtles {-}

The `Instruction` type we used to control the turtle is also an algebraic data type.
How do you think `Instruction` is defined?

<div class="solution">
An `Instruction` is:
- a `Forward`; or
- a `Turn`; or
- a `Branch`; or
- a `NoOp`

Therefore `Instruction` is a sum type. `Forward`, `Turn`, and `Branch` are all product types.

A `Forward` holds a distance, which is a `Double`.

A `Turn` holds an angle, which is an `Angle`.

A `Branch` holds a `List[Instruction]`---therefore the `Instruction` type is defined in terms of itself, just like `List`.

A `NoOp` holds no data.
</div>


### Defining Algebraic Data Types

No we understand how to model data with algebraic data types, let's see how to define our own.

The pattern is this:

- If `A` is a `B` or `C` write

```scala mdoc:invisible:reset-object
import doodle.core._
import doodle.image._
import doodle.image.syntax._
import doodle.image.syntax.core._
import doodle.java2d._
```
```scala mdoc
sealed abstract class A extends Product with Serializable
final case class B() extends A
final case class C() extends A
```

There is a lot boilerplate here, which we can basically ignore beyond accepting it's stuff we have to write. However, if you're interested in what it means (and possibly have some prior object-oriented programming experience).

<div class="info-warning">
Describe `sealed` etc. here.
</div>

To define `PathElement` we might start with

```scala mdoc
sealed abstract class PathElement extends Product with Serializable
final case class MoveTo() extends PathElement
final case class LineTo() extends PathElement
final case class CurveTo() extends PathElement
```

The other half of the pattern is

- If `A` has a `B` and `C`, write

```scala
final case class A(b: B, c: C)
```

<div class="info-warning">
Describe constructor parameters here.
</div>

Returning to `PathElement`, `MoveTo` and `LineTo` each have a point (the destination) and `CurveTo` has a destination point and two control points. So we could write.

```scala
sealed abstract class PathElement extends Product with Serializable
final case class MoveTo(to: Point) extends PathElement
final case class LineTo(to: Point) extends PathElement
final case class CurveTo(cp1: Point, cp2: Point, to: Point) extends PathElement
```

And this is essentially how `PathElement` is defined in Doodle.

#### Exercise {-}

Define your own algebraic data type to represent `Instruction`.

<div class="solution">
We can directly turn the textual description into code using the patterns above.

```scala mdoc
sealed abstract class Instruction extends Product with Serializable
final case class Forward(distance: Double) extends Instruction
final case class Turn(angle: Angle) extends Instruction
final case class Branch(instructions: List[Instruction]) extends Instruction
final case class NoOp() extends Instruction
```
</div>
