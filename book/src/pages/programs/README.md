# Writing Larger Programs

In this chapter we'll learn about two tools for writing larger programs:

* writing programs in code files outside of a worksheet; and
* giving names to values so we can reuse them.

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

