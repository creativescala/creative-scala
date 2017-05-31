## Functions

```tut:invisible
import doodle.core._
import doodle.core.Image._
import doodle.syntax._
import doodle.jvm.Java2DFrame._
import doodle.backend.StandardInterpreter._
```

As the error message we saw in the previous section above suggests, we can convert any method to a function using the `_` operator and call it with the same parameters.


```tut:silent:book
// Parametric equation for rose with k = 7
def rose(angle: Angle) =
  Point.cartesian((angle * 7).cos * angle.cos, (angle * 7).cos * angle.sin)
```
```tut:book
rose _

(rose _)(0.degrees)
```

A function is basically a method, but we can use a function as a first-class value:

- we can pass it as an argument or parameter to a method or function; 
- we can return it from a method or function; and
- we can give it a name using `val`.

```tut:book
val roseFn = rose _
roseFn(0.degrees)
```


### Function Types

To pass functions to methods we need to know how to write down their types (because when we declare a parameter we have to declare its type).

We write a function type like `(A, B) => C` where `A` and `B` are the types of the parameters and `C` is the result type. 
The same pattern generalises from functions of no arguments to an arbitrary number of arguments.

In our example above we want `f` to be a function that accepts two `Int`s as parameters and returns an `Int`. Thus we can write it as `(Int, Int) => Int`.

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


### Function Literals

There is a literal syntax for functions. 
For example, here is a function that adds `42` to its input.

```tut:book
(x: Int) => x + 42
```

We can apply the function to an argument in the usual way.

```tut:book
val add42 = (x: Int) => x + 42
add42(0)
```

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
</div>


### Functions as Objects

Because Scala is an object oriented language, all first class values are objects.
This means functions can have methods, including some useful means for composition:

```tut:book
val addTen = (a: Int) => a + 10
val double = (a: Int) => a * 2
val combined = addTen andThen double // this composes the two functions
combined(5)
```

#### Exercises {-}

##### Function Types {-}

What is the type of the function `roseFn` defined above? What does this type mean?

<div class="solution">
The type is `Angle => Point`. This means `roseFn` is a function that takes of single argument of type `Angle` and returns a value of type `Point`. In other words, `roseFn` transforms an `Angle` to a `Point`.
</div>

##### Function Literals {-}

Write `roseFn` as a function literal.

<div class="solution">
```tut:book
val roseFn = (angle: Angle) =>
  Point.cartesian((angle * 7).cos * angle.cos, (angle * 7).cos * angle.sin)
```
</div>


