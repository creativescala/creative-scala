# Writing Larger Programs

We're getting to the point where it's inconvenient to type programs into the console. In this section we'll learn how to work with code in a file, and how to setup a project to run our code without relying on Doodle.

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

