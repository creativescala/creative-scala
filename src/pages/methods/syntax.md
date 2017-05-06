## Method Syntax

```tut:invisible
import doodle.core._
import doodle.core.Image._
import doodle.syntax._
import doodle.jvm.Java2DCanvas._
import doodle.backend.StandardInterpreter._
```

We've already seen an example of declaring a method.

```tut:silent:book
def boxes(color: Color): Image = {
  val box =
    Image.rectangle(40, 40).
      lineWidth(5.0).
      lineColor(color.spin(30.degrees)).
      fillColor(color) 

  box beside box beside box beside box beside box
}
```

Let's use this as a model for understanding the syntax of declaring a method.
The first part is the *keyword* `def`.
A keyword is a special word that indicates something important to the Scala compiler---in this case that we're going to declare a method.
We're already seen `object` and `val` keywords.

The `def` is immediately followed by the name of the method, in this case `boxes`, in the same way that `val` and `object` are immediately followed by the name they declare.
Like a `val` declaration a method declaration is not a top-level declaration and must be wrapped in an `object` declaration (or other top-level declaration) when written in a file.

Next we have the method parameters, defined in brackets (`()`).
The method parameters are the parts that the caller can "plug-in" to the expression that the method evaluates.
When declaring method parameters we must give them both a name and a type.
A colon (`:`) separates the name and the type.
We haven't had to declare types before.
Most of the time Scala will work out the types for us, a process known as *type inference*.
Type inference, however, cannot infer the type of method parameters so we must provide them.

After the method parameters comes the result type.
The result type is the type of the value the method evaluates to when it is called.
Unlike parameter types Scala can infer the result type, but it is good practice to include it and we will do so throughout Creative Scala.

Finally, the body expression of the method calculates the result of calling the method.
A body can be a block expression, as in `boxes` above, or just a single expression.

<div class="callout callout-info">
#### Method Declaration Syntax {-}

The syntax for a method declaration is

```scala
def methodName(param1: Param1Type, ...): ResultType =
  bodyExpression
```

where

- `methodName` is the name of the method;
- the optional `param1 : Param1Type, ...` are one or more pairs of parameter name and parameter type;
- the optional `ResultType` is the type of the result of calling the method; and
- `bodyExpression` is the expression that is evaluated to yield the result of calling the method.
</div>


### Exercises {-}

Let's practice declaring methods by writing some simple examples.

#### Square {-}

Write a method `square` that accepts an `Int` argument and returns the `Int` square of it's argument. (Squaring a number is multiplying it by itself.)

<div class="solution">
The solution is

```tut:silent:book
def square(x: Int): Int = 
  x * x
```

We can arrive at the solution by the following steps.

We're given the name (`square`), the type of the parameter (`Int`), and the type of the result (`Int`).
From this we can write the method skeleton

```tut:silent:book
def square(x: Int): Int =
  ???
```

where we have chosen `x` as the name of the parameter.
This is a fairly arbitrary choice.
Where there is no meaningful name you often see one-letter names such as `x`, `v`, or `i` used.

By the way this is valid code.
Enter it into the console and see!
What happens if you call `square` when it's defined like so?

Now we need to complete the body.
We've been told that squaring is multiplying a number by itself, so `x * x` is what we replace the `???` with.
We don't need to wrap this in braces as there is only a single expression in the body.
</div>


#### Halve {-}

Write a method `halve` that accepts a `Double` argument and returns the `Double` that is half of it's argument. 

<div class="solution">
```tut:silent:book
def halve(x: Double): Double =
 x / 2.0
```

We can follow the same process as for `square` above to arrive at the solution.
</div>
