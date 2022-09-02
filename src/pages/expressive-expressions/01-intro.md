# Introduction

So far we have seen expressions using the `Int` type. We will need quite a few more types, and a lot more expressions, to create the kind of expressive programs we're aiming for. 

In this chapter we will see how to write more complex expressions, and introduce some more of Scala's basic types. We'll then look at the `Image` type, provided by the Doodle library, which allows us to create pictures. We'll focus our attention on computing with pictures, and later with animations, for a little while. Pictures offer us immediate creative opportunities, and they make our program output tangible in a way that other approaches can't deliver. 

<div class="callout callout-info">
If you run the examples from the SBT console within Doodle they will just work. If not, you will need to start your code with the following imports to make Doodle available.

```scala mdoc
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._
```
</div>
