# Structural Recursion over the Natural Numbers

In this chapter we see our first major programming strategy for structuring computations: *structural recursion over the natural numbers*. That's quite a mouthful, so let's break it down:

- By a programming strategy, we mean a way of writing code that is useful in lots of different contexts. We'll encounter structural recursion in many different situations throughout this book. 

- By the natural numbers we mean the whole numbers 0, 1, 2, and upwards. 

- By recursion we mean something that refers to itself. 

Structural recursion means a recursion that follows the structure of the data it is processing. If the data is recursive (refers to itself) then the structural recursion will also refer to itself. We'll see in more detail what this means in a moment.

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

