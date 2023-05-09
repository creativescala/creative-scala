## Working Without Worksheets

```scala mdoc:invisible
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._
import cats.effect.unsafe.implicits.global
```

The code we've been writing inside the console will cause problems running outside the console. For example, put the following code into `Example.scala` in the `src/main/scala`.

```scala mdoc:silent
Image.circle(100).fillColor(Color.paleGoldenrod).strokeColor(Color.indianRed)
```

Now restart SBT and try to enter the console. You should see an error similar to

```bash
[error] src/main/scala/Example.scala:1: expected class or object definition
[error] circle(100) fillColor Color.paleGoldenrod strokeColor Color.indianRed
[error] ^
[error] one error found
```

You'll see something similar if you're using an IDE.

The problem is this:

- Scala is attempting to compile all our code before the console starts; and
- there are restrictions on code written in files that don't apply to code written directly in the console.

We need to know about these restrictions and change how we write code in files accordingly.

The error message gives us some hint: `expected class or object definition`. We don't yet know what a class is, but we do know about objects---all values are objects. In Scala all code in a file must be written inside an object or class. We can easily define an object by wrapping an expression like the below.

```scala mdoc:silent
object Example {
  Image.circle(100).fillColor(Color.paleGoldenrod).strokeColor(Color.indianRed).draw()
}
```

Now the code won't compile for a different reason. You should see a lot of errors similar to

```bash
[error] doodle/shared/src/main/scala/doodle/examples/Example.scala:1: not found: value Image
[error]   Image.circle(100).fillColor(Color.paleGoldenrod).strokeColor(Color.indianRed).draw()
[error]   ^
```

The compiler is saying that we've used a name, `circle`, but the compiler doesn't know what value this name refers to.
It will have a similiar issue with `Color` in the code above.
We'll talk in more details about names in just a moment.
Right now let's tell the compiler where it can find the values for these names by adding some `import` statements.
The name `Color` is found inside a *package* called `doodle.core`, and the name `circle` is within the object `Image` that is in `doodle.image`.
We can tell the compiler to use all the name in `doodle.core`, and all the names in `doodle.image` by writing

```scala mdoc:silent
import doodle.core._
import doodle.image._
```

There are a few other names that the compiler will need to find for the complete code to work.
We can import these with the lines

```scala mdoc:silent
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._
```

We should place all these imports at the top of the file, so the complete code looks like

```scala
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._

object Example {
  Image.circle(100).fillColor(Color.paleGoldenrod).strokeColor(Color.indianRed).draw()
}
```

With this in place the code should compile without issue.

Now when we go to the console within SBT we can refer to our code using the name, `Example`, that we've given it.

```scala
Example // draws the image
```

### Exercise {-}

If you haven't done so already, save the code above in the file `src/main/scala/Example.scala` and check that the code compiles and you can access it from the console.
