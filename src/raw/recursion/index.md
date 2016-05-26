# Structural Recursion

In this chapter we see our first major pattern for structuring computations: *structural recursion over the natural numbers*. That's quite a mouthful, so let's break it down:

- By a pattern, we mean a way of writing code that is useful in lots of different contexts. We'll encounter structural recursion in many different situations throughout this book. 

- By the natural numbers we simply mean the integers 0, 1, 2, and so on. 

- By recursion we mean something that refers to itself. Structural recursion means a recursion that follows the structure of the data it is processing. We'll see in more detail what this means right now.

## The Natural Numbers

The natural numbers are the whole numbers or integers greater than or equal to zero. In other words the numbers 0, 1, 2, 3, ... (Some people define the natural numbers as starting at 1, not 0. It doesn't greatly matter for our purposes which definition you choose, but here we'll assume they start at 0.)

One interesting property of the natural numbers is that we can define them recursively. That is, we can define them in terms of themselves. This kind of circular definition seems like it would lead to nonsense. We avoid this by including in the definition a *base case* that ends the recursion. Concretely, the definition is:

A natural number `n` is

- 0; or
- 1 + `m`, where `m` is a natural number.

The case for 0 is the base case, whilst the other case is recursive.

Given a natural number, say, 3, we can break it down using the definition above as follows:

3 = 1 + 2 = 1 + (1 + 1) = 1 + (1 + (1 + 0))

We use the recursive rule to expand the equation, until we finally cannot use it any more and instead use the base case and stop.


## Structural Recursion

Now onto structural recursion. Structural recursion gives us two things:

- a reusable code skeleton for processing any natural number; and
- the guarantee that we can use this skeleton to implement *any* computation on natural numbers.

Let's start by creating a method to a sequence of boxes like [@fig:recursion:sequential-boxes]. We know how to do this if we want a fixed number of boxes. What if we want to be able to specify the number of boxes?

![Five boxes filled with Royal Blue](./src/raw/recursion/sequential-boxes.pdf){#fig:recursion:sequential-boxes}

If we want to draw zero boxes, we should draw `Image.empty` (the image that takes up no space.)
