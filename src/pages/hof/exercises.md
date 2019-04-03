## Exercises

```scala mdoc:invisible
import doodle.core._
import doodle.core.Image._
import doodle.syntax._
import doodle.jvm.Java2DFrame._
import doodle.backend.StandardInterpreter._
```

Now we are chock full of knowledge about functions, we're going to return to the problem of drawing flowers. 
We'll be asking you to do more design work here than we have done previously.

Your task is to break down the task of drawing a flower into small functions that work together. 
Try to give yourself as much creative freedom as possible, by breaking out each individual component of the task into a function.

Try to do this now. If you run into problems look at our decomposition below.

### Components

We've identified two components of drawing flowers:

- the parametric equation; and
- the structural recursion over angles.

What other components might we abstract into functions? What are their types? (This is a deliberately open ended question. Explore!)

<div class="solution">
When we draw the parametric curves we probably what to change the radius of different curves.
We could abstract this into a function. 
What should the type of this function be? 
Perhaps the most obvious approach is to have function with type `(Point, Double) => Point`, where the `Double` parameter is the amount by which we scale the point. 
This is somehwat annoying to use, however. We have to continually pass around the `Double`, which never varies from its initial setting. 

A better structure is to create a function with type `Double => (Point => Point)`. 
This is a function to which we pass the scaling factor. 
It returns a function that transforms a `Point` by the given scaling factor. 
In this way we separate out the fixed scaling factor. The implementation could be

```scala mdoc:silent
def scale(factor: Double): Point => Point = 
  (pt: Point) => {
    Point.polar(pt.r * factor, pt.angle)
  }
```

In our previous discussion we've said we'd like to abstract the parametric equation out from `sample`. 
This we can easily do with

```tut:invisible
def parametricCircle(angle: Angle): Point =
  Point.cartesian(angle.cos, angle.sin)
```

```scala mdoc:silent
def sample(start: Angle, samples: Int, location: Angle => Point): Image = {
  // Angle.one is one complete turn. I.e. 360 degrees
  val step = Angle.one / samples
  val dot = triangle(10, 10)
  def loop(count: Int): Image = {
    val angle = step * count
    count match {
      case 0 => Image.empty
      case n =>
        dot.at(location(angle).toVec) on loop(n - 1)
    }
  }
  
  loop(samples)
}
```

We might like to abstract out the choice of image primitive (`dot` or `Image.triangle` above). 
We could change `location` to be a function `Angle => Image` to accomplish this. 

```scala mdoc:silent
def sample(start: Angle, samples: Int, location: Angle => Image): Image = {
  // Angle.one is one complete turn. I.e. 360 degrees
  val step = Angle.one / samples
  def loop(count: Int): Image = {
    val angle = step * count
    count match {
      case 0 => Image.empty
      case n => location(angle) on loop(n - 1)
    }
  }
  
  loop(samples)
}
```

We could also abstract out the entire problem specific part of the structural recursion. 
Where we had

```scala
def loop(count: Int): Image = {
  val angle = step * count
  count match {
    case 0 => Image.empty
    case n => location(angle) on loop(n - 1)
  }
}
```

we could abstract out the base case (`Image.empty`) and the problem specific part on the recursion (`location(angle) on loop(n - 1)`). The former would be just an `Image` but the latter is a function with type `(Angle, Image) => Image`. The final result is

```scala mdoc:silent
def sample(start: Angle, samples: Int, empty: Image, combine: (Angle, Image) => Image): Image = {
  // Angle.one is one complete turn. I.e. 360 degrees
  val step = Angle.one / samples
  def loop(count: Int): Image = {
    val angle = step * count
    count match {
      case 0 => empty
      case n => combine(angle, loop(n - 1))
    }
  }
  
  loop(samples)
}
```

This is a very abstract function. We don't expect most people will see this abstraction, but if you're interested in exploring this idea more you might like to read about folds and monoids.
</div>


### Combine

Now we've broken out the components we can combine them to create interesting results. Do this now.

<div class="solution">
You might end up with something like.

```scala mdoc:silent
def parametricCircle(angle: Angle): Point =
  Point.cartesian(angle.cos, angle.sin)
  
def rose(angle: Angle): Point =
  Point.cartesian((angle * 7).cos * angle.cos, (angle * 7).cos * angle.sin)

def scale(factor: Double): Point => Point = 
  (pt: Point) => {
    Point.polar(pt.r * factor, pt.angle)
  }

def sample(start: Angle, samples: Int, location: Angle => Point): Image = {
  // Angle.one is one complete turn. I.e. 360 degrees
  val step = Angle.one / samples
  val dot = triangle(10, 10)
  def loop(count: Int): Image = {
    val angle = step * count
    count match {
      case 0 => Image.empty
      case n => dot.at(location(angle).toVec) on loop(n - 1)
    }
  }
  
  loop(samples)
}

def locate(scale: Point => Point, point: Angle => Point): Angle => Point =
  (angle: Angle) => scale(point(angle))

// Rose on circle
val flower = {
  sample(0.degrees, 200, locate(scale(200), rose _)) on
  sample(0.degrees, 40, locate(scale(150), parametricCircle _)) 
}
```
</div>


### Experiment

Now experiment with the creative possibilities open to you!

<div class="solution">
Our implementation used to create [@fig:hof:flower-power] is at [Flowers.scala](https://github.com/underscoreio/doodle/blob/develop/shared/src/main/scala/doodle/examples/Flowers.scala). What did you come up with? Let us know! Our email addresses are `noel@underscore.io` and `dave@underscore.io`.
</div>
