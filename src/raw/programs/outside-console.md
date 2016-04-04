## Coding Outside the Console

```tut:invisible
import doodle.core._
import doodle.core.Image._
import doodle.syntax._
import doodle.jvm.Java2DCanvas._
import doodle.backend.StandardInterpreter._
```

The code we've been writing inside the console will cause problems running outside the console. For example, put the following code into `Example.scala` in the Doodle examples directory. 

```tut:silent
circle(100) fillColor Color.paleGoldenrod lineColor Color.indianRed
```

Now restart SBT and try to enter the console. You should see an error similar to

```bash
[error] doodle/shared/src/main/scala/doodle/examples/Example.scala:1: expected class or object definition
[error] circle(100) fillColor Color.paleGoldenrod lineColor Color.indianRed
[error] ^
[error] one error found
```

You'll see something similar if you're using an IDE.

The problem is this:

- Scala is attempting to compile all our code before the console starts; and
- there are restrictions on code written in files that don't apply to code written directly in the console.

We need to know about these restrictions and change how we write code in files accordingly.

The error message gives us some hint: `expected class or object definition`. We don't yet know what a class is, but we do know about objects---all values are objects. In Scala all code in a file must be written inside an object or class. We can easily define an object by wrapping an expression like the below.

```tut:book
object Example {
  (circle(100) fillColor Color.paleGoldenrod lineColor Color.indianRed).draw
}
```

Now the code won't compile for a different reason. I get a lot of errors similar to

```bash
[error] doodle/shared/src/main/scala/doodle/examples/Example.scala:2: not found: value circle
[error]   (circle(100) fillColor Color.paleGoldenrod lineColor Color.indianRed).draw
[error]    ^
```

### TODO

Introduce names, `val`, and imports.
