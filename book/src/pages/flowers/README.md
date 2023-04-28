# Functions and Flowers

In this chapter we're going to learn how to draw flowers and to use functions as first-class values.
We know that programs work with values, but not all values are *first-class*. 
A first-class value is something we can pass as a parameter to a method, or return as a result from a method call, or give a name using `val`.

So far we have used the terms *function* and *method* interchangeably. 
We'll soon see that in Scala these two terms have distinct, though related, meanings.
We will learn how to define functions and function types, and then explore how passing functions to methods and returning functions from methods is useful.

Our motivating example for this will be drawing flowers. There is an example below.

@:doodle("flower-power", "FlowersFlowerPower.flowerPower")

@:callout(info)
Don't forget, you will need to start your code with the following imports for the examples to work.

```scala mdoc:silent
import cats.effect.unsafe.implicits.global
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._
```
@:@
