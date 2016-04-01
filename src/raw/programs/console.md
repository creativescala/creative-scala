## Working Within the Console

```tut:invisible
import doodle.core._
import doodle.core.Image._
import doodle.syntax._
import doodle.jvm.Java2DCanvas._
import doodle.backend.StandardInterpreter._
```

Whether you're using a text editor or an IDE, you should have no problem saving code to a file. If you're working from Doodle you can save your code in the directory `shared/src/main/scala/doodle/examples`, where we have already created a number of examples.

How do we use code that you've saved to a file from the console? There are a number of special commands that only work in the console, all beginning with a colon. We've seen `:type` already. The command to run a file is called `:paste`[^load]. We follow `:paste` with the name of the file we want to run. For example, if we save as `shared/src/main/scala/doodle/examples/Example.scala` the expression

```tut:silent
circle(100) fillColor Color.paleGoldenrod lineColor Color.indianRed
```

we can then run this code by writing at the console

```scala
:paste shared/src/main/scala/doodle/examples/Tree.scala
// res0: doodle.core.Image = ContextTransform(<function1>,ContextTransform(<function1>,Circle(100.0)))
```

Note the value has been given the name `res0` in the example above. If you're following along, the name in your console might end with a different number depending on what you've already typed into the console. We can draw the image by evaluating `res0.draw` (or the correct name for your console).

### Tips for Using the Console

Here are a few tips for using the console more productively:

- If you press the up arrow you'll get the last thing you typed into the console. Handy to avoid having to type in those long file names over and over again! You can press up multiple times to go through the history of your interactions at the console.

- You can press the `Tab` key to get the console to suggest completions for code, but unfortunately not file names, you're typing. For example, if you type `Stri` and then press `Tab`, the console will show possible completions. Type `Strin` and the console will complete `String` for you.

[^load]: There is also a command called `:load` which works in a slightly different way to `:paste`. It compiles and runs each line in the file on its own, while `:paste` compiles and runs the whole file in one go. They have subtly different semantics. The way `:paste` works is closer to how Scala code works outside the console, so we'll use it in preference to `:load`.

<div class="callout callout-warn">
Once we start saving code to a file, we'll likely find the compiler doesn't like our code next time we start SBT. Read the next section to see how we can fix this problem.
</div>
