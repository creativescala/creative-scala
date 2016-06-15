## Exercises 

```tut:invisible
import doodle.core._
import doodle.core.Image._
import doodle.syntax._
import doodle.jvm.Java2DCanvas._
import doodle.backend.StandardInterpreter._
import doodle.random._
import cats.syntax.cartesian._
```

### Colored Boxes

In this exercise we'll return to one of our first examples, creating colored boxes. Start by adapting the `randomConcentricCircles` code to create a sequence of boxes, where each box has a random color. You should generate images like in [@fig:generative:random-color-boxes].

![Boxes with randomly chosen colors.](./src/raw/generative/random-color-boxes.pdf){#fig:generative:random-color-boxes}

<div class="solution">
Our solution reuses many of the components we created from `randomConcentricCircles`. It's fundamentally just a structural recursion over the integers.

```tut:book
val randomAngle: Random[Angle] =
  Random.double.map(x => x.turns)

val randomColor: Random[Color] =
  randomAngle map (hue => Color.hsl(hue, 0.7.normalized, 0.7.normalized))

val randomSpin: Random[Double] =
  Random.normal(15.0, 10.0)

def coloredRectangle(color: Color): Image =
   rectangle(20, 20).noFill fillColor color

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

These images are often a bit too random in their color choices. We can make some interesting images if we take an underlying structure---say a gradient---and make a random alteration to it.

The code for creating the gradient boxes with randomness is

```tut:book
def gradientBoxes(n: Int, color: Color): Image =
  n match {
    case 0 => coloredRectangle(color)
    case n => coloredRectangle(color) beside gradientBoxes(n-1, color.spin(15.degrees))
  }
```

This creates image like [@fig:generative:gradient-color-boxes]

![Boxes with a deterministic gradient.](./src/raw/generative/gradient-color-boxes.pdf){#fig:generative:gradient-color-boxes}

Now at each step we can add some random noise to `color`. (Note we can't use the randomly altered color as the input to the next recursion. We don't currently have the tools to make a random value depend on a random value.) This results in images like [@fig:generative:noisy-gradient-color-boxes]. Implement this.

![Boxes with a deterministic gradient with added random noise.](./src/raw/generative/noisy-gradient-color-boxes.pdf){#fig:generative:noisy-gradient-color-boxes}

<div class="solution">
Here's our solution. It's a combination of the simple structural recursion in `gradientBoxes` with the product operator as we saw in `randomColorBoxes`.

```tut:book
def nextColor(color: Color): Random[Color] =
  randomSpin map { spin => color.spin(spin.degrees) }

def noisyGradientBoxes(n: Int, color: Color): Random[Image] =
  n match {
    case 0 => nextColor(color) map { c => coloredRectangle(c) }
    case n =>
      val box = nextColor(color) map { c => coloredRectangle(c) }
      val boxes = noisyGradientBoxes(n-1, color.spin(15.degrees))
      box |@| boxes map { (b, bs) =>  b beside bs }
  }
```
</div>

### Scatter Plots

In this exercise we'll implement scatter plots as in [@fig:generative:distributions]. Experiment with different distributions (trying creating your own distributions by transforming ones defined on `Random`).

There are three main components of a scatter plot:

- we need to generate the points we'll plot;
- we need to overlay the images on top of each other in the same coordinate system to create the plot; and
- we need to convert a point to an image we can render; and

We tackle each task in turn.

Start by writing a method `makePoint` that will accept a `Random[Double]` for the x and y coordinates of a point and return a `Random[Point]`. It should have the following signature:

```scala
def makePoint(x: Random[Double], y: Random[Double]): Random[Point]
```

<div class="solution">
This is a nice example of composition of `Randoms` using `|@|`.

```tut:book
def makePoint(x: Random[Double], y: Random[Double]): Random[Point] =
  x |@| y map { (x, y) => Point.cartesian(x, y) }
```
</div>

Now create, say, a thousand random points using the techniques we learned in the previous chapter and a random distribution of your choice. You should end up with a `List[Random[Point]]`.

<div class="solution">
Something like the following should work.

```tut:book
val normal = Random.normal(50, 15)
val normal2D = makePoint(normal, normal)

val data = (1 to 1000).toList.map(_ => normal2D)
```
</div>

Now let's transform our `List[Random[Point]]` into `List[Random[Image]]`. Do this in two steps: first write a method to convert a `Point` to an `Image`, then write code to convert `List[Random[Point]]` to `List[Random[Image]]`.

<div class="solution">
We can convert a `Point` to an `Image` using a method `point` below. Note I've made each point on the scatterplot quite transparent---this makes it easier to see where a lot of points are grouped together.

```tut:book
def point(loc: Point): Image =
  circle(2).fillColor(Color.cadetBlue.alpha(0.3.normalized)).noLine.at(loc.toVec)
```

Converting between the lists is just a matter of calling `map` a few times.

```tut:book
val points = data.map(r => r.map(point _))
```
</div>

Now create a method that transforms a `List[Random[Image]]` to a `Random[Image]` by placing all the points `on` each other. This is the equivalent of the `allOn` method we've developed previously, but it now works with data wrapped in `Random`. 

<div class="solution">
You might recognise this pattern. It's what we used in `allOn` with the addition of `|@|`---which is exactly what `randomConcentricCircles` uses. 

```tut:book
def allOn(points: List[Random[Image]]): Random[Image] =
  points match {
    case Nil => Random.always(Image.empty)
    case img :: imgs => img |@| allOn(imgs) map { (i, is) => i on is }
  }
```
</div>

Now put it all together to make a scatter plot.

<div class="solution">
This is just calling methods and using values we've already defined.

```tut:book
val plot = allOn(points)
```
</div>


### Parametric Noise

In this exercise we will combine parametric equations, from the previous chapter, with randomness.

Let's start by making a method `perturb` that adds random noise to a `Point`. The method should have signature

```scala
def perturb(point: Point): Random[Point]
```

Choose whatever noise function you like.

<div class="solution">
Here's our solution. We can combine independent noise for the x- and y-coordinates using the product operator.

```tut:book
def perturb(point: Point): Random[Point] =
  Random.normal(0, 10) |@| Random.normal(0, 10) map { (x,y) =>
    Point.cartesian(point.x + x, point.y + y) 
  }
```
</div>

Now create a parametric function, like we did in the previous chapter. You could use the rose function (the function we explored in the previous chapter) or you could create one of your own devising. Here's the definition of rose.

```tut:book
def rose(k: Int): Angle => Point =
  (angle: Angle) => {
    Point.cartesian((angle * k).cos * angle.cos, (angle * k).cos * angle.sin)
  }
```

Now we can combine our parametric function and `perturb` to create a method with type `Angle => Random[Point]`. You can write this easily using the `andThen` method on functions, or you can write this out the long way. Here's a quick example of `andThen` showing how we write the fourth power in terms of the square.

```tut:book
val square = (x: Double) => x * x
val quartic = square andThen square
```

<div class="solution">
Writing this with `andThen` is nice and neat.

```tut:book
def perturbedRose(k: Int): Angle => Random[Point] =
  rose(k) andThen perturb
```
</div>

Now using `allOn` create a picture that combines randomnes and structure. Be as creative as you like, perhaps adding color, transparency, and other features to your image.

<div class="solution">
Here's the code we used to create [#fig:generative:volcano]. It's quite a bit larger than code we've seen up to this point, but you should understand all the components this code is built from.

```tut:book
object ParametricNoise {
  def rose(k: Int): Angle => Point =
    (angle: Angle) => {
      Point.cartesian((angle * k).cos * angle.cos, (angle * k).cos * angle.sin)
    }

  def scale(factor: Double): Point => Point =
    (pt: Point) => {
      Point.polar(pt.r * factor, pt.angle)
    }

  def perturb(point: Point): Random[Point] = {
    val noise = Random.normal(0, 10.0)

    (noise |@| noise) map { (dx, dy) =>
      Point.cartesian(point.x + dx, point.y + dy)
    }
  }

  def smoke(r: Normalized): Random[Image] = {
    val alpha = Random.normal(0.5, 0.1) map (a => a.normalized)
    val hue = Random.double.map(h => (h * 0.1).turns)
    val saturation = Random.double.map(s => (s * 0.8).normalized)
    val lightness = Random.normal(0.4, 0.1) map (a => a.normalized)
    val color =
      (hue |@| saturation |@| lightness |@| alpha) map {
        (h, s, l, a) => Color.hsla(h, s, l, a)
      }
    val c = Random.normal(5, 5) map (r => circle(r))

    (c |@| color) map { (circle, line) => circle.lineColor(line).noFill }
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

      (img |@| perturbed) map { (i, pt) =>
        i at pt.toVec.rotate(rotation)
      }
    }
  }

  def iterate(step: Angle): (Angle => Random[Image]) => Random[Image] = {
    (point: Angle => Random[Image]) => {
      def iter(angle: Angle): Random[Image] = {
        if(angle > Angle.one)
          Random.always(Image.empty)
        else
          point(angle) |@| iter(angle + step) map { _ on _ }
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
      (accum |@| img) map { _ on _ }
    }
    val background = (rectangle(650, 650) fillColor Color.black)

    picture map { _ on background }
  }
}
```
</div>
