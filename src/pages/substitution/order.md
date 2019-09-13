## Order of Evaluation

```scala mdoc:invisible
import doodle.core._
import doodle.image._
import doodle.image.syntax._
import doodle.image.syntax.core._
import doodle.java2d._
```

We're now ready to tackle the question of order-of-evaluation.
We might wonder if the order of evaluation even matters?
In the examples we've looked at so far the order doesn't seem to matter, except for the issue that we cannot evaluate an expression before it's sub-expressions.

To investigate these issues further we need to introduce a new concept.
So far we have almost always dealt with *pure* expressions.
These are expressions that we can freely substitute in any order without issue[^corner-cases].

*Impure* expressions are those where the order of evaluation matters.
We have already used one impure expression, the method `draw`.
If we evaluate

```scala
Image.circle(100).draw
Image.rectangle(100, 50).draw
```

and

```scala
Image.rectangle(100, 50).draw
Image.circle(100).draw
```

the windows containing the images will appear in different orders.
Hardly an exciting difference, but it *is* a difference, which is the point.

The key distinguishing feature of impure expressions is that their evaluation causes some change that we can see.
For example, evaluating `draw` causes an image to be displayed.
We call these observable changes *side effects*, or just *effects* for short.
In a program containing side effects we cannot freely use substitution.
However we can use side effects to investigate the order of evaluation.
Our tool for doing so will be the `println` method.

The `println` method displays text on the console (a side effect) and evaluates to unit.
Here's an example:

```scala mdoc
println("Hello!")
```

The side-effect of `println`---printing to the console---gives us a convenient way to investigate the order of evaluation.
For example, the result of running

```scala mdoc
println("A")
println("B")
println("C")
```

indicates to us that expressions are evaluated from top to bottom.
Let's use `println` to investigate further.


### Exercises {-}

#### No Substitute for Println {-}

In a pure program we can give a name to any expression and substitute any other occurrences of that expression with the name.
Concretely, we can rewrite

```scala mdoc:silent
(2 + 2) + (2 + 2)
```

to

```scala mdoc:silent
val a = (2 + 2)
a + a
```

and the result of the program doesn't change.

Using `println` as an example of an impure expression, demonstrates that this is *not* the case for impure expressions, and hence we can say that impure expressions, or side effects, break substitution.

<div class="solution">
Here is a simple example that illustrates this.
The following two programs are observably different.

```scala mdoc
println("Happy birthday to you!")
println("Happy birthday to you!")
println("Happy birthday to you!")
```

```scala mdoc
val happy = println("Happy birthday to you!")
happy
happy
happy
```

Therefore we cannot freely use substitution in the presence of side effects, and we must be aware of the order of evaluation.
</div>


#### Madness to our Methods {-}

When we introduced scopes we also introduced block expressions, though we didn't call them that at the time.
A block is created by curly braces (`{}`). It evaluates all the expressions inside the braces. The final result is the result of the last expression in the block.

```scala mdoc
// Evaluates to three
{
  val one = 1
  val two = 2
  one + two
}
```

We can use block expressions to investigate the order in which method parameters are evaluated, by putting `println` expression inside a block that evaluates to some other useful value.

For example, using `Image.rectangle` or `Color.hsl` and block expressions, we can determine if Scala evaluates method parameters in a fixed order, and if so what that order is.

Note that you can write a block compactly, on one line, by separating expressions with semicolons (`;`).
This is generally not good style but might be useful for these experiments.
Here's an example.

```scala mdoc
// Evaluates to three
{ val one = 1; val two = 2; one + two }
```

<div class="solution">
The following code demonstrates that method parameters are evaluated from left to right.

```scala mdoc
Color.hsl(
  {
    println("a")
    0.degrees
  },
  {
    println("b")
    1.0
  },
  {
    println("c")
    1.0
  }
)
```

We can write this more compactly as
```scala mdoc
Color.hsl({ println("a"); 0.degrees },
          { println("b"); 1.0 },
          { println("c"); 1.0 })
```
</div>


#### The Last Order {-}

In what order are Scala expressions evaluated?
Perform whatever experiments you need to determine an answer to this question to your own satisfaction.
You can reasonably assume that Scala uses consistent rules across all expressions.
There aren't special cases for different expressions.

<div class="solution">
We've already seen that expressions are evaluated from top-to-bottom, and method parameters are evaluated from left-to-right.
We might want to check that expressions are in general evaluated left-to-right.
We can show this fairly easily.

```scala mdoc
{ println("a"); 1 } + { println("b"); 2 } + { println("c"); 3}
```

So in conclusion we can say that Scala expressions are evaluated from top-to-bottom and left-to-right.
</div>

[^corner-cases]: This is not entirely true. There are some corner cases where the order of evaluation does make a difference even with pure expressions. We're not going to worry about these cases here. If you're interested in learning more, and this is interesting and useful stuff, you can read up on "eager evaluation" and "lazy evaluation".
