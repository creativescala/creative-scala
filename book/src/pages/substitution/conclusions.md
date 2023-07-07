# Conclusions

In this section we've learned about the substitution model of evaluation, which is a simple model we can use to understand what our code does when it is run. 
Substitution only works for code that has no side-effects, sometimes called pure code.
Pure code can be freely substituted. 
Impure code, that is code with side-effects, cannot.

We've seen that keeping code free of side-effects allows us to use local reasoning, which is extremely powerful in large code bases.
However, real programs must have effects. 
One goal of functional programming is to control effects so that we can use local reasoning as much as possible.
