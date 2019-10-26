## Working Within the Console

```scala mdoc:invisible
import doodle.core._
import doodle.image._
import doodle.image.syntax._
import doodle.image.syntax.core._
import doodle.java2d._
```

Your text editor or IDE will allow you to save code to a file, but we need to save files in the right place so the Scala compiler can find them.
If you're working from the Doodle template you should save your code in the directory `src/main/scala/`.

How do we use code that we saved to a file from the console? 
There is a special command, that only works from the console, that allows us to run code saved in a file. 
This command is called `:paste`[^load]. We follow `:paste` with the name of the file we want to run. For example, if we save in the file `src/main/scala/Example.scala` the expression

```scala mdoc:silent
Image.circle(100).fillColor(Color.paleGoldenrod).strokeColor(Color.indianRed)
```

we can then run this code by writing at the console

```scala
:paste src/main/scala/Example.scala
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
