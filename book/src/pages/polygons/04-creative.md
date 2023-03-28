# Going Further

```scala mdoc:invisible
import doodle.core.*
import doodle.syntax.all.*
import doodle.image.*
import doodle.image.syntax.all.*

def regularPolygon(sides: Int, radius: Double): Image = {
  val turn = (1.0 / sides).turns
  def loop(count: Int): ClosedPath =
    count match {
      case 0 => ClosedPath.empty.moveTo(radius, 0.degrees)
      case n => loop(n - 1).lineTo(radius, turn * n)
    }

  Image.path(loop(sides))
}
```

We're now going to look at a few creative extensions to what we've been working with so far. This gives us more practice with the core ideas: polar coordinates, paths, and structural recursion over the natural numbers. It also might show you a few tricks to use if you're interested in your own creative computing sketches.

## Concentric Polygons

We've created concentric shapes before. It's natural to use this same idea with polygons. In my example I've also increased the number of sides in the polygon as the shape gets bigger. The colors are created by smoothly reducing the lightness to zero as the polygons get larger.

@:doodle("concentric", "Creative.concentricExercise")

Create your own version of this concept. 

@:solution

This is a little twist on the structural recursion pattern we've used so far. I extracted the styling into it's own method to make the code easier to read. I use the method `darkenBy` to reduce the lightness by the given percentage. Calling `darkenBy` with `1.0.normalized` decreases the lightness by one hundred percent, while calling it with `0.5.normalized` reduces the lightness by half. Squaring the `percentage` in `style` makes the lightness decrease more slowly. You could also try the square root (`Math.sqrt`) for a faster transition.

```scala mdoc:silent
def style(percentage: Double, image: Image): Image = {
  image
    .strokeColor(
      Color.crimson.darkenBy((percentage * percentage).normalized)
    )
    .strokeWidth(3.0)
}

def concentricPolygons(
    total: Int,
    startRadius: Double,
    radiusStep: Double
): Image = {
  def loop(count: Int): Image =
    count match {
      case 0 =>
        regularPolygon(3, startRadius)
          .strokeColor(Color.crimson)
          .strokeWidth(3.0)
      case n =>
        loop(n - 1).on(
          style(
            n.toDouble / total.toDouble,
            regularPolygon(n + 3, startRadius + (radiusStep * n))
          )
        )
    }

  loop(total)
}
```
@:@


## Polygon Spiral

This next example takes the idea of concentric polygons and adds a small rotation at each step. Here I've used a fill instead of a stroke. Alternating between white and black fills give the result an [Op Art](https://en.wikipedia.org/wiki/Op_art) style.

@:doodle("spiral", "Creative.spiralExercise")

Create your own take on this idea.

@:solution

Once again, the core idea is a structural recursion over the natural numbers. As I did in the previous example, I have extracted the styling into it's own method. Using the modulo operator (`%`) allows me to choose a different fill for odd and even values of `count`, which creates the alternating fills in the image above.

```scala mdoc:silent
def style(count: Int, image: Image): Image = {
  image.noStroke.fillColor(if (count % 2 == 0) Color.white else Color.black)
}

def polygonSpiral(
    total: Int,
    startRadius: Double,
    radiusStep: Double,
    angleStep: Angle
): Image = {
  def loop(count: Int): Image =
    count match {
      case 0 => style(0, regularPolygon(5, startRadius))
      case n =>
        loop(count - 1).on(
          style(
            count,
            regularPolygon(5, startRadius + (radiusStep * n))
              .rotate(angleStep * n)
          )
        )
    }

  loop(total)
}
```
@:@


## Star Polygons

Our method of constructing polygons was to join a vertex to its immediate neighbour with a straight line. If we skip one or more neighbours we get what is called a star polygon. The animation below illustrates how this construction works for an octagon.

@:doodle("star-construction", "Creative.starPolygonConstruction")

Below are a few examples of star polygons that I created.

@:doodle("star-polygon", "Creative.starPolygonExercise")

Take this idea and give it your own spin.

@:solution

I made a few mistakes in calculating the vertex that is joined to the current vertex, but got it right after a little while. Once again, the core is a structural recursion over the natural numbers. No styling in this code because I separately styled the three star polygons in the example above.

```scala mdoc:silent
def starPolygon(sides: Int, radius: Int, skip: Int): Image = {
  val turn = (1.0 / sides).turns
  def loop(count: Int): ClosedPath =
    count match {
      case 0 => ClosedPath.empty.moveTo(radius, 0.degrees)
      case n =>
        loop(n - 1).lineTo(radius, turn * (n * skip))
    }

  Image.path(loop(sides))
}
```
@:@


## Curvygons

So far we have used only straight lines in paths. Paths can also include curves. In particular, they can include Bézier curves, which are defined by two control points and an end point. The animation below shows how the curve changes as the control points change.

@:doodle("bezier", "Creative.bezierCurveAnimation")

Using a Bézier curve to join vertices gives us a shape with curving edges that I call a curvygon. There is an example below. I couldn't resist animating this one, but I'm not expecting you to do so. (We haven't discussed how to create animations, but you can check the [Doodle documentation](https://www.creativescala.org/doodle/interact/animation.html).)

@:doodle("curvygon", "Creative.curvygonExercise")

Take this idea and go create!

@:solution

I'm not including all the code I wrote here. It's a bit long and uses features we haven't yet seen. You can find it online if you want, as everything about this book is freely available. Below is the core of it: the method `regularCurvygon` creates a curvygon given a number of sides, the radius, the amount to offset the control points relative to the radius, and proportion of turn where the first and second control points are located. If you don't understand what each parameter does (which is quite understandable, this is very brief description) play around with different values and draw the results.

```scala mdoc:silent
def regularCurvygon(
    sides: Int,
    radius: Int,
    radiusOffset: Int,
    offset1: Double,
    offset2: Double
): Image = {
  val turn = (1.0 / sides).turns
  def loop(count: Int): ClosedPath =
    count match {
      case 0 => ClosedPath.empty.moveTo(radius, 0.degrees)
      case n =>
        loop(n - 1).curveTo(
          radius - radiusOffset,
          turn * (n - 1) + (turn * offset1),
          radius + radiusOffset,
          turn * (n - 1) + (turn * offset2),
          radius,
          turn * n
        )
    }

  Image.path(loop(sides))
}
```
@:@
