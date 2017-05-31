# Writing Larger Programs

We're getting to the point where it's inconvenient to type programs into the console. 
In this chapter we'll learn about two tools for writing larger programs:

- saving programs to a file so we don't have to type code over and over again;
- giving names to values so we can reuse them.

<div class="callout callout-info">
If you run the examples from the SBT console within Doodle they will just work. If not, you will need to start your code with the following imports to make Doodle available.

```tut:silent
import doodle.core._
import doodle.core.Image._
import doodle.syntax._
import doodle.jvm.Java2DFrame._
import doodle.backend.StandardInterpreter._
```
</div>

