# Fun with Functions

```scala mdoc:invisible
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
```

In the previous section we learned about functions. I said we can write our structural recursions over natural numbers using the method `fold` below, which accepts a function as a parameter.

```scala mdoc:silent
def fold(count: Int, build: (Int, Image) => Image): Image =
  count match {
    case 0 => Image.empty
    case n => build(n, fold(count - 1, build))
  }
```

Let's see if that is really the case, and get a bit more practice using functions.

Below is an example of a row of boxes, where the color changes from each box to the next. We've already written this as a method. Now I want you to rewrite it using `fold` and a function you create.

@:doodle("gradient-boxes", "HofFun.gradientBoxesExample")

@:solution
Here's how I wrote it.

```scala mdoc:silent
val gradientBoxes: (Int, Image) => Image =
  (count, image) =>
    Image
      .square(50)
      .fillColor(Color.royalBlue.spin(10.degrees * count))
      .noStroke
      .beside(image)
```
```scala
fold(5, gradientBoxes)
```

If you struggled to come up with this look at the version written as a method.

```scala mdoc:silent
def gradientBoxes(count: Int): Image =
  count match {
    case 0 => Image.empty
    case n => 
      Image
        .square(50)
        .fillColor(Color.royalBlue.spin(10.degrees * count))
        .noStroke
        .beside(gradientBoxes(count-1))
  }
```

All we're doing is extracting the problem specific part in the recursive case

```scala
case n => 
  Image
    .square(50)
    .fillColor(Color.royalBlue.spin(10.degrees * count))
    .noStroke
    .beside(gradientBoxes(count-1))
```

and turning it into a function.
@:@

Here's a variation on that idea, which changes size as well as changing color. Write this using `fold` and a function of your own construction.

@:doodle("growing-circles", "HofFun.growingCirclesExample")

@:solution
Here's what I wrote. It's the same idea as the previous example, with a small modification to change the size of the element as well as the color.

```scala mdoc:silent
val growingCircles: (Int, Image) => Image =
  (count, image) =>
    Image
      .circle(20 * count)
      .fillColor(Color.crimson.spin(10.degrees * count))
      .noStroke
      .beside(image)
```

```scala
fold(5, growingCircles)
```
@:@

Let's try a fractal. Below is the Sierpinski triangle. Can you write this using `fold`? If not, why not? Can you change `fold` so you can write it using `fold`?

@:doodle("sierpinski", "HofSierpinski.sierpinskiExample")
                        HofSierpinski.sierpinskiExample
@:solution
The way the question is worded is a strong hint that we cannot write the Sierpinski triangle using `fold` as we have currently written it. The reason is that `fold`, as currently written, always uses `Image.empty` as the base case. For the Sierpinski triangle we need a different base case. The solution is to add another parameter to `fold` that allows us to change the base case.
@:@


## A More General Fold

In the previous section we saw there are some kinds of `Images` we cannot create using `fold`. A more general fold will allow us to also vary the base case as well as the recursive case. This is simply another parameter to the method.

```scala mdoc:invisible:reset
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
```
```scala mdoc:silent
def fold(count: Int, base: Image, build: (Int, Image) => Image): Image =
  count match {
    case 0 => base
    case n => build(n, fold(count - 1, build))
  }
```

Using the version of fold we can create the Sierpinski triangle. 

```scala mdoc:silent
val sierpinski: (Int, Image) => Image =
  (count, image) => image.above(image.beside(image))

fold(
  5,
  Image.equilateralTriangle(10).strokeColor(Color.hotpink),
  sierpinski
)
```

To make a fully general fold we'd need to be able to change the result type. This requires *generic types*, which we haven't encountered yet. 
