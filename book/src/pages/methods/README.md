# Methods without Madness

We've already used methods; methods are the way we interact with objects.
In this chapter we'll learn how to write our own methods.

Names allow us to abstract over expressions.
Methods allow us to abstract over and *generalise* expressions.
By generalisation we mean the ability to express a group of related things, in this case expressions.
A method captures a template for an expression, and allows the caller to fill in parts of that template by passing the method parameters.

@:callout(info)
You will need to start your code with the following imports to make Doodle available.

```scala mdoc:silent
import cats.effect.unsafe.implicits.global
import doodle.core.*
import doodle.image.*
import doodle.syntax.all.*
import doodle.image.syntax.all.*
import doodle.java2d.*
```
@:@


