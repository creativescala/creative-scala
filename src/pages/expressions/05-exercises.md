## Exercises

Let's check our understanding of the core concepts in this chapter with some exercises.

#### Arithmetic

Write an expression using integers, addition, and subtraction that evaluates to 42.

This exercise is just about getting used to writing Scala code. There are many possible solutions.

<div class="solution">

Here's how I chose to do it.

```scala mdoc
1 + 43 - 2
```
</div>


#### Precedence

In mathematics we learned that some operators take *precedence* over others. For example, in the expression `1 + 2 * 3` we should do the multiplication before the addition. Do the same rules hold in Scala? Use the worksheet to find out.

<div class="solution">
A bit of exploration should convince you that yes, Scala does maintain the standard precedence rules. The example below demonstrates this.

```scala mdoc
1 + 2 * 3
1 + (2 * 3)
(1 + 2) * 3
```
</div>

#### Types and Values

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

```scala mdoc:silent:crash
1 / 0
```

<div class="solution">
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
</div>

#### Appending Strings

Join together two strings (known as *appending* strings) using the `++` method. Write equivalent expressions using both the normal method call style and the operator style.

<div class="solution">
Something like the below should do.

```scala mdoc
"It is a truth ".++("universally acknowledged")
"It is a truth " ++ "universally acknowledged"
```
</div>



#### Floating Point Failings

When we introduced Doubles, I said they are an approximation to the real numbers. Why do you think this is? Think about representing numbers like ⅓ and π. How much space would it take to represent these numbers in decimal?

<div class="solution">
`Double` is an approximation because it has the fit within the computer's finite memory. A `Double` takes up precisely 64-bits, which is enough space to store a lot of digits but not enough to store a number that, like π, has an infinite expansion.

The number ⅓ also has an infinite expansion in decimal. Because Doubles are stored in binary there are some numbers that can be represented in a finite number of decimal digits but have no finite representation in binary. 0.1 turns out to be one such number.

In general, floating point numbers can lead to nasty surprises if you expect them to act like the reals. They are fine for our purposes in Creative Scala, but don't go using them to write accounting software!
</div>

#### Beyond Expressions

In our current model of computation there are only three components: expressions (program text) with types, that evaluate to values (something within the computer's memory). Is this sufficient? Could we write a stock market or a computer game with just this model? Can you think of ways to extend this model?

<div class="solution">
This is very open ended question. There are several ways to go beyond the model we have so far.

To be useful our programs must be capable of creating effects---changes in the world that go beyond the computer's memory. For example, displaying things on the screen, making sound, sending messages to other computers, and the like. The console implicitly does some of this for us, by printing values on the screen. We'll need to go a bit beyond that for more useful programs.

We also don't have any way to define our own objects and methods, or reuse values in our programs. If we want to, say, use someone's name across a program we have to repeat that name everywhere. We need more methods of *abstraction* and that's what we'll turn to soon.
</div>
