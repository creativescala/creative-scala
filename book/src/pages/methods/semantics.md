# Method Semantics

Now that we know how to declare methods, let's turn to the semantics.
How do we understand a method call in terms of our substitution model?

We already know we can substitute a method call with the value it evaluates to.
However we need a more fine-grained model so we can work out what this value will be.
Our extended model is as follows: when we see a method call we will create a new block and within this block:
bind the parameters to the respective expressions given in the method call and substitute the method body.

We can then apply substitution as usual.

Let's see a simple example.
Given the method

```scala mdoc:silent
def square(x: Int): Int =
  x * x
```

we can expand the method call

```scala mdoc:silent
square(2)
```

by introducing a block

```scala mdoc:silent
{
  square(2)
}
```

binding the parameter `x` to the expression `2`

```scala mdoc:silent
{
  val x = 2
  square(2)
}
```

and substituting the method body

```scala mdoc:silent
{
  val x = 2
  x * x
}
```

We can now perform substitution as usual giving

```scala mdoc:silent
{
  2 * 2
}
```

and finally

```scala mdoc:silent
{
  4
}
```

Once again we see that substitution is involved but no single step was particularly difficult.


@:exercise(Method Order of Evaluation)
Last time we looked at substitution we spent a lot of time investigating order of evaluation.
In the description above we have decided that a method's arguments are evaluated before the body is evaluated.
This is not the only possibility.
We could, for example, evaluate the method's arguments only at the point they are needed.
This could save us some time if a method doesn't use one of its parameters.
By using our old friend `println`, determine when method parameters are evaluated in Scala.
@:@

@:solution
The following program demonstrates that parameters are evaluated before the method body.

```scala mdoc
def example(a: Int, b: Int): Int = {
  println("In the method body!")
  a + b
}

example({ println("a"); 1 }, { println("b"); 2 })
```

The alternative we described above is used by some languages, most notably Haskell, and is known as lazy or non-strict evaluation.
@:@
