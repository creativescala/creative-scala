## Branching Structures

```tut:invisible
import doodle.core._
import doodle.core.Image._
import doodle.syntax._
import doodle.jvm.Java2DCanvas._
import doodle.backend.StandardInterpreter._
```

<div class="callout callout-info">
In addition to the standard imports given at the start of the chapter, in this section we're assuming the following:

```tut:silent
import doodle.turtle._
import doodle.turtle.Instruction._
```
</div>

Using the `branch` turtle instruction we can explore some shapes that are difficult to create without it. The `branch` instruction takes a `List[Instruction]`. It saves the current state of the turtle (it's location and heading), draws the given instructions, and returns the turtle to the saved state.

Consider the code below, which creates the image in [@fig:turtles:y]. This is easy to draw with a branching turtle, but quite involved to create with just a path.

```tut:book
val y = Turtle.draw(List(
          forward(100),
          branch(turn(45.degrees), forward(100)),
          branch(turn(-45.degrees), forward(100)))
        )
```

![An image that is easy to create with a branching turtle, and comparatively difficult to create without.](src/pages/turtles/y.pdf+svg){#fig:turtles:y}

Using branching we can model some forms of biological growth, producing, for example, images of plants as in [@fig:turtles:plant]. One particular model is known as an *L-system*. An L-system has consists of two parts:

- an initial *seed* to start the growth; and
- *rewrite rules*, which specify how the growth occurs.

A specific example of this process is shown in [@fig:turtles:branches]. The figure on the left hand side is the seed. The rewrite rules are:

- each straight line doubles in size; and
- a bud (the diamond at the end of a line) grows into two branches that end with buds.

![Modelling the growth of a plant using rewrite rules.](src/pages/turtles/branches.pdf+svg){#fig:turtles:branches}

Concretely, we can write these rules as a transformation on `Instruction` assuming that we use `NoOp` to represent a bud.

```tut:book
val stepSize = 10

def rule(i: Instruction): List[Instruction] =
  i match {
    case Forward(_) => List(forward(stepSize), forward(stepSize))
    case NoOp => 
      List(branch(turn(45.degrees), forward(stepSize), noop), 
           branch(turn(-45.degrees), forward(stepSize), noop))
    case other => List(other)
  }
```

Note how we used pattern matching on `Instruction`, like we have on the other algebraic data types---natural numbers and `List`---we've seen so far. By importing `doodle.turtle.Instruction._` we can access all the patterns for `Instruction`, which are

- `Forward(distance)`, where `distance` is a `Double`;
- `Turn(angle)`, where `angle` is an `Angle`;
- `NoOp`; and
- `Branch(instructions)`, where `instructions` is a `List[Instruction]`.

As a function, `rule` has type `Instruction => List[Instruction]`, as we're potentially transforming each instruction into several instructions (as we do in the case of `Forward`). Now how can we actually apply this rule to a `List[Instruction]` to create a `List[Instruction]` (for example, applying it to `List[noop]`)? Can we use `map`?

<div class="solution">
In this case `map` is not the right solution, as the types tell us. Remember the type equation for `map` is

```scala
List[A] map (A => B) = List[B]
```

If
- we have `List[Instruction]`; and
- we `map` a function `Instruction => List[Instruction]`; then
- we'll get a `List[List[Instruction]]`

as we can see from the type equation.

Our turtle doesn't know how to draw `List[List[Instruction]]` so this won't work.
</div>

There is a method `flatten` on `List`, which will convert a `List[List[A]]` to `List[A]`. We *could* use a combination of `map` and `flatten` but we have a better solution. This pattern comes up enough---and in different contexts which we'll see later---that there is a method just to handle it. The method is called `flatMap`.

The type equation for `flatMap` is

```scala
List[A] flatMap (A => List[B]) = List[B]
```

and this is illustrated graphically in [@fig:turtles:flatMap]. We can see that `flatMap` has the right type to combine `rule` with `List[Instruction]` to create a rewritten `List[Instruction]`.

![The type equation for flatMap illustrated 
graphically.](src/pages/turtles/flatMap.pdf+svg){#fig:turtles:flatMap}

When discussing `map` we said that it doesn't allow us to change the number of elements in the `List`. Graphically, we can't create a new "box" using `map`. With `flatMap` we can change the box, in the case lists meaning we can change the number of elements in the list.


### Exercises {-}

#### Double {-}

Using `flatMap`, write a method `double` that transforms a `List` to a `List` where every element appears twice. For example

```tut:invisible
def double[A](in: List[A]): List[A] =
  in.flatMap { x => List(x, x) }
```

```tut:book
double(List(1, 2, 3))
double(List("do", "ray", "me"))
```

<div class="solution">
There are two points to this:

- recognising how to use `flatMap`; and
- remembering how to use type variables.

```tut:silent:book
def double[A](in: List[A]): List[A] =
  in.flatMap { x => List(x, x) }
```
</div>


#### Or Nothing {-}

Using `flatMap`, write a method `nothing` that transforms a `List` to the empty `List`. For example

```tut:invisible
def nothing[A](in: List[A]): List[A] =
  in.flatMap { x => List() }
```

```tut:book
nothing(List(1, 2, 3))
nothing(List("do", "ray", "me"))
```

<div class="solution">
We could easily write this method as

```tut:silent:book
def nothing[A](in: List[A]): List[A] =
  List() // or List.empty or Nil
```

but the point of this exercise is to get more familiarity with using `flatMap`. With `flatMap` we can write the method as 

```tut:silent:book
def nothing[A](in: List[A]): List[A] =
  in.flatMap { x => List.empty }
```
</div>


#### Rewriting the Rules {-}

Write a method `rewrite` with signature

```tut:silent:book
def rewrite(instructions: List[Instruction], 
            rule: Instruction => List[Instruction]): List[Instruction] =
  ???
```

This method should apply `rule` to rewrite every instruction in `instructions`, except for branches which you'll need to handle specially. If you encounter a branch you should rewrite all the instructions inside the branch but leave the branch alone.

*Note*: You'll need to pass a `List[Instruction]` to `branch`, while `branch` itself accepts zero or more instructions (so-called *varargs*). To convert the `List[Instruction]` into a form that `branch` will accept, follow the parameter with `:_*` like so

```tut:book
val instructions = List(turn(45.degrees), forward(10))
branch(instructions:_*)
```

<div class="solution">
There are two parts to this:

- recognising that we need to use `flatMap`, for reasons discussed above; and
- realising that we need to recursively call `rewrite` to process the contents of a branch.

The latter is an example of structural recursion, though a slighlty more complex pattern than we've seen before.

```tut:silent:book
def rewrite(instructions: List[Instruction], rule: Instruction => List[Instruction]): List[Instruction] =
  instructions.flatMap { i =>
    i match {
      case Branch(i) =>
        List(branch(rewrite(i, rule):_*))
      case other =>
        rule(other)
    }
  }
```
</div>
 
#### Your Own L-System {-}

We're now ready to create a complete L-system. Using `rewrite` from above, create a method `iterate` with signature

```tut:book
def iterate(steps: Int,
            seed: List[Instruction], 
            rule: Instruction => List[Instruction]): List[Instruction] =
  ???
```

This should recursively apply `rule` to `seed` for `steps` iterations. 

<div class="solution">
This is just a simple structural recursion of the natural numbers, with all the hard work done by `rewrite`.

```tut:silent:book
def iterate(steps: Int, 
            seed: List[Instruction], 
            rule: Instruction => List[Instruction]): List[Instruction] =
  steps match {
    case 0 => seed
    case n => iterate(n - 1, rewrite(seed, rule), rule)
  }
```
</div>


#### Plants and Other Creations {-}

Create the pictures shown in [@fig:turtles:branching] and [@fig:turtles-koch-curve] using your L-system implementation.

![Five iterations of the simple branching L-system.](src/pages/turtles/branching.pdf+svg){#fig:turtles:branching}

![Five iterations of the Koch curve, a fractal that is simple to create with an L-System.](src/pages/turtles/koch-curve.pdf+svg){#fig:turtles:koch-curve}
