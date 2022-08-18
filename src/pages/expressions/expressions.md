## Expressions and Values

Expressions are the text that describes programs, and values are the result of running expressions. For example, here's an expression:

```scala mdoc:silent
1 + 2
```

This expression is simple enough that we don't need the computer to work with it. You probably immediately thought "1 + 2 is 3". Evaluating or running the expression is the name for the process of "working out" the result. The result itself we call a value, in this case 3.

Being a bit more formal, and talking specifically about code, we can define expressions, evaluation, and value as follows:

- *evaluation*, *execution*, or just *running* code is the process of carrying out the instructions in the program;
- *values* are the result in the computer's memory from evaluating code; and
- *expressions* are those parts of programs that evaluate to values. (The vast majority of our programs will be expressions but there are some parts that are not. )

We should understand that expressions and values are very different things. We can write the expression `1 + 2`, but this is just text. We can write it in a book, on a wall, or in a computer file. The resulting value, `3`, is something that lives solely in the computer's memory. Specifically it's a 32-bit two's complement integer. This jargon is not something you should understand---it's here to emphasize that the value is something inside to the computer. When we write the value is `3` it's not literally the computer's representation, but a representation that we understand as having the same meaning as what's inside the computer.

To help you understand this distinction we can make an exact analogy to reading and writing:

- writing is exactly the same as an expression: it's just text;
- reading is the process that gives meaning to writing, which is the same as evaluation giving meaning to expressions; and
- the understanding in the reader's head is exactly the same as the value in the computer's memory: it's the result of evaluation.


We can evaluate expressions immediately by writing them at the console and pressing "Enter" (or "Return"). Try it now.

```scala mdoc
1 + 2
```

The console responds with the value the expression evaluates to, and the type of the expression.

The expression `1 + 2` evaluates to the value `3`. We can write down the number three here on the page, but the real value is something stored in the computer's memory. In this case, it is a 32-bit integer represented in two's-complement. The meaning of "32-bit integer represented in two's-complement" is not important. We just mention it to emphasize the fact the computer's representation of the value `3` is the true value, not the numeral written here or displayed by the console.
