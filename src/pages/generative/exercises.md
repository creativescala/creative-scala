## Exercises 

```scala mdoc:invisible
import doodle.core._
import doodle.core.Image._
import doodle.syntax._
import doodle.jvm.Java2DFrame._
import doodle.backend.StandardInterpreter._
import doodle.random._
import cats.syntax.cartesian._
```

### Scatter Plots

In this exercise we'll implement scatter plots as in [@fig:generative:distributions]. 
Experiment with different distributions (trying creating your own distributions by transforming ones defined on `Random`).

There are three main components of a scatter plot:

- we need to generate the points we'll plot;
- we need to overlay the images on top of each other in the same coordinate system to create the plot; and
- we need to convert a point to an image we can render.

We tackle each task in turn.

Start by writing a method `makePoint` that will accept a `Random[Double]` for the x and y coordinates of a point and return a `Random[Point]`. 
It should have the following skeleton:

```scala mdoc:silent
def makePoint(x: Random[Double], y: Random[Double]): Random[Point] =
  ???
```

Use a for comprehension in your implementation.

<div class="solution">
This is a nice example of composition of `Randoms`.

```scala mdoc
def makePoint(x: Random[Double], y: Random[Double]): Random[Point] =
  for {
    theX <- x
    theY <- y
  } yield Point.cartesian(theX, theY)
```
</div>

Now create, say, a thousand random points using the techniques we learned in the previous chapter on lists and a random distribution of your choice. 
You should end up with a `List[Random[Point]]`.

<div class="solution">
Something like the following should work.

```scala mdoc:silent
val normal = Random.normal(50, 15)
val normal2D = makePoint(normal, normal)

val data = (1 to 1000).toList.map(_ => normal2D)
```
</div>

Let's now transform our `List[Random[Point]]` into `List[Random[Image]]`. 
Do this in two steps: first write a method to convert a `Point` to an `Image`, then write code to convert `List[Random[Point]]` to `List[Random[Image]]`.

<div class="solution">
We can convert a `Point` to an `Image` using a method `point` below. 
Note I've made each point on the scatterplot quite transparent---this makes it easier to see where a lot of points are grouped together.

```scala mdoc:silent
def point(loc: Point): Image =
  circle(2).fillColor(Color.cadetBlue.alpha(0.3.normalized)).noLine.at(loc.toVec)
```

Converting between the lists is just a matter of calling `map` a few times.

```scala mdoc:silent
val points = data.map(r => r.map(point _))
```
</div>

Now create a method that transforms a `List[Random[Image]]` to a `Random[Image]` by placing all the points `on` each other. 
This is the equivalent of the `allOn` method we've developed previously, but it now works with data wrapped in `Random`. 

<div class="solution">
You might recognise this pattern. 
It's what we used in `allOn` with the addition of `flatMap`, which is exactly what `randomConcentricCircles` (and many other examples) use. 

```scala mdoc:silent
def allOn(points: List[Random[Image]]): Random[Image] =
  points match {
    case Nil => Random.always(Image.empty)
    case img :: imgs => 
      for {
        i  <- img
        is <- allOn(imgs)
      } yield (i on is)
  }
```
</div>

Now put it all together to make a scatter plot.

<div class="solution">
This is just calling methods and using values we've already defined.

```scala mdoc:silent
val plot = allOn(points)
```
</div>


### Parametric Noise

In this exercise we will combine parametric equations, from a previous chapter, with randomness.

Let's start by making a method `perturb` that adds random noise to a `Point`. 
The method should have skeleton

```scala mdoc:silent
def perturb(point: Point): Random[Point] =
  ???
```

Choose whatever noise function you like.

<div class="solution">
Here's our solution. 
We've already seen very similar code in the scatter plot.

```scala mdoc:silent
def perturb(point: Point): Random[Point] =
  for {
    x <- Random.normal(0, 10)
    y <- Random.normal(0, 10)
  } yield Point.cartesian(point.x + x, point.y + y) 
```
</div>

Now create a parametric function, like we did in a previous chapter. 
You could use the rose function (the function we explored previously) or you could create one of your own devising. Here's the definition of rose.

```scala mdoc:silent
def rose(k: Int): Angle => Point =
  (angle: Angle) => {
    Point.cartesian((angle * k).cos * angle.cos, (angle * k).cos * angle.sin)
  }
```

We can combine our parametric function and `perturb` to create a method with type `Angle => Random[Point]`. 
You can write this easily using the `andThen` method on functions, or you can write this out the long way. 
Here's a quick example of `andThen` showing how we write the fourth power in terms of the square.

```scala mdoc:silent
val square = (x: Double) => x * x
val quartic = square andThen square
```

<div class="solution">
Writing this with `andThen` is nice and neat.

```scala mdoc:silent
def perturbedRose(k: Int): Angle => Random[Point] =
  rose(k) andThen perturb
```
</div>

Now using `allOn` create a picture that combines randomnes and structure. 
Be as creative as you like, perhaps adding color, transparency, and other features to your image.

<div class="solution">
Here's the code we used to create [#fig:generative:volcano]. 
It's quite a bit larger than code we've seen up to this point, but you should understand all the components this code is built from.

```scala mdoc:silent
object ParametricNoise {
  def rose(k: Int): Angle => Point =
    (angle: Angle) => {
      Point.cartesian((angle * k).cos * angle.cos, (angle * k).cos * angle.sin)
    }

  def scale(factor: Double): Point => Point =
    (pt: Point) => {
      Point.polar(pt.r * factor, pt.angle)
    }

  def perturb(point: Point): Random[Point] =
    for {
      x <- Random.normal(0, 10)
      y <- Random.normal(0, 10)
    } yield Point.cartesian(point.x + x, point.y + y) 

  def smoke(r: Normalized): Random[Image] = {
    val alpha = Random.normal(0.5, 0.1) map (a => a.normalized)
    val hue = Random.double.map(h => (h * 0.1).turns)
    val saturation = Random.double.map(s => (s * 0.8).normalized)
    val lightness = Random.normal(0.4, 0.1) map (a => a.normalized)
    val color =
      for {
        h <- hue
        s <- saturation
        l <- lightness
        a <- alpha
      } yield Color.hsla(h, s, l, a)
    val c = Random.normal(5, 5) map (r => circle(r))
    
    for {
      circle <- c
      line   <- color
    } yield circle.lineColor(line).noFill
  }

  def point(
    position: Angle => Point,
    scale: Point => Point,
    perturb: Point => Random[Point],
    image: Normalized => Random[Image],
    rotation: Angle
  ): Angle => Random[Image] = {
    (angle: Angle) => {
      val pt = position(angle)
      val scaledPt = scale(pt)
      val perturbed = perturb(scaledPt)

      val r = pt.r.normalized
      val img = image(r)

      for {
        i  <- img
        pt <- perturbed
      } yield (i at pt.toVec.rotate(rotation))
    }
  }

  def iterate(step: Angle): (Angle => Random[Image]) => Random[Image] = {
    (point: Angle => Random[Image]) => {
      def iter(angle: Angle): Random[Image] = {
        if(angle > Angle.one)
          Random.always(Image.empty)
        else
          for {
            p  <- point(angle)
            ps <- iter(angle + step)
          } yield (p on ps)
      }

      iter(Angle.zero)
    }
  }

  val image: Random[Image] = {
    val pts =
      for(i <- 28 to 360 by 39) yield {
        iterate(1.degrees){
          point(
            rose(5),
            scale(i),
            perturb _,
            smoke _,
            i.degrees
          )
        }
      }
    val picture = pts.foldLeft(Random.always(Image.empty)){ (accum, img) =>
      for {
        a <- accum
        i <- img
      } yield (a on i)
    }
    val background = (rectangle(650, 650) fillColor Color.black)

    picture map { _ on background }
  }
}
```
</div>
