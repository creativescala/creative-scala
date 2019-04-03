## Build Your Own Turtle

```scala mdoc:invisible
import doodle.core._
import doodle.core.Image._
import doodle.syntax._
import doodle.jvm.Java2DFrame._
import doodle.backend.StandardInterpreter._
```

Here's the `Instruction` type we defined in the previous section.

```scala mdoc
sealed abstract class Instruction extends Product with Serializable
final case class Forward(distance: Double) extends Instruction
final case class Turn(angle: Angle) extends Instruction
final case class Branch(instructions: List[Instruction]) extends Instruction
final case class NoOp() extends Instruction
```

Now we've defined our own `Instruction` type, let's go one further and create our own `Turtle`. To complete our turtle we need to implement `draw`. We can start with

```scala mdoc
object Turtle {
  def draw(instructions: List[Instruction]): Image =
    ???
}
```

`Instruction` is an algebraic data type, so we know we can use structural recursion to process it. However to do so we need to also store the current state of the turtle: it's location (a `Vec`) and heading (an `Angle`). Implement a type to hold this data.

<div class="solution">
This is a product type.

```scala mdoc
final case class TurtleState(at: Vec, heading: Angle)
```
</div>

When we process the instructions, we will turn them into a `List[PathElement]`, which we can later wrap with an open path to create an `Image`. For each instruction, the conversion will be a function of the current turtle state and the instruction, and will returnan updated state and a `List[PathElement]`. 

Implement a method `process` to do this job with signature

```scala mdoc
def process(state: TurtleState, instruction: Instruction): (TurtleState, List[PathElement]) =
  ???
```

First implement this without branching instructions. We'll return to branches in a moment.

<div class="solution">
The core pattern is a structural recursion but the details are a bit more intricate in this case than we've seen before. We need to both create the path elements and update the state.

```scala mdoc:reset:invisible
import doodle.core._
import doodle.core.Image._

sealed abstract class Instruction extends Product with Serializable
final case class Forward(distance: Double) extends Instruction
final case class Turn(angle: Angle) extends Instruction
final case class Branch(instructions: List[Instruction]) extends Instruction
final case class NoOp() extends Instruction

final case class TurtleState(at: Vec, heading: Angle)
```
```scala mdoc
def process(state: TurtleState, instruction: Instruction): (TurtleState, List[PathElement]) = {
  import PathElement._
  
  instruction match {
    case Forward(d) =>
      val nowAt = state.at + Vec.polar(d, state.heading)
      val element = lineTo(nowAt.toPoint)

      (state.copy(at = nowAt), List(element))
    case Turn(a) =>
      val nowHeading = state.heading + a

      (state.copy(heading = nowHeading), List())
    case Branch(i) =>
      // Ignoring for now
      (state, List())
    case NoOp() =>
      (state, List())
  }
}
```
</div>

Now using `process` write a structural recursion over `List[Instruction]` that converts the instructions to a `List[PathElement]`. Call this method `iterate` with signature

```scala mdoc
def iterate(state: TurtleState, instructions: List[Instruction]): List[PathElement] =
  ???
```

<div class="solution">
```scala mdoc
def iterate(state: TurtleState, instructions: List[Instruction]): List[PathElement] =
  instructions match {
    case Nil => 
      Nil
    case i :: is =>
      val (newState, elements) = process(state, i)
      elements ++ iterate(newState, is)
  }
```
</div>

Now add branching to `process`, using `iterate` as a utility.

<div class="function">
```scala mdoc
def process(state: TurtleState, instruction: Instruction): (TurtleState, List[PathElement]) = {
  import PathElement._
  
  instruction match {
    case Forward(d) =>
      val nowAt = state.at + Vec.polar(d, state.heading)
      val element = lineTo(nowAt.toPoint)

      (state.copy(at = nowAt), List(element))
    case Turn(a) =>
      val nowHeading = state.heading + a

      (state.copy(heading = nowHeading), List())
    case Branch(is) =>
     val branchedElements = iterate(state, is)
     
     (state, moveTo(state.at.toPoint) :: branchedElements)
    case NoOp() =>
      (state, List())
  }
}
```
</div>

Now implement `draw` using `iterate`.

<div class="solution">
Here's the complete turtle.

```scala mdoc
object Turtle {
  def draw(instructions: List[Instruction]): Image = {
    def iterate(state: TurtleState, instructions: List[Instruction]): List[PathElement] =
      instructions match {
        case Nil => 
          Nil
        case i :: is =>
          val (newState, elements) = process(state, i)
          elements ++ iterate(newState, is)
      }

    def process(state: TurtleState, instruction: Instruction): (TurtleState, List[PathElement]) = {
      import PathElement._
      
      instruction match {
        case Forward(d) =>
          val nowAt = state.at + Vec.polar(d, state.heading)
          val element = lineTo(nowAt.toPoint)
    
          (state.copy(at = nowAt), List(element))
        case Turn(a) =>
          val nowHeading = state.heading + a
    
          (state.copy(heading = nowHeading), List())
        case Branch(is) =>
         val branchedElements = iterate(state, is)
         
         (state, moveTo(state.at.toPoint) :: branchedElements)
        case NoOp() =>
          (state, List())
      }
    }
    
    openPath(iterate(TurtleState(Vec.zero, Angle.zero), instructions))
  }
}
```
</div>


### Extensions

Turtles that can make random choices can lead to more organic images. Can you implement this?
