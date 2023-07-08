# Writing Methods

```scala mdoc:invisible
import doodle.core.*
import doodle.image.*
import doodle.syntax.all.*
import doodle.image.syntax.all.*
import doodle.java2d.*
```

There is a systematic way to writing methods that will help us implement methods correctly.
We've already talked about this in the solution to some exercises, but it is worth calling out here.

Our first goal is to build a *method skeleton*, which is a method declaration with the body replaced with `???`.
This tells us the name of the method, its parameters and their types, and the type of the result.
We then fill in the body.

For example, we could describe the method `boxes` as a method that accepts a `Color` value and returns five boxes in a row filled with that color.
From this we can infer:

- the name `boxes`;
- there is a single `Color` parameter; and
- the result is an `Image`.

The skeleton for this is

```scala mdoc:silent
def boxes(color: Color): Image =
  ???
```

Notice that Scala will let you run this skeleton, and it will fail with an exception.

```scala mdoc:silent:fail
boxes(Color.mistyRose)
```

The reason we use this technique is it allows us to do *type-driven development*.
The types give us a lot of structure to our program, and as we'll see in the next chapter given a type we can derive a lot of our program in many cases.
By declaring the types but not implementing the body we can get Scala's type checker to see if large programs make sense, from the point of view of types, before we start implementing them.
This is an extremely powerful technique that we will only scratch the surface of.

Now we know all about methods let's implement some more complex examples.


@:exercise(Gradient Boxes)

Let's turn `boxes` up to eleven by making the color of the boxes change in a smooth gradient, like that shown below.

To minimise the amount of code we have to write, implement this by writing *two* methods.
The first method, `box`, should accept a `Color` and an `Angle` and create a box with the color spun by the angle.
The second method, `graidentBoxes`, should accept a `Color` and return an `Image` of five boxes filled with a gradient starting from the given color and changing by `15.degrees` at each successive box. `gradientBoxes` should make use of `box`.
Remember to write out the method skeletons first.

@:figure{ img = "gradient-boxes.svg", key = "#fig:methods:gradient-boxes", caption = "Five boxes filled with a gradient starting from Royal Blue" }
@:@

@:solution
The first step is to write out the skeletons.

```scala mdoc:reset:invisible
import doodle.core.*
import doodle.image.*
import doodle.syntax.all.*
import doodle.image.syntax.all.*
import doodle.java2d.*
```
```scala mdoc:silent
def box(color: Color, spin: Angle): Image =
  ???

def gradientBoxes(color: Color): Image =
  ???
```

Now fill in the body of the methods.

```scala mdoc:reset:invisible
import doodle.core.*
import doodle.image.*
import doodle.syntax.all.*
import doodle.image.syntax.all.*
import doodle.java2d.*
```
```scala mdoc:silent
def box(color: Color, spin: Angle): Image =
  Image.rectangle(40, 40).
    strokeWidth(5.0).
    strokeColor(color.spin(spin + 30.degrees)).
    fillColor(color.spin(spin)) 

def gradientBoxes(color: Color): Image = {
  box(color, 0.degrees) beside 
  box(color, 15.degrees) beside
  box(color, 20.degrees) beside
  box(color, 45.degrees) beside
  box(color, 60.degrees)
}
```
@:@


#### Gradient Concentric Circles {-}

Now implement methods to draw a picture similar to @:fref(methods:gradient-concentric-circles).
You should follow a design similar to `gradientBoxes` to reduce the amount of code you write, but we're not going to give the method skeletons this time---you have to work it out yourself.

@:figure{ img = "./src/pages/recursion/gradient-concentric-circles.pdf+svg", key = "#fig:methods:gradient-concentric-circles", caption = "Five concentric circles filled with a gradient starting from Royal Blue" }

<div class="solution">
There are a number of different ways to write this. 
We expect you would have two methods, `circle` and `gradientConcentricCircles` mirroring the design of `box` and `gradientBoxes` above.
The exact implementation of these two methods, particularly `circle` is where we'll see the most difference.

Here's our implementation.

```tut:silent:book
def circle(color: Color, n: Int): Image = 
  Image.circle(50 + (n * 10)).
    strokeColor(color.spin((15 * n).degrees)).
    strokeWidth(3)
    
def concentricCircles(color: Color): Image = {
  circle(color, 0) on
  circle(color, 1) on
  circle(color, 2) on
  circle(color, 3) on
  circle(color, 4)
}
```

We could have made `circle` have separate parameters for the size and spin, but we decided to compute the size and spin from a single number.
This gives us less flexibility (they cannot vary independently) but more compact code.
</div>
