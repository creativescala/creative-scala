## Methods

```scala mdoc:invisible
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._
```

In a previous chapter we created the image shown in @:fref(methods:sequential-boxes) using the program

@:figure{ img = "./src/pages/programs/sequential-boxes.pdf+svg", key = "#fig:methods:sequential-boxes", caption = "Five boxes filled with Royal Blue" }

```scala mdoc:silent
val box =
  Image.rectangle(40, 40).
    strokeWidth(5.0).
    strokeColor(Color.royalBlue.spin(30.degrees)).
    fillColor(Color.royalBlue)

box beside box beside box beside box beside box
```

Imagine we wanted to change the color of the boxes.
Right now we would have to write out the expression again for each different choice of color.

```scala mdoc:silent
val paleGoldenrod = {
  val box =
    Image.rectangle(40, 40).
      strokeWidth(5.0).
      strokeColor(Color.paleGoldenrod.spin(30.degrees)).
      fillColor(Color.paleGoldenrod)

  box beside box beside box beside box beside box
}

val lightSteelBlue = {
  val box =
    Image.rectangle(40, 40).
      strokeWidth(5.0).
      strokeColor(Color.lightSteelBlue.spin(30.degrees)).
      fillColor(Color.lightSteelBlue)

  box beside box beside box beside box beside box
}

val mistyRose = {
  val box =
    Image.rectangle(40, 40).
      strokeWidth(5.0).
      strokeColor(Color.mistyRose.spin(30.degrees)).
      fillColor(Color.mistyRose)

  box beside box beside box beside box beside box
}
```

This is tedious.
Each expression only differs in a minor way.
It would be nice if we could capture the general pattern and allow the color to vary.
We can do exactly this by declaring a method.

```scala mdoc:silent
def boxes(color: Color): Image = {
  val box =
    Image.rectangle(40, 40).
      strokeWidth(5.0).
      strokeColor(color.spin(30.degrees)).
      fillColor(color)

  box beside box beside box beside box beside box
}

// Create boxes with different colors
boxes(Color.paleGoldenrod)
boxes(Color.lightSteelBlue)
boxes(Color.mistyRose)
```

Try this yourself to see that you get the same result using the method as you did writing everything out by hand.

Now that we've seen an example of declaring a method, we need to explain the syntax of methods. Next, we'll look at how to write methods, the semantics of method calls, and how they work in terms of substitution.
