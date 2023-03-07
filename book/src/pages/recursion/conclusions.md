## Conclusions

In this chapter we've seen our first big pattern for structuring code, *structural recursion over the natural numbers*. There are a few key points.

First is the structural recursion pattern itself. We saw we can use this to write methods that produce a value with a varying size, and *the pattern is the same everytime.* We've seen a lot of examples that generate images, but we can use this pattern for anything that is transforming a natural number into anything else (including other natural numbers). We'll use this pattern, and other variants of structural recursion, throughout the book.

The second big idea is how we reason about structural recursion. We can use substitution but it is easier to take a shortcut. For structural recursion we are guaranteed to get the correct result if we get the base case and the recursive case correct. In particular we don't need to reason through the recursive call; we assume it returns the correct result and only check that we correctly implement the next step adding to the result.

The third key point is that we can use the value of the counter to do other things beyond the recursion. We looked at using it to adjust the images we created at each step, which gives us new creative possibilities.

In the next section we'll look at creating more complex images using structural recursion, and see a few new techniques we can use with it.
