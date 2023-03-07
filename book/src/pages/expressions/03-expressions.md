## Expressions and Values

Expressions are the text that describes programs, and values are the result of running expressions. For example, we've already seen the expression

```scala mdoc:silent
1 + 2
```

Evaluating or running the expression is the name for the process of "working out" the result. The result itself we call a value. In this case the value is 3.

Being a bit more formal, we can define expressions, evaluation, and values as follows:

- *evaluation*, *execution*, or just *running* code is the process of carrying out the instructions in the program;
- *values* are the result in the computer's memory from evaluating code; and
- *expressions* are those parts of programs that evaluate to values. 

The vast majority of our programs will be expressions but there are some parts that are not. 

We should understand that expressions and values are very different things. We can write the expression `1 + 2`, but this is just text. We can write it in a book, on a wall, or in a computer file. The resulting value, `3`, is something that lives solely in the computer's memory. Specifically it's a 32-bit two's complement integer. This jargon is not something you should understand; it's here to emphasize that the value is something inside the computer. When we write the value is `3` it's not literally the computer's representation, but a representation that we understand as having the same meaning as what's inside the computer.

To help you understand this distinction we can make an exact correspondence to reading and writing:

- writing is exactly the same as an expression: it's just text;
- reading is the process that gives meaning to writing, which is the same as evaluation giving meaning to expressions; and
- the understanding in the reader's head is exactly the same as the value in the computer's memory: it's the result of evaluation.

Now we've looked at expressions and values, let's talk about types.
