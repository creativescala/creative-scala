# Expressions, Values, and Types

Scala programs have three fundamental building blocks: *expressions*, *values*, and *types*. In this section, we explore these concepts.

Here's a very simple expression:

```tut:silent:book
1 + 2
```

An *expression* is a fragment of Scala code. We can write expressions in an a text editor, or on a piece of paper, or on a wall. You get the idea.

Expressions are like writing. Just like writing must be read for it to have any effect on the world (and the reader has to understand the language the writing is written in), the computer must *run* an expression for it to have an effect. The result of running an expression is a *value*. Value's live in the computer's memory, in the same way that the result of reading some writing lives in the reader's head. We will also say expressions are *evaluated* or *executed* to describe the process of transforming them into values.

We can evaluate expressions immediately by writing them at the console and pressing "Enter" (or "Return"). Try it now.

```tut:book
1 + 2
```

The console responds with the value the expression evaluates to, and the type of the expression.

The expression `1 + 2` evaluates to the value `3`. We can write down the number three here on the page, but the real value is something stored in the computer's memory. In this case, it is a 32-bit integer represented in two's-complement. The meaning of "32-bit integer represented in two's-complement" is not important. I just mention it to emphasize the fact the computer's representation of the value `3` is the true value, not the numeral written here or by the console.

*Types* are the final piece of the puzzle. A type is anything we can determine about a program *without* running it. The expression `1 + 2` has the type `Int`, which means we should interpret the value the expression evaluates to as an integer. This also means we can write further expressions with the result of this expression, but these expressions must be operations that make sense for integers. We could add, subtract, multiply, or divide, but we couldn't convert an integer to lowercase, for example.

Types will often tell us how we should understand the value (the "stuff" in the computer's memory) that an expression evaluates to. Should we understand it as an integer or as a stream of points giving the current position of the mouse? The types will tell us. We can use types for other things, including things that don't have any representation at run time. These uses are a bit more advanced than we'll get into here, but don't make the mistake of thinking that types correspond to value. Types only exist at compile time in Scala. There is not necessarily any representation at run time of the type of the expression that produced a given a value.

Before a Scala program is run, it must be *compiled*. Compilation checks that a program makes sense. It must be syntactically correct, meaning it must be written according to the rules of Scala. For example `(1 + 2)` is syntactically correct, but `(1 + 2` is not. It must also *type checked*, meaning the types must be correct for the operations we're trying to do. `1 + 2` type checks (we are adding integers), but `1.toUpperCase` does not (there is no concept of upper and lower case for numbers.)

Only programs that successfully compile can be run. We can think of compilation as being analogous to the rules of grammar in writing. The sentence "F$Rf  fjrmn;l df.fd"
is syntactically incorrect in English. The arrangement of letters doesn't form any words. The sentence "dog fly a here no" is made out of valid words but their arrangement breaks the rules of grammar---analogous to the type checks that Scala performs.

We will talk about *compile time* as the time when a code is compiled, and *run time* as the time when the code is run.
