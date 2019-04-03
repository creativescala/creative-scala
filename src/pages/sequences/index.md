# Shapes, Sequences, and Stars

In this chapter we will learn how to build our own shapes out of the primitve lines and curves that make up the triangles, rectangles, and circles we've used so far. 
In doing so we'll learn how to represent sequences of data, and manipulate such sequences using higher-order functions that abstract over structural recursion. 
That's quite a lot of jargon, but we hope you'll see it's not as difficult as it sounds!

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
