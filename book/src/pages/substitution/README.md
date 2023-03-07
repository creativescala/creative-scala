# The Substitution Model of Evaluation

We need to build a mental model of how Scala expressions are evaluated so we can understand what our programs are doing.
We've been getting by with an informal model so far. 
In this section we make our model a bit more formal by learning about the *substitution model* of evaluation.
Like many things in programming we're using some fancy words for a simple concept.
In this case you've probably already learned about substitution in high school algebra, and we're just taking those ideas into a new context.

<div class="callout callout-info">
If you run the examples from the SBT console within Doodle they will just work. If not, you will need to start your code with the following imports to make Doodle available.

```scala mdoc:silent
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._
```
</div>
