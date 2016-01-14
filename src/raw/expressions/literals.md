## Literal Expressions

We'll now start to explore the various forms of expressions in Scala, starting with the simplest expressions *literals*. Here's a literal expression:

```tut:book
3
```

A literal evaluates to "itself." How we write the expression and how the console prints the value are the same. Remember though, there is a difference between the written representation of a value and its actual representation in the computer's memory.

Scala has many different forms of literals. We've already seen `Int` literals. There is a different type, and a different literal syntax, for what are called *floating point numbers*. This corresponds to a computer's approximation of the real numbers. Here's an example:

```tut:book
0.1
```

As you can see, the type is called `Double`.

### Exercises

#### Floating Point Failings

Why did I say above that `Double` is an approximation of the real numbers? Think about representing numbers like ⅓ and π.

<div class="solution">
`Double` is an approximation because it has the fit within the computer's finite memory. A `Double` takes up precisely 64-bits, which is enough space to store a lot of digits but not enough to store a number that, like π, has an infinite expansion.

The number ⅓ also has an infinite expansion in decimal. As `Doubles` are stored in binary there are some numbers that can be represented in a finite number of decimal digits but have no finite representation in binary. 0.1 turns out to be one such number.

In general, floating point numbers can lead to nasty surprises if you expect them to act like the reals. They are fine for our purposes in Creative Scala, but don't go using them to write accounting software!
</div>

Numbers are well and good, but what about text. Scala's `String` type represents a sequence of characters. We write literal strings by putting their contents in double quotes.

```tut:book
"To be fond of dancing was a certain step towards falling in love."
```

Sometimes we want to write strings that span several lines. We can do this by using triple double quotes, as below.

```tut:book
"""
A new, a vast, and a powerful language is developed for the future use of analysis,
in which to wield its truths so that these may become of more speedy and accurate
practical application for the purposes of mankind than the means hitherto in our
possession have rendered possible. 

  -- Ada Lovelace, the world's first programmer
"""
```

A `String` is a sequence of characters. Characters themselves have a type, `Char`, and character literals are written in single quotes.

```tut:book
'a'
```

Finally we'll look at the literal representations of the `Boolean` type, named after English logician [George Boolean](https://en.wikipedia.org/wiki/George_Boole). The fancy name just means a value that can be either `true` or `false`, and this indeed is how we write boolean literals.

```tut:book
true
false
```
