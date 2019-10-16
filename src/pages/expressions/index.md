# Expressions, Values, and Types

Scala programs have three fundamental building blocks: *expressions*, *values*, and *types*. In this section, we explore these concepts.

Here's a very simple expression:

```scala mdoc:silent
1 + 2
```

An *expression* is a fragment of Scala code. We can write expressions in a text editor, or on a piece of paper, or on a wall; expressions are like writing. Just like writing must be read for it to have any effect on the world (and the reader has to understand the language the writing is written in), the computer must *run* an expression for it to have an effect. The result of running an expression is a *value*. Values live in the computer's memory, in the same way that the result of reading some writing lives in the reader's head. We will also say expressions are *evaluated* or *executed* to describe the process of transforming them into values.

We can evaluate expressions immediately by writing them at the console and pressing "Enter" (or "Return"). Try it now.

```scala mdoc
1 + 2
```

The console responds with the value the expression evaluates to, and the type of the expression.

The expression `1 + 2` evaluates to the value `3`. We can write down the number three here on the page, but the real value is something stored in the computer's memory. In this case, it is a 32-bit integer represented in two's-complement. The meaning of "32-bit integer represented in two's-complement" is not important. We just mention it to emphasize the fact the computer's representation of the value `3` is the true value, not the numeral written here or displayed by the console.

*Types* are the final piece of the puzzle. A type describes a set of values. The expression `1 + 2` has the type `Int`, which means the value the expression evaluates to will be one of the over four billion values the computer understands to be integers. We determine the type of an expression *without* running it, which is why the type `Int` doesn't tell us which specific value the expression evalutes to.

Before a Scala program is run, it must be *compiled*. Compilation checks that a program makes sense. It must be syntactically correct, meaning it must be written according to the rules of Scala. For example `(1 + 2)` is syntactically correct, but `(1 + 2` is not because there is no `)` to match the `(`. It must also *type check*, meaning the types must be correct for the operations we're trying to do. `1 + 2` type checks (we are adding integers), but `1.toUpperCase` does not (there is no concept of upper and lower case for integers.)

Only programs that successfully compile can be run. We can think of compilation as being analogous to the rules of grammar in writing. The sentence "FaRf  fjrmn;l df.fd"
is syntactically incorrect in English. The arrangement of letters doesn't form any words. The sentence "dog fly a here no" is made out of valid words but their arrangement breaks the rules of grammar---analogous to the type checks that Scala performs.

It is important to remember that type checking is done before a program runs. If you have used a language like Python or Javascript, which are sometimes called "dynamically typed", there is no type checking done before a program runs. In a "statically typed" language like Scala the type checking catches some potential errors for us before we run the code. What is sometimes called a type in a dynamically typed language is *not* a type as we understand it. Types, for us, exist at at the time when a program is compiled, which we will call *compile time*. At time when a program runs, which we call *run time*, we have only values. Values may record some information about the type of the expression that created them. If they do we call these *tags*, or sometimes *boxes*. Not all values are tagged or boxed. Avoiding tagging, which is also called *type erasure*, allows for more efficient programs.
