## Nested Methods

```tut:invisible
import doodle.core._
import doodle.core.Image._
import doodle.syntax._
import doodle.jvm.Java2DCanvas._
import doodle.backend.StandardInterpreter._
val aBox = Image.rectangle(20, 20).fillColor(Color.royalBlue)
```

A method is a declaration.
The body of a method can contain declarations and expressions.
Therefore, a method declaration can contiain other method declarations.

To see why this is useful.

```tut:book
def cross(count: Int): Image = {
  val unit = Image.circle(20)
  count match {
    case 0 => unit
    case n => unit beside (unit above cross(n-1) above unit) beside unit
  }
}
```
