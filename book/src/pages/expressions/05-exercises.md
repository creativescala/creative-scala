## Exercises

Let's check our understanding of the core concepts in this chapter with some exercises.

@:exercise(Arithmetic)

Write an expression using integers, addition, and subtraction that evaluates to 42.

This exercise is just about getting used to writing Scala code. There are many possible solutions.
@:@

@:solution
Here's how I chose to do it.

```scala mdoc
1 + 43 - 2
```
@:@


@:exercise(Precedence)

In mathematics we learned that some operators take *precedence* over others. For example, in the expression `1 + 2 * 3` we should do the multiplication before the addition. Do the same rules hold in Scala? Use the worksheet to find out.
@:@

@:solution
A bit of exploration should convince you that yes, Scala does maintain the standard precedence rules. The example below demonstrates this.

```scala mdoc
1 + 2 * 3
1 + (2 * 3)
(1 + 2) * 3
```
@:@


@:exercise(Types and Values)

Below we've written a number of expressions. For each expression, try to answer the questions:

1. It will compile?
2. If it compiles, what is it's type?
3. If it compiles can it be evaluated, or will it fail at run-time?

Then try to check your guesses using the worksheet. If you guessed wrong, try to think about what part of your understanding is incorrect. 

```scala mdoc:silent
1 + 2
```

```scala mdoc:silent:fail
1 ? 2
```

```scala mdoc:silent
4 / 2
```

```scala mdoc:silent
1 / 2
```

```scala
1 / 0
```
@:@

@:solution
```scala mdoc
1 + 2
```

Easy example as we've seen this one before. This expression has type `Int` and evaluates to `3`.


```scala mdoc:fail
1 ? 2
```

This is not a valid expression and does not compile. As such it doesn't have a type.

```scala mdoc
4 / 2
```

This expression has type `Int` and evaluates to `2`.

```scala mdoc
1 / 2
```

This expression has type `Int` and evaluates to `0`. People coming from dynamically typed languages will sometimes guess that this evaluates to `0.5` and hence the type would be a floating point number. This would require we pass information back from the run-time to compile-time, which is not possible.

```scala mdoc:fail
1 / 0
```

This expression has type `Int` but fails at run-time. This exercise is emphasizing that the type is a property of the expression, not the value, and types are not determined by run time behaviour.
@:@
