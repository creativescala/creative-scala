# Composition of Generative Art

In this chapter we'll explore techniques from generative art, which will in turn allow us to explore key concepts for functional programming. We'll see:

- uses for `map` and `flatMap` that go beyond manipulating collections of data that we've seen in the previous chapters; 
- how we can handle side effects without breaking substitution; and
- some interesting, and possibly beautiful, images that combine elements of structure and randomness.

![An example image generated using the techniques in this chapter](./src/pages/generative/volcano.png){#fig:generative:volcano}

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

