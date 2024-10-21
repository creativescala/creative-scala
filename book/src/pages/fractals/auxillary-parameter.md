# Auxiliary Parameters

```scala mdoc:invisible
import doodle.core.*
import doodle.image.*
import doodle.syntax.all.*
import doodle.image.syntax.all.*
import doodle.java2d.*
val aBox = Image.square(20).fillColor(Color.royalBlue)
```

We've seen how to use structural recursion over the natural numbers to write a number of interesting programs.
In this section we're going to learn how *auxillary parameters* allow us to write more complex programs.
An auxiliary parameter is just an additional parameter to our method that allows us to pass extra information down the recursive call.

For example, imagine creating the picture below which shows a line of boxes that grow in size as we move along the line.

@:figure{ img = "growing-boxes.svg", key = "#fig:recursion:growing-boxes", caption = "Boxes that grow in size with each recursion." }

How can we create this image?

We know it has to be a structural recursion over the natural numbers, so we can immediately write down the skeleton

```scala
def growingBoxes(count: Int): Image =
  count match {
    case 0 => base
    case n => unit add growingBoxes(n-1)
  }
```

Using what we learned working with `boxes` earlier we can go a bit further and write down

```scala
def growingBoxes(count: Int): Image =
  count match {
    case 0 => Image.empty
    case n => Image.square(???).beside(growingBoxes(n-1))
  }
```

The challenge becomes how to make the box grow in size as we move to the right.

There are two ways to do this.
The tricky way is to switch the order in the recursive case and make the size of the box a function of `n`.
Here's the code.

```scala mdoc:silent
def growingBoxes(count: Int): Image =
  count match {
    case 0 => Image.empty
    case n => growingBoxes(n-1).beside(Image.square(n*10))
  }
```

Spend some time figuring out why this works before moving on to the solution using an auxiliary parameter.

Alternatively we can simply add another parameter to `growingBoxes` that tells us how big the current box should be.
When we recurse we change this size.
Here's the code.

```scala mdoc:silent
def growingBoxes(count: Int, size: Int): Image =
  count match {
    case 0 => Image.empty
    case n => 
      Image
        .square(size)
        .beside(growingBoxes(n-1, size + 10))
  }
```

The auxiliary parameter method has two advantages: we only have to think about what changes from one recursion to the next (in this case, the box gets larger), and it allows the caller to change this parameter (for example, making the starting box larger or smaller).

Now we've seen the auxiliary parameter method let's practice using it.

@:exercise(Gradient Boxes)

In this exercise we're going to draw a picture like that shown below.
We already know how to draw a line of boxes.
The challenge in this exercise is to make the color change at each step.

Hint: you can `spin` the fill color at each recursion.

@:figure{ img = "gradient-boxes.svg", key = "#fig:recursion:gradient-boxes", caption = "Five boxes filled with changing colors starting from Royal Blue" }
@:@

@:solution
There are two ways to implement a solution.
The auxiliary parameter method is to add an extra parameter to `gradientBoxes` and pass the `Color` through the structural recursion.

```scala mdoc:silent
def gradientBoxes(n: Int, color: Color): Image =
  n match {
    case 0 => Image.empty
    case n =>
      aBox
        .fillColor(color)
        .beside(gradientBoxes(n - 1, color.spin(15.degrees)))
  }
```

We could also make the fill color a function of `n`, as we demonstrated with the box size in `growingBoxes` above.

```scala mdoc:silent
def gradientBoxes(n: Int): Image =
  n match {
    case 0 => Image.empty
    case n =>
      aBox
        .fillColor(Color.royalBlue.spin((15 * n).degrees))
        .beside(gradientBoxes(n - 1))
  }
```
@:@


@:exercise(Concentric Circles)

Now let's try a variation on the theme, drawing concentric circles as in the picture below. Here we are changing the size rather than the color of the image at each step. Otherwise the pattern stays the same. Have a go at implementing it.

@:figure{ img = "concentric-circles.svg", key = "#fig:recursion:concentric-circles", caption = "Concentric circles, colored Royal Blue" }
@:@

@:solution
This is almost identical to `growingBoxes`.

```scala mdoc:silent
def concentricCircles(count: Int, size: Int): Image =
  count match {
    case 0 => Image.empty
    case n => 
      Image
        .circle(size)
        .on(concentricCircles(n-1, size + 5))
  }
```
@:@

@:exercise(Once More, With Feeling)

Now let's combine both techniques to change size and color on each step, giving results like those shown below.
Experiment until you find something you like.

@:figure{ img = "colorful-circles.svg", key = "#fig:recursion:colorful-circles", caption = "Concentric circles with interesting color variations" }
@:@

@:solution
Here's our solution, where we've tried to break the problem into reusable parts and reduce the amount of repeated code.
We still have a lot of repetition as we don't yet have the tools to get rid of more.
We'll come to that soon.

```scala mdoc:silent
def circle(size: Int, color: Color): Image =
  Image.circle(size).strokeWidth(3.0).strokeColor(color)

def fadeCircles(n: Int, size: Int, color: Color): Image =
  n match {
    case 0 => Image.empty
    case n => 
      circle(size, color)
        .on(fadeCircles(n-1, size+7, color.fadeOutBy(0.05.normalized)))
  }

def gradientCircles(n: Int, size: Int, color: Color): Image =
  n match {
    case 0 => Image.empty
    case n => 
      circle(size, color)
        .on(gradientCircles(n-1, size+7, color.spin(15.degrees)))
  }

def image: Image =
  fadeCircles(20, 50, Color.red)
    .beside(gradientCircles(20, 50, Color.royalBlue))
```
@:@
