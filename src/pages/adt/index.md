# Algebraic Data Types To Call Our Own

In this chapter we'll learn how to create our own algebraic data types, and bring together all the tools we've seen far.

So far in Creative Scala we've used (algebraic) data types provided by Scala or Doodle, such as `List` and `Point`. In this section we'll learn how to create our own algebraic data types in Scala, opening up new possibilities for the type of programs we can write.

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

