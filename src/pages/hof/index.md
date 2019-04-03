# Horticulture and Higher-order Functions

In this chapter we're going to learn how to draw flowers and to use functions as first-class values.

We know that programs work with values, but not all values are *first-class*. A first-class value is something we can pass as a parameter to a method or return as a result from a method call.

If we pass a function as an argument to another function then:

- the function that is passed is being used as a first-class value; and
- the function that is receiving the function parameter is called a *higher-order function*.

This terminology is not especially important, but you'll encounter it in other writing so it's useful to know (at least vaguely) what it means.
It will soon become clearer when we see some examples.

So far we have used the terms *function* and *method* interchangeably. 
We'll soon see that in Scala these two terms have distinct, though related, meanings.

Enough background. Let's dive in to see:

- how we create functions in Scala; and
- how we use first-class functions to structure programs.

Our motivating example for this will be drawing flowers as in [@fig:hof:flower-power].

![A flower created using the techniques in this chapter](src/pages/hof/flower-power.pdf+svg){#fig:hof:flower-power}

<div class="callout callout-info">
If you run the examples from the SBT console within Doodle they will just work. If not, you will need to start your code with the following imports to make Doodle available.

```scala mdoc:silent
import doodle.core._
import doodle.core.Image._
import doodle.syntax._
import doodle.jvm.Java2DFrame._
import doodle.backend.StandardInterpreter._
```
</div>
