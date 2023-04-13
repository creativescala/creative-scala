## Functions as Abstractions

We have written a lot of structural recursions over the natural numbers. We started with code like

```scala mdoc:invisible
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._
```
```scala mdoc:silent
def stackedBoxes(count: Int): Image =
  count match {
    case 0 => Image.empty
    case n => aBox.beside(stackedBoxes(n-1))
  }
```

and more recently have been writing code like

```scala mdoc:silent
def polygonPoints(sides: Int, radius: Double): Image = {
  val turn = (1.0 / sides).turns
  def loop(count: Int): Image =
    count match {
      case 0 => Image.empty
      case n =>
        Image
          .circle(5)
          .at(Point(radius, turn * n))
          .on(loop(n - 1))
    }

  loop(sides)
}
```

As you can appreciate, there is an underlying pattern here that stays the same. In fact that's one of the points we're really emphasizing in this section: that there are these basic underlying patterns in programming that can adapt to many different situations.

A function is basically a method, but we can use a function as a first-class value:

- we can pass it as an argument or parameter to a method or function; 
- we can return it from a method or function; and
- we can give it a name using `val`.

Here's an example where we give the name `add42` to a function that adds 42 to its input.

```scala mdoc
val add42 = (x: Int) => x + 42
```

We can call it just like we'd call a method.

```scala mdoc
add42(0)
```

This is an example of a function literal. Let's learn about them now.



### Function Literals

We've just seen an example of a function literal, which was

```scala mdoc
(x: Int) => x + 42
```

The general syntax is an extension of this.


<div class="callout callout-info">
#### Function Literal Syntax {-}

The syntax for declaring a function literal is

```scala
(parameter: type, ...) => expression
```

where
- the optional `parameter`s are the names given to the function parameters;
- the `type`s are the types of the function parameters; and
- the `expression` determines the result of the function.

The parentheses around the parameters are optional if the function has just a single parameter.
</div>



### Function Types

To pass functions to methods we need to know how to write down their types (because when we declare a parameter we have to declare its type).

We write a function type like `(A, B) => C` where `A` and `B` are the types of the parameters and `C` is the result type. 
The same pattern generalises from functions of no arguments to an arbitrary number of arguments.

Here's an example. We create a method that accepts a function, and that function is from `Int` to `Int`. We write this type as `Int => Int` or `(Int) => Int`.

```scala mdoc
def squareF(x: Int, f: Int => Int): Int =
  f(x) * f(x)
```

We can pass `add42` to this method

```scala mdoc
squareF(0, add42)
```

We could also pass a function literal

```scala mdoc
squareF(0, x => x + 42)
```

Note that we didn't have to put the parameter type on the function literal in this case because Scala has enough information to infer the type.



<div class="callout callout-info">
#### Function Type Declaration Syntax {-}

To declare a function type, write

```scala
(A, B, ...) => C
```

where

- `A, B, ...` are the types of the input parameters; and
- `C` is the type of the result.

If a function only has one parameter the parentheses may be dropped:

```scala
A => B
```
</div>



### Functions as Objects

All first class values are objects in Scala, including functions.
This means functions can have methods, including some useful means for composition.

```scala mdoc
val addTen = (a: Int) => a + 10
val double = (a: Int) => a * 2
val combined = addTen.andThen(double) // this composes the two functions
combined(5)
```

Calling a function is actually calling the method called `apply` on the function. Scala allows a shortcut for any object that has a method called `apply`, where can drop the method name `apply` and write the call like a function call. This means the following are equivalent.

```scala mdoc
val halve = (a: Int) => a / 2
halve(4)
halve.apply(4)
```


### Converting Methods to Functions

Methods are very similar to functions, so Scala provides a way to convert functions to methods. If we follow a method name with a `_` it will be converted to a function.

```scala mdoc
def times42(x: Int): Int =
  x * 42

val times42Function = times42 _
```

We can also write a method call but replace all parameters with `_` and Scala will convert the method to a function.

```scala mdoc
val times42Function2 = times42(_)
```



#### Exercises {-}

##### Function Literals

Let's get some practice writing function literals. Write a function literal that:

- squares it's `Int` input;
- has a `Color` parameter and spins the hue of that `Color` by 15 degrees; and
- takes an `Image` input and creates four copies in a row, where each copy is rotated by 90 degrees relative to the previous image (use the `rotate` method on `Image` to achieve this.)

<div class="solution">
The first function is

```scala mdoc
(x: Int) => x * x
```

The second is

```scala mdoc
(c: Color) => c.spin(15.degrees)
```

The third is

```scala mdoc
(image: Image) => 
  image.beside(image.rotate(90.degrees))
    .beside(image.rotate(180.degrees))
    .beside(image.rotate(270.degrees))
    .beside(image.rotate(360.degrees))
```
</div>



##### Function Types {-}

Here's an interesting function we'll do more with in later sections. We don't need to understand what it does right now, though you might want to experiment with it.

```
val roseFn = (angle: Angle) =>
  Point.cartesian((angle * 7).cos * angle.cos, (angle * 7).cos * angle.sin)
```

What is the type of the function `roseFn` defined above? What does this type mean?

<div class="solution">
The type is `Angle => Point`. This means `roseFn` is a function that takes a single argument of type `Angle` and returns a value of type `Point`. In other words, `roseFn` transforms an `Angle` to a `Point`.
</div>
