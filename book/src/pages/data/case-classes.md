# Case Classes

Case classes are the first way of constructing data that we'll look at. From the denotational perspective, a case class represents a *logical and*. For example, we're already worked with `Vec`. It represents a two-dimensional vector as an x *and* y coordinate, using a case class. From the operational perspective. Well, it's more complicated and we'll see the details.


## An Example

We'll get started with an example of a case class in an animation.

```scala mdoc:reset-object:silent
import doodle.core._
import doodle.image._
import doodle.reactor._
import doodle.java2d._

final case class Circle(radius: Int)

val animation =
  Reactor
    .init(Circle(20))
    .withRender(circle => Image
                           .circle(circle.radius)
                           .strokeWidth(5)
                           .strokeColor(Color.midnightBlue))
    .withOnTick(circle => if(circle.radius > 200) Circle(20) else Circle(circle.radius + 10))

```
```scala
animation.run(Frame.size(400, 400))
```

Run the animation and see what happens. Now read the code and try to understand how it works. You might need to know that the function passed to `withRender` determines how a `Circle` is transformed into an `Image`, and the function passed to `withOnTick` determines how the `Circle` is updated each clock tick. The animation is updated each clock tick.


## Declaring and Using Case Classes

We're now going to look at how we can declare case classes, and some of the ways we can use them.

We've seen an example of declaring a case class:

```scala
final case class Circle(radius: Int)
```

This declares a case class called `Circle`. It has a single field called `radius` with the type `Int`.

A case class is not a value. It declares a type. To create a value---an object---of type `Circle` we can write, for example,

```scala mdoc:silent
Circle(20)
```

In this case the field `radius` has the value 20.

A bit of terminology. 

- When we create a value of a type, we sometimes say we're creating an *instance*.
- Creating 

The terminology "instance" and "constructor" comes from the field of object-oriented programming.
