## Methods

```tut:invisible
import doodle.core._
import doodle.core.Image._
import doodle.syntax._
import doodle.jvm.Java2DFrame._
import doodle.backend.StandardInterpreter._
```

In a previous chapter we created the image shown in [@fig:methods:sequential-boxes] using the program

![Six boxes filled with Royal Blue](./src/pages/programs/sequential-boxes.pdf+svg){#fig:methods:sequential-boxes}

```tut:silent:book
val box =
  Image.rectangle(40, 40).
    lineWidth(5.0).
    lineColor(Color.royalBlue.spin(30.degrees)).
    fillColor(Color.royalBlue) 

box beside box beside box beside box beside box
```

Imagine we wanted to change the color of the boxes.
Right now we would have to write out the expression again for each different choice of color.

```tut:silent:book
val paleGoldenrod = {
  val box = 
    Image.rectangle(40, 40).
      lineWidth(5.0).
      lineColor(Color.paleGoldenrod.spin(30.degrees)).
      fillColor(Color.paleGoldenrod) 

  box beside box beside box beside box beside box
}

val lightSteelBlue = {
  val box =
    Image.rectangle(40, 40).
      lineWidth(5.0).
      lineColor(Color.lightSteelBlue.spin(30.degrees)).
      fillColor(Color.lightSteelBlue) 

  box beside box beside box beside box beside box
}

val mistyRose = {
  val box =
    Image.rectangle(40, 40).
      lineWidth(5.0).
      lineColor(Color.mistyRose.spin(30.degrees)).
      fillColor(Color.mistyRose) 

  box beside box beside box beside box beside box
}
```

This is tedious.
Each expression only differs in a minor way.
It would be nice if we could capture the general pattern and allow the color to vary.
We can do exactly this with by declaring a method.

```tut:silent:book
def boxes(color: Color): Image = {
  val box =
    Image.rectangle(40, 40).
      lineWidth(5.0).
      lineColor(color.spin(30.degrees)).
      fillColor(color) 

  box beside box beside box beside box beside box
}

// Create boxes with different colors
boxes(Color.paleGoldenrod)
boxes(Color.lightSteelBlue)
boxes(Color.mistyRose)
```

Try this yourself to see that you get the same result using the method as you did writing everything out by hand.

Now we've seen an example of declaring a method we need to explain the syntax of methods---what we write---and the semantics of method calls---how they work in terms of substitution.
