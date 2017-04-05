## Exercises

```tut:invisible
import doodle.core._
import doodle.core.Image._
import doodle.syntax._
import doodle.jvm.Java2DCanvas._
import doodle.backend.StandardInterpreter._
import doodle.turtle._
import doodle.turtle.Instruction._
```

### Flat Polygon

Using the Turtle methods, `Range`, and `flatMap`, rewrite your method to create a polygon. The signature of `polygon` is

```tut:silent:book
def polygon(sides: Int, sideLength: Double): Image = 
  ???
```

<div class="solution">
Using `flatMap` we can make the code more compact than the explicit structural recursion we had to use before.

```tut:silent:book
def polygon(sides: Int, sideLength: Double): Image = {
  val rotation = Angle.one / sides
  
  Turtle.draw((1 to sides).toList.flatMap { n =>
    List(turn(rotation), forward(sideLength))
  })
}
```
</div>


### Flat Spiral

Using the Turtle methods, `Range`, and `flatMap`, rewrite your method to create the square spiral. The signature of `squareSpiral` is

```tut:silent:book
def squareSpiral(steps: Int, distance: Double, angle: Angle, increment: Double): Image =
  ???
```

<div class="solution">
Again, the result is more compact than the previous implementation without `flatMap`. Isthis easier to read? I find it about the same. I belive comprehensibility is a function of familiarity, and we're (hopefully) by now becoming familiar with `flatMap`.

```tut:silent:book
def squareSpiral(steps: Int, distance: Double, angle: Angle, increment: Double): Image = {
  Turtle.draw((1 to steps).toList.flatMap { n =>
   List(forward(distance + (n * increment)), turn(angle)) 
  })
}
```
</div>


### L-System Art

In this exercise we want you to use your creativity to construct a picture of a natural object using your L-system implementation. You've seen many examples already that you can use an inspriation. 
