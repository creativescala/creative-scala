# Conclusions

This chapter has focused on function composition.
We've used `Angle => Point` functions to represent parametric curves,
and we have seen that we can build many useful abstractions based on function composition.

Functions are not the only things that compose. 
Every time we've used the `beside`, or `above` methods on `Image`, for example, we've been composing images.
Composition is one of the key ideas in functional programming, and we'll continue to see it throughout the book.
These methods are sometimes known as **combinators**.
This isn't a term we'll use much, but you may find it in other literature so it's useful to know what it means.

The methods of composition, or combinators, all have a property known as **closure**.
This means that the input is the same sort of thing as the output.
When we combine two `Images` using `above` we get back an `Image`.
When we combine two functions using `andThen` we get back a function.

It isn't necessary for all the parameters of a method to have the same type for a method to be a combinator.
For example, `strokeColor` combiines an `Image` and a `Color` to produce an `Image`. 
We still consider this a combinator because we're most interested in the `Image` type, which is both an input and an output of `strokeColor`.
So we could say that `strokeColor` composes an `Image` and a `Color` to produce an `Image`.
