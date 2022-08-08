## Match Expressions

In the previous section we saw the `match` expression

```scala
count match {
  case 0 => Image.empty
  case n => aBox.beside(boxes(n-1))
}
```

How are we to understand this new kind of expression,
and write our own?
Let's break it down.

The very first thing to say is that `match` is indeed an expression,
which means it evaluates to a value.
If it didn't, the `boxes` method would not work!

To understand what it evaluates to we need more detail.
A `match` expression in general has the shape

```scala
<anExpression> match {
  case <pattern1> => <expression1>
  case <pattern2> => <expression2>
  case <pattern3> => <expression3>
  ...
}
```

`<anExpression>`, concretely `count` in the case above, is the expression that evaluates to the value we're matching against.
The patterns `<pattern1>` and so on are matched against this value.
So far we've seen two kinds of patterns:

 - a literal (as in `case 0`) which matches exactly the value that literal evaluates to; and
 - a wildcard (as in `case n`) which matches *anything*, and introduces a binding within the right-hand side expression.

Finally, the right-hand side expressions, `<expression1>` and so on, are just expressions like any other we've written so far.
The entire `match` expression evaluates to the value of the right-hand side expression of the *first* pattern that matches.
So when we call `boxes(0)` both patterns will match (because the wildcard matches anything), but because the literal pattern comes first the expression `Image.empty` is the one that is evaluated.

A `match` expression that checks for all possible cases is called an exhaustive match.
If we can assume that `count` is always greater or equal to zero, the `match` in `boxes` is exhaustive.

Once we're comfortable with `match` expressions we need to look at the structure of the natural numbers before we can explain structural recursion over them.


### Exercises {-}

#### Guess the Result {-}

Let's check our understanding of match by guessing what each of the following expressions evaluates to, and why.

```scala mdoc:fail:silent
"abcd" match {
  case "bcde" => 0
  case "cdef" => 1
  case "abcd" => 2
}
```

```scala mdoc:fail:silent
1 match {
  case 0 => "zero"
  case 1 => "one"
  case 1 => "two"
}
```

```scala mdoc:fail:silent
1 match {
  case n => n + 1
  case 1 => 1000
}
```

```scala mdoc:fail:silent
1 match {
  case a => a
  case b => b + 1
  case c => c * 2
}
```

<div class="solution">

The first example evaluates to `2`, as the pattern `"abcd"` is the only match for the literal `"abcd"` amongst the patterns.

The second example evaluates to `"one"`, because the first matching case is the one that is evaluated.

The third example evaluates to `2`, because `case n` defines a wildcard pattern that matches anything.

The final example evaluates to `1` because the first matching case is evaluated.
</div>

#### No Match {-}

What happens if no pattern matches in a `match` expression?
Take a guess, then write a `match` expression that fails to match and see if you managed to guess correctly.
(At this point we have no reason to expect any particular behavior so any reasonable guess will do.)

<div class="solution">
Here are three reasonable possibilities I can think of; perhaps you came up with something else?

 - The expression could evaluate to some default, like `Image.empty` (but how should Scala pick the right default?)
 - The Scala compiler should just not let you write code like that.
 - The `match` expression will fail at runtime.

Here's a match expression that doesn't match.

```scala mdoc:crash
2 match {
  case 0 => "zero"
  case 1 => "one"
}
```

The correct answer is one of the last two possibilities, failing to compile or failing at runtime.
In this example we have an error at runtime.
The exact answer depends on how Scala is configured (we can tell the compiler to refuse to compile matches that it can show are not exhaustive, but this is not the default behavior).
</div>
