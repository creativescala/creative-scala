## Branching Structures

```tut:invisible
import doodle.core._
import doodle.core.Image._
import doodle.syntax._
import doodle.jvm.Java2DCanvas._
import doodle.backend.StandardInterpreter._
```

<div class="callout callout-info">
In addition to the standard imports given at the start of the chapter, in this section we're assuming the following:

```tut:silent
import doodle.turtle._
import doodle.turtle.Instruction._
```
</div>

Using the `branch` turtle instruction we can explore some shapes that have been difficult to create till this point. The `branch` instruction takes a `List[Instruction]`. It saves the current state of the turtle (it's location and heading), draws the given instructions, and returns the turtle to the saved state.

Consider the code below, which creates the image in [@fig:turtles:y]. This is easy to draw with a branching turtle, but quite involved to create with a raw path.

```tut:book
val y = Turtle.draw(List(
          forward(100),
          branch(List(turn(45.degrees), forward(100))),
          branch(List(turn(-45.degrees), forward(100))))
        )
```

![An image that is easy to create with a branching turtle, and comparatively difficult to create without.](src/raw/turtles/y.pdf+svg){#fig:turtles:y}
