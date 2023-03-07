## Easing Functions

```scala mdoc:invisible
import doodle.core._
import doodle.image._
import doodle.image.syntax._
import doodle.image.syntax.core._
import doodle.java2d._
import doodle.reactor._
```

Take a look at the following animation.

```scala mdoc
val bubble =
  Reactor.linearRamp(0, 200)
    .withRender(r => Image.circle(r))
```
