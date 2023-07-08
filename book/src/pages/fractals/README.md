# Fractals

A fractal is an image that is *self-similar*, meaning that it contains copies of itself. Fractals are an intriguing type of image as they build complex output from simple rules. In this chapter we will build some simple fractals, get more experience with structural recursion over natural numbers, and finally learn more programming techniques.

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
