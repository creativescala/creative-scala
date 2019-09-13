## Controlling the Turtle

```scala mdoc:invisible
import doodle.core._
import doodle.image._
import doodle.image.syntax._
import doodle.image.syntax.core._
import doodle.java2d._
```

Let's look over the turtle graphics API, and use it to draw a few different images.


### Instructions

We control the turtle by giving it instructions. 
These instructions are defined as methods on the object `doodle.turtle.Instruction` (similarly to the methods on `doodle.core.Image` that create images).

We can import the methods and then create instructions.

```scala mdoc:silent
import doodle.turtle._
import doodle.turtle.Instruction._
```
```scala mdoc
forward(10)
turn(5.degrees)
```

This doesn't do anything useful unless we assemble these commands into an image. 
To do so, we create a list of instructions and then ask the turtle (`doodle.turtle.Turtle` to be exact) to draw them to an `Image`.

```scala mdoc:silent
val instructions = 
  List(forward(10), turn(90.degrees), 
       forward(10), turn(90.degrees), 
       forward(10), turn(90.degrees), 
       forward(10))

val path = Turtle.draw(instructions)
```

This creates a path---an `Image`---which we can then `draw` in the usual way.
This gives the output shown in [@fig:turtles:square]. 
This is not a very exciting image, but we can change color, line width, and so on to create more interesting results.

![A square created via the turtle graphics system.](src/pages/turtles/square.pdf+svg){#fig:turtles:square}

The complete list of turtle instructions in given in [@tbl:turtles:instructions]

---------------------------------------------------------------------------------------------
Instruction                Description                         Example
-------------------------- ----------------------------------- --------------------------------
`forward(distance)`        Move forward the given `distance`,  `forward(100.0)`
                           specified as a `Double`.

`turn(angle)`              Turn the given `angle` (an `Angle`) `turn(10.degrees)`
                           from the current heading.

`branch(instruction, ...)` Save the current position and       `branch(turn(10.degrees), forward(10))`
                           heading, draw the given
                           `instructions` , and then return to
                           the saved position to draw the rest
                           of the instructions.
                                  
`noop`                     Do nothing!                         `noop`
---------------------------------------------------------------------------------------------

: The instructions understood by the turtle. {#tbl:turtles:instructions}

### Exercises {-}

#### Polygons {-}

In the previous chapter we wrote a method to create a polygon. Reimplement this method using turtle graphics instead. The method header should be something like

```scala
def polygon(sides: Int, sideLength: Double): Image =
 ???
```

You'll have to do a bit of geometry to work out the correct turn angle, but as that's half the fun we won't spoil it for you.

<div class="solution">
Here's our solution. It's a structural recursion over the natural numbers. The turn angle is exactly the same as the rotation angle used to create polygons in polar coordinates in the previous chapter, though the derivation is quite different.

```scala mdoc:silent
def polygon(sides: Int, sideLength: Double): Image = {
  val rotation = Angle.one / sides
  def iter(n: Int): List[Instruction] =
    n match {
      case 0 => Nil
      case n => turn(rotation) :: forward(sideLength) :: iter(n-1)
    }

  Turtle.draw(iter(sides))
}
```
</div>


#### The Square Spiral

The square spiral is shown in [@fig:turtles:square-spiral]. Write a method to create square spirals using turtle graphics.

This task requires a bit more design work than we usually ask of you. You'll have to work out how the square spiral is constructed (hint: it starts at the center) and then create a method to draw one.

![The square spiral!](src/pages/turtles/square-spiral.pdf+svg){#fig:turtles:square-spiral}

<div class="solution">
The key insights to draw the square spiral are realising:

- each turn is a little bit less than 90 degrees
- each step forward is a little bit longer than the last one

Once we have this understood this, the structure is basically the same as drawing a polyon. Here's our solution.

```scala mdoc
def squareSpiral(steps: Int, distance: Double, angle: Angle, increment: Double): Image = {
  def iter(n: Int, distance: Double): List[Instruction] = {
    n match {
      case 0 => Nil
      case n => forward(distance) :: turn(angle) :: iter(steps-1, distance + increment)
    }
  }

  Turtle.draw(iter(steps, distance))
}
```
</div>

#### Turtles vs Polar Coordinates {-}

We can create polygons in polar coordinates using a `Range` and `map` as shown below.

```scala mdoc:silent
import doodle.core.Point._

def polygon(sides: Int, size: Int): Image = {
  val rotation = Angle.one / sides
  val elts =
    (1 to sides).toList.map { i =>
      PathElement.lineTo(polar(size, rotation * i))
    }
  Image.closedPath(PathElement.moveTo(polar(size, Angle.zero)) :: elts)
}
```

We cannot so easily write the same method to generate turtle instructions using a `Range` and `map`. Why is this? What abstraction are we missing?

<div class="solution">
Each side of the polygon requires two turtle instructions: a `forward` and a `turn`. Thus drawing a pentagon requires ten instructions, and in general n sides requires 2n instructions.
Using `map` we cannot change the number of elements in a list. Therefore mapping `1 to n`, as we did int the code above, won't work. We could map over `1 to (n*2)`, and on, say, odd numbers move forward and on even numbers turn, but this is rather inelegant. It seems it would be simpler if we had an abstraction like `map` that allowed us to change the number of elements in the list as well as transform the individual elements.
</div>
