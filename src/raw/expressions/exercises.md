## Exercises

#### Arithmetic

Write an expression using integer literals, addition, and subtraction that evaluates to 42.

<div class="solution">
This exercise is just about getting used to writing Scala code. Here is one possible solution.

```tut:book
1 + 43 - 2
```
</div>


#### Appending Strings

Join together two strings (known as *appending* strings) using the `++` method. Write equivalent expressions using both the normal method call style and operator style.

<div class="solution">
Something like the below should do.

```tut:book
"It is a truth ".++("universally acknowledged")
"It is a truth " ++ "universally acknowledged"
```
</div>

#### Precedence

In mathematics we learned that some operators take *precedence* over others. For example, in the expression `1 + 2 * 3` we should do the multiplication before the addition. Do the same rules hold in Scala?

<div class="solution">
A bit of exploration at the console should convince you that yes, Scala does maintain the standard precedence rules. The example below demonstrates this.

```tut:book
1 + 2 * 3
1 + (2 * 3)
(1 + 2) * 3
```
</div>


#### Types and Values

Which of the following expressions will not compile? Of the expressions that will compile, what is their type? Which expressions fail at run-time?

```tut:silent
1 + 2
```

```tut:silent
"3".toInt
```

```tut:fail:silent
"Electric blue".toInt
```

```tut:silent
"Electric blue".take(1)
```

```tut:fail:silent
"Electric blue".take("blue")
```

```tut:silent
1 + ("Moonage daydream".indexOf("N"))
```

```tut:silent
1 / 1 + ("Moonage daydream".indexOf("N"))
```

```tut:fail:silent
1 / (1 + ("Moonage daydream".indexOf("N")))
```

<div class="solution">
```tut:book
1 + 2
```

This expression has type `Int` and evaluates to `3`.


```tut:fail:book
"3".toInt
```

This expression has type `Int` and evaluates to `3`.

```tut:fail:book
"Electric blue".toInt
```

This expression has type `Int` but fails at run-time.

```tut:silent
"Electric blue".take(1)
```

This expression has type `String` and evaluates to `"E"`.

```tut:fail:book
"Electric blue".take("blue")
```

This expression fails at compile-time and hence has no type.

```tut:book
1 + ("Moonage daydream".indexOf("N"))
```

This expression has type `Int` and evaluates to `0`.

```tut:book
1 / 1 + ("Moonage daydream".indexOf("N"))
```

This expression has type `Int` and, due to precedence, evaluates to `(1 / 1) + -1`, which is `0`.

```tut:fail:silent
1 / (1 + ("Moonage daydream".indexOf("N")))
```

This expression has type `Int` but fails at run-time with a division by zero.
</div>

#### Floating Point Failings

When we introduced `Doubles`, I said they are an approximation to the real numbers. Why do you think this is? Think about representing numbers like ⅓ and π. How much space would it take to represent these numbers in decimal?

<div class="solution">
`Double` is an approximation because it has the fit within the computer's finite memory. A `Double` takes up precisely 64-bits, which is enough space to store a lot of digits but not enough to store a number that, like π, has an infinite expansion.

The number ⅓ also has an infinite expansion in decimal. As `Doubles` are stored in binary there are some numbers that can be represented in a finite number of decimal digits but have no finite representation in binary. 0.1 turns out to be one such number.

In general, floating point numbers can lead to nasty surprises if you expect them to act like the reals. They are fine for our purposes in Creative Scala, but don't go using them to write accounting software!
</div>

#### Beyond Expressions

In our current model of computation there are only three components: expressions (program text) with types, that evaluate to values (something within the computer's memory). Is this sufficient? Could we write a stock market or a computer game with just this model? Can you think of ways to extend this model?

<div class="solution">
This is very open ended question. There are several ways to go beyond the model we have so far. 

To be useful our programs must be capable of creating effects---changes in the world that go beyond the computer's memory. For example, displaying things on the screen, making sound, sending messages to other computers, and the like. The console implicitly does some of this for us, by printing values on the screen. We'll need to go a bit beyond that for more useful programs.

We also don't have any way to define our own objects and methods, or reuse values in our programs. If we want to, say, use someone's name across a program we have to repeat that name everywhere. We need more methods of *abstraction* and that's what we'll turn to soon.
</div>
