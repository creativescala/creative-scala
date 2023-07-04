## Exercises

@:exercise(Greater Expressions, Lesser Expressions)

The less than (`<`) and greater than (`>`) methods on `Int` work in the usual way.
What do you think the type an expression using these methods is? 
For example, what would you guess the type of

```scala
1 < 2
```

is? How can you verify your answer?

What about

```scala
(3 + 2) > 7
```

and

```scala
(3 / 0) < 6
```
?
@:@

@:solution
These are all `Boolean` expressions.
The first evalutes to `true`, the second to `false`, and the third does not evaluate to any value.
You can verify this in the worksheet, by looking at the output for expressions that succeed, by hovering over the expressions, or by adding a type annotation and seeing if the compiler complains about it.
@:@


@:exercise(Abyssal Depths)

Below is a quote from *Wuthering Heights*. Write an expression to extract `"leave me in this abyss"` from this `String`.

```scala
"""
  Be with me always—take any form—drive me mad! 
  only do not leave me in this abyss, where I cannot find you!"
"""
```
@:@

@:solution
Using the `substring` method will do the job.

```scala mdoc
"""
  Be with me always—take any form—drive me mad! 
  only do not leave me in this abyss, where I cannot find you!"
""".substring(76, 98)
```

From this exercise you will probably notice that indices into the `String` start at 0; a common convention in programming but perhaps a little surprising the first time you encounter it.
@:@


@:exercise(Floating Point Failings)

When we introduced `Doubles`, I said they are an approximation to the real numbers. Why do you think this is? Think about representing numbers like ⅓ and π. How much space would it take to represent these numbers in decimal?
@:@

@:solution
`Double` is an approximation because it has the fit within the computer's finite memory. A `Double` takes up precisely 64-bits, which is enough space to store a lot of digits but not enough to store a number that, like π, has an infinite expansion.

The number ⅓ also has an infinite expansion in decimal. Because Doubles are stored in binary there are some numbers that can be represented in a finite number of decimal digits but have no finite representation in binary. 0.1 turns out to be one such number.

In general, floating point numbers can lead to nasty surprises if you expect them to act like the reals. They are fine for our purposes in Creative Scala, but don't go using them to write accounting software!
@:@


@:exercise(Appending Strings)

Join together two strings (known as *appending* strings) using the `++` method. Write equivalent expressions using both the normal method call style and the operator style.
@:@

@:solution
Something like the below should do.

```scala mdoc
"It is a truth ".++("universally acknowledged")
"It is a truth " ++ "universally acknowledged"
```
@:@


@:exercise(Stylish Operator)
When we discussed operator style, we said that

```scala
a b c d e
```

is equivalent to

```scala
a.b(c).d(e)
```

and not

```scala
a.b(c, d, e)
```

Can you write an expression that shows this is the case? (Hint: think about using a method with two or more parameters.)
@:@

@:solution
We know the `substring` method has two parameters. 

```scala mdoc
"The only way to test a hypothesis is to look for ... information that disagrees with it.".substring(16, 33)
```

If we try writing this in operator style, as below, the code will not compile.

```scala
"The only way to test a hypothesis is to look for ... information that disagrees with it." substring 16 33
```

However if we try a method with only a single parameter, we can use either style.

```scala mdoc
1 + 2 + 3
1.+(2).+(3)
```

So, if we assume there is a consistent translation between the two (one that does not depend on the method being called, for example) then we have shown the conversion between the two is what we claimed.
@:@


@:exercise(Beyond Expressions)

In our current model of computation there are only three components: expressions (program text) with types, that evaluate to values (something within the computer's memory). Is this sufficient? Could we write a stock market or a computer game with just this model? Can you think of ways to extend this model? (We haven't so far addressed this in the book, so there is no reason you should be able to answer this question. It's here to provoke thought.)
@:@

@:solution
This is very open ended question. There are several ways to go beyond the model we have so far.

To be useful our programs must be capable of creating effects—changes in the world that go beyond the computer's memory. For example, displaying things on the screen, making sound, sending messages to other computers, and the like. The console implicitly does some of this for us, by printing values on the screen. We'll need to go a bit beyond that for more useful programs.

We also don't have any way to define our own objects and methods, or reuse values in our programs. If we want to, say, use someone's name across a program we have to repeat that name everywhere. We need more methods of *abstraction* and that's what we'll turn to soon.
@:@
