# Methods

We've already used methods---methods are the way we interact with objects.
In this chapter we'll learn how to write our own methods.

Names allow us to abstract over expressions.
Methods allow us to abstract over and *generalise* expressions.
By generalisation we mean the ability to express a group of related things, in this case expressions.
A method captures a template for an expression, and allows the caller to fill in parts of that template by passing the method parameters.

<div class="callout callout-info">
If you run the examples from the SBT console within Doodle they will just work. If not, you will need to start your code with the following imports to make Doodle available.

```tut:silent
import doodle.core._
import doodle.core.Image._
import doodle.syntax._
import doodle.jvm.Java2DCanvas._
import doodle.backend.StandardInterpreter._
```
</div>


