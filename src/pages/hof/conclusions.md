## Conclusions

In this chapter we looked at functions. We saw that functions are, unlike methods *first-class values*. This means we can pass functions to methods (or other functions), return them from methods and functions, and give them a name using `val`.

We saw that, because functions are values, we can *compose* them. This means to create new functions by combining existing functions. We saw a few different ways of doing this. Function composition allowed us to build a toolbox of useful functions that we could then combine to create interesting results. Composition is one of the key ideas in functional programming. Doodle is another example of composition: we combine `Images` using methods like `on` and `above` to create new `Images`.
