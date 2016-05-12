## Exercises 

```tut:invisible
import doodle.core._
import doodle.core.Image._
import doodle.syntax._
import doodle.jvm.Java2DCanvas._
import doodle.backend.StandardInterpreter._
```

Don't forget the following imports when you attempt these exercises.

```tut:book
import doodle.random._
import cats.syntax.cartesian._
```

### Colored Boxes

Let's warm up by returning to a problem from the beginning of the book: drawing colored boxes. This time we're going to make the gradient a little more interesting, by making each color randomly chosen.

Recall the basic structural recursion for making a row of boxes

```tut:book
def rowOfBoxes(n: Int): Image =
  n match {
    case 0 => rectangle(20, 20)
    case n => rectangle(20, 20) beside rowOfBoxes(n-1)
  }
```

Let's alter this, like with did with concentric circles, to have each box filled with a random color. *Hint:* you might find it useful to reuse some of the utilities we created for `randomConcentricCircles`. Example output is shown in [@fig:generative:random-color-boxes].

![Boxes filled with random colors.](./src/pages/generative/random-color-boxes.png){#fig:generative:random-color-boxes}

<div class="solution">
This code uses exactly the same pattern as `randomConcentricCircles`.

```tut:book
val randomAngle: Random[Angle] =
  Random.double.map(x => x.turns)

val randomColor: Random[Color] =
  randomAngle map (hue => Color.hsl(hue, 0.7.normalized, 0.7.normalized))

def coloredRectangle(color: Color): Image =
  rectangle(20, 20) fillColor color

def randomColorBoxes(n: Int): Random[Image] =
  n match {
    case 0 => randomColor map { c => coloredRectangle(c) }
    case n =>
      val box = randomColor map { c => coloredRectangle(c) }
      val boxes = randomColorBoxes(n-1)
      (box |@| boxes) map { (b, bs) => b beside bs }
  }
```
</div>


