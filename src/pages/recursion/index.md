# Structural Recursion

In this chapter we see our first major pattern for structuring computations: *structural recursion over the natural numbers*. That's quite a mouthful, so let's break it down:

- By a pattern, we mean a way of writing code that is useful in lots of different contexts. We'll encounter structural recursion in many different situations throughout this book. 

- By the natural numbers we mean the whole numbers 0, 1, 2, and upwards. 

- By recursion we mean something that refers to itself. Structural recursion means a recursion that follows the structure of the data it is processing. If the data is recursive (refers to itself) then the structural recursion will also refer to itself. We'll see in more detail what this means in a moment.

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

