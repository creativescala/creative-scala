## Functions as Abstractions

Let's motivate functions by look at structural recursion over the natural numbers, and trying to capture in code the pattern we've used many times.

We have written a lot of structural recursions over the natural numbers. We started with code like

```scala mdoc:invisible
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
```
```scala
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

The details are different, but the underlying structure of the two examples is the same. In fact that's one of the points we're really emphasizing: that code has underlying patterns that apply in many different situations.

When we see this repetition we might wonder if we can somehow capture the commonality in code, so we don't have to write it out again and again. This is what we're going to look at in this section, and in doing so we'll introduce functions.

Let's start by putting the two structural recursions next to each other, and in the case of `polygonPoints` removing the surrounding code.

```scala mdoc:invisible:reset
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
```
```scala
def stackedBoxes(count: Int): Image =
  count match {
    case 0 => Image.empty
    case n => aBox.beside(stackedBoxes(n-1))
  }

def loop(count: Int): Image =
  count match {
    case 0 => Image.empty
    case n =>
      Image
        .circle(5)
        .at(Point(radius, turn * n))
        .on(loop(n - 1))
  }
```

If we keep the common parts of these methods and replace the parts that vary with `???` we end up with something like

```scala
def aMethod(count: Int): Image =
  count match {
    case 0 => Image.empty
    case n => ??? aMethod(count - 1)
  }
```

This is the code skeleton for structural recursion over the natural numbers.

Now our challenge is to turn this into something we can actually use in Scala. Let's look at the recursive case in the code skeleton

```scala
case n => ??? aMethod(count - 1)
```

This is the only part that has a `???`. In the real methods we have

```scala
case n => aBox.beside(stackedBoxes(n-1))
```

and

```scala
case n =>
  Image
    .circle(5)
    .at(Point(radius, turn * n))
    .on(loop(n - 1))
```

To capture this in code we need to allow:

1. varying how we build the result of the recursion (in one case it's `beside`, while we use `on` in the other); and
2. creating the `Image` at the current step to depend on `n` (as in the case taken from `polygonPoints`, where we use the value `n` to determine the location of the circle.)

In other words, the value we build is parameterized by the result of the recursion and the value `n`. We could express this with a method

```scala
def build(n: Int, recursive: Image): Image = ???
```

and then, with two different implementations of `build`, we could write both original methods in terms of

```scala
def aMethod(count: Int, build: ???): Image =
  count match {
    case 0 => Image.empty
    case n => build(n, aMethod(count - 1, build))
  }

```

However this won't work; we cannot pass a method as a parameter to a method. The solution, of course, is to use a function.

A function is basically a method, but we can use a function as a first-class value:

- we can pass it as an argument or parameter to a method or function; 
- we can return it from a method or function; and
- we can give it a name using `val`.

Here's how we can solve the problem above using functions. First I'm going to define the method we've been calling `aMethod` above. I'm going to call it `fold`, which is the usual name for this kind of method. (This isn't an entirely correct implementation for fold, but we don't have all the tools to do it properly right now. This is something we'll come back to in a later chapter.)

```scala mdoc:silent
def fold(count: Int, build: (Int, Image) => Image): Image =
  count match {
    case 0 => Image.empty
    case n => build(n, fold(count - 1, build))
  }
```

This method definition says that the parameter `build` is a function from two arguments (an `Int` and an `Image`) to an `Image`. To call `fold` we need to create a function, so let's see an example of that.

```scala mdoc:silent
val aBox = Image.square(20).fillColor(Color.royalBlue).strokeColor(Color.crimson)

val stack = (count: Int, image: Image) => aBox.above(image)
```

We can now call `fold` with `stack`.

```scala mdoc:silent
fold(5, stack)
```

This produes the output below.

@:doodle("fold", "HofFold.stack")

Now that we've seen an example of functions, let's go into the details of defining and using them.


### Function Literals

We've just seen an example of a function literal, which was

```scala mdoc:silent
(count: Int, image: Image) => aBox.above(image)
```

The general syntax is an extension of this.


@:callout(info)
#### Function Literal Syntax

The syntax for declaring a function literal is

```scala
(parameter: type, ...) => expression
```

where
- the optional `parameter`s are the names given to the function parameters;
- the `type`s are the types of the function parameters; and
- the `expression` determines the result of the function.

The parentheses around the parameters are optional if the function has just a single parameter.
@:@



### Function Types

To pass functions to methods we need to know how to write down their types (because when we declare a parameter we have to declare its type).

We write a function type like `(A, B) => C` where `A` and `B` are the types of the parameters and `C` is the result type. 
The same pattern generalises from functions of no arguments to an arbitrary number of arguments.

Here's an example. We create a method that accepts a function, and that function is from `Int` to `Int`. We write this type as `Int => Int` or `(Int) => Int`.

```scala mdoc:silent
def squareF(x: Int, f: Int => Int): Int =
  f(x) * f(x)
```

We can define a function `add42`, and pass it to this method

```scala mdoc:silent
val add42 = (x: Int) => x + 42
```
```scala mdoc
squareF(0, add42)
```

We could also pass a function literal

```scala mdoc
squareF(0, x => x + 42)
```

Note that we didn't have to put the parameter type on the function literal in this case because Scala has enough information to infer the type.



@:callout(info)
#### Function Type Declaration Syntax

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
@:@



### Functions as Objects

All first class values are objects in Scala, including functions.
This means functions can have methods, including some useful means for composition.

```scala mdoc:silent
val addTen = (a: Int) => a + 10
val double = (a: Int) => a * 2
val combined = addTen.andThen(double) // this composes the two functions
```

```scala mdoc
combined(5)
```

Calling a function is actually calling the method called `apply` on the function. Scala allows a shortcut for any object that has a method called `apply`, where can drop the method name `apply` and write the call like a function call. This means the following are equivalent.

```scala mdoc:silent
val halve = (a: Int) => a / 2
```

```scala mdoc
halve(4)
halve.apply(4)
```


### Converting Methods to Functions

Methods are very similar to functions, so Scala provides a way to convert functions to methods. If we follow a method name with a `_` it will be converted to a function.

```scala mdoc:silent
def times42(x: Int): Int =
  x * 42
```

```scala mdoc
val times42Function = times42 _
```

We can also write a method call but replace all parameters with `_` and Scala will convert the method to a function.

```scala mdoc
val times42Function2 = times42(_)
```



#### Exercises

@:exercise(Function Literals)

Let's get some practice writing function literals. Write a function literal that:

- squares it's `Int` input;
- has a `Color` parameter and spins the hue of that `Color` by 15 degrees; and
- takes an `Image` input and creates four copies in a row, where each copy is rotated by 90 degrees relative to the previous image (use the `rotate` method on `Image` to achieve this.)
@:@

@:solution
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
@:@



@:exercise(Function Types)

Here's an interesting function we'll do more with in later sections. We don't need to understand what it does right now, though you might want to experiment with it.

```
val roseFn = (angle: Angle) =>
  Point.cartesian((angle * 7).cos * angle.cos, (angle * 7).cos * angle.sin)
```

What is the type of the function `roseFn` defined above? What does this type mean?
@:@

@:solution
The type is `Angle => Point`. This means `roseFn` is a function that takes a single argument of type `Angle` and returns a value of type `Point`. In other words, `roseFn` transforms an `Angle` to a `Point`.
@:@
