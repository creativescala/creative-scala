## Exercises

```scala mdoc:invisible
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._
```

We now have a number of new tools in our toolbox. It's time to get some practice putting them all together.

Here's an example of the familiar chessboard pattern. We have used an auxillary parameter to pass along a color that we change at each recursion. By changing the hue by a prime number we end up a complex pattern with infrequently repeating colors. See [@fig:fractals:chessboard-2] for an example.

```scala mdoc
def chessboard(count: Int, color: Color): Image =
  count match {
    case 0 =>
      val contrast = color.spin(180.degrees)
      val box = Image.square(20)
      box
        .fillColor(color)
        .beside(box.fillColor(contrast))
        .above(box.fillColor(contrast).beside(box.fillColor(color)))

    case n =>
      chessboard(n - 1, color.spin(17.degrees))
        .beside(chessboard(n - 1, color.spin(-7.degrees)))
        .above(
          chessboard(n - 1, color.spin(-19.degrees))
            .beside(chessboard(n - 1, color.spin(3.degrees)))
        )
  }
```

![Chessboard with colors evolving at each recursive step.  ](./src/pages/fractals/chessboard-2.pdf+svg){#fig:fractals:chessboard-2}


Your mission is to take the ideas we've seen in this chapter, perhaps using the chessboard example for inspiration, and create your own artwork. No other guidelines this time; it's up to you and your imagination.
