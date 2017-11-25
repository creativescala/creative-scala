## Substitution

Substitution says that wherever we see an expression we can replace it with the value it evaluates to. For example, where we see

```tut:silent:book
1 + 1
```

we can replace it with `2`.
This in turn means when we see a compound expression such as

```tut:silent:book
(1 + 1) + (1 + 1)
```

we can substitute `2` for `1 + 1` giving

```tut:silent:book
2 + 2
```

which evaluates to `4`.

This type of reasoning is what we do in high school algebra when we simplify an expression.
Naturally computer science has fancy words for this process.
In addition to substitution, we can call this *reducing an expression*, or *equational reasoning*.

Substitution gives us a way to reason about our programs, which is another
way of saying "working out what they do".
We can apply substitution to just about any expression we've seen so far.
It's easier to use examples that work with numbers and strings, rather than images, here so we'll return to an example we saw in an earlier chapter:

```tut:silent:book
1 + ("Moonage daydream".indexOf("N"))
```

In the previous example we were a bit fast-and-loose.
Here we will be a bit more precise to illustrate the steps the computer would have to go through.
We are trying to emulate the computer, after all.

The expression containing the `+` consists of two sub-expressions, `1` and `("Moonage daydream".indexOf("N"))`.
We have to decide which to evaluate first: the left or the right.
Let's arbitrarily choose the right sub-expression (we'll return to this choice later.)

The sub-expression `("Moonage daydream".indexOf("N"))` again consists of two sub-expressions, `"Moonage daydream"` and `"N"`.
Let's again evaluate the right-hand first, remembering that literal expressions are not values so they must be evaluated.

The literal `"N"` evaluates to the value `"N"`.
To avoid this confusion let's write the value as `|"N"|`.
No we can substitute the value for the expression, giving as our first steps

```scala
1 + ("Moonage daydream".indexOf(|"N"|))
```

Now we can evaluate the left-hand side of the sub-expression, substituting the literal expression `"Moonage daydream"` with its value `|"Moonage daydream"|`.
This gives us

```scala
1 + (|"Moonage daydream"|.indexOf(|"N"|))
```

Now we're in a position to evaluate the entire expression `(|"Moonage daydream"|.indexOf(|"N"|))`, which evaluates to `|-1|` (again differentiating the integer value from the literal expression by using a vertical bar).
Once again we perform substitution and now we have

```scala
1 + |-1|
```

Now we should evaluate the left-hand side literal `1`, giving `|1|`.
Perform substitution and we get

```scala
|1| + |-1|
```

Now we can evaluate the entire expression, giving

```scala
|0|
```

We can ask Scala to evaluate the whole expression to check our work.

```tut:book
1 + ("Moonage daydream".indexOf("N"))
```

Correct!

There are some observations we might make at this point:

 - doing substitution rigorously like a computer might involve a lot of steps;
 - the shortcut evaluation you probably did in your head probably got to the correct answer; and
 - our seemingly arbitrary choice to do evaluation from right-to-left got us the correct answer.

Did we somehow manage to choose the same substitution order that Scala uses (no we didn't, but we haven't investigated this yet) or does it not really matter what order we choose?
When exactly can we take shortcuts and still reach the right result, like we did in the first example with addition?
We will investigate these questions in just a moment, but first let's talk about how substitution works with names.


### Names

The substitution rule for names is to substitute the name with the value it refers to.
We've already been using this rule implicitly.
Now we're just formalising it.

For example, given the code

```tut:silent:book
val name = "Ada"
name ++ " " ++ "Lovelace"
```

we can apply substitution to get

```tut:silent:book
"Ada" ++ " " ++ "Lovelace"
```

which evaluates to

```tut:silent:book
"Ada Lovelace"
```

We can use names to be a bit more formal with our substitution process.
Returning to our first example

```tut:silent:book
1 + 1
```

we can give this expression a name:

```tut:silent:book
val two = 1 + 1
```

When we see a compound expression such as

```tut:silent:book
(1 + 1) + (1 + 1)
```

substitution tells us we can substitute `two` for `1 + 1` giving

```tut:silent:book
two + two
```

Remember when we worked through the expression

```tut:silent:book
1 + ("Moonage daydream".indexOf("N"))
```

we broke it into sub-expressions which we then evaluated and substituted.
Using words, this was quite convoluted.
With a few `val` declarations we can make this both more compact and easier to see.
Here's the same expression broken into it's components.

```tut:silent:book
val a = 1
val b = "Moonage daydream"
val c = "N"
val d = b.indexOf(c)
val e = a + d
```

If we (at this point, arbitrarily) define that evaluation occurs from top-to-bottom we can experiment with different ordering to see what difference they make.

For example,

```tut:silent:book
val c = "N"
val b = "Moonage daydream"
val a = 1
val d = b.indexOf(c)
val e = a + d
```

achieves the same result as before.
However we can't use

```scala
val e = a + d
val a = 1
val b = "Moonage daydream"
val c = "N"
val d = b.indexOf(c)
```

because `e` depends on `a` and `d`, and in our top-to-bottom ordering `a` and `d` have yet to be evaluated.
We might rightly claim that this is a bit silly to even attempt. The complete expression we're trying to evaluate is  `e` but `a` to `d` are sub-expressions of `e`, so of course we have to evaluate the sub-expressions before we evaluate the expression.
