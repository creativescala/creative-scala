# Literal Expressions

The simplest expressions are *literals*. Here's a literal expression:

```scala mdoc
3
```

A literal evaluates to "itself." How we write the expression and how the worksheet prints the value are the same. Remember though, there is a difference between the written representation of a value and its actual representation in the computer's memory.

Scala has many different forms of literals. We've already seen `Int` literals. There is a different type, and a different literal syntax, for what are called *floating point numbers*. This corresponds to a computer's approximation of the real numbers. Here's an example:

```scala mdoc
0.1
```

As you can see, the type is called `Double`.

Numbers are well and good, but what about text? Scala's `String` type represents a sequence of characters. We write literal strings by putting their contents in double quotes.

```scala mdoc
"To be fond of dancing was a certain step towards falling in love."
```

Sometimes we want to write strings that span several lines. We can do this by using triple double quotes, as below.

```scala mdoc
"""
A new, a vast, and a powerful language is developed for the future use of analysis,
in which to wield its truths so that these may become of more speedy and accurate
practical application for the purposes of mankind than the means hitherto in our
possession have rendered possible.

  -- Ada Lovelace, the world's first programmer
"""
```

A `String` is a sequence of characters. Characters themselves have a type, `Char`, and character literals are written in single quotes.

```scala mdoc
'a'
```

Finally we'll look at the literal representations of the `Boolean` type, named after English logician [George Boole](https://en.wikipedia.org/wiki/George_Boole). This fancy name just means a value that can be either `true` or `false`, and this indeed is how we write boolean literals.

```scala mdoc
true
false
```

With literal expressions, we can create values, but we won't get very far if we can't somehow manipulate the values we've created. We've seen a few examples of more complex expressions like `1 + 2`. In the next section, we'll learn about objects and methods, which will allow us to understand how this, and more interesting expressions, work.
