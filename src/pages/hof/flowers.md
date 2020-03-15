## Flowers and Other Curves

```scala mdoc:invisible
import doodle.core._
import doodle.image._
import doodle.image.syntax._
import doodle.image.syntax.core._
import doodle.java2d._
```

In the previous section we saw that is was useful for methods to accept functions as parameters. In this section we'll see that it is useful for methods to return functions.

We've seen all the basic steps we need to make our flowers. Now we just need to know the curve that makes the flower shape! The shape I used is known as the [rose curve][rose-curve]. One example is shown in [@fig:hof:rose7]. 

![An example of the rose curve.](src/pages/hof/rose7.pdf+svg){#fig:hof:rose7}

The code for the parametric curve that gives this shape is below.

```scala mdoc:silent
// Parametric equation for rose with k = 7
val rose7 = (angle: Angle) =>
  Point((angle * 7).cos * 200, angle)
```

You may wonder why I called this function `rose7`. It's because we can vary the shape by changing the value `7` to something else. We could make a method or function to which we pass the value of this parameter and this function would return a particular rose curve. Here's that idea in code.

```scala mdoc:silent
def rose(k: Int): Angle => Point =
  (angle: Angle) => Point((angle * k).cos * 200, angle)
```

The `rose` method describes a family of curves. They all look similar, and we create individuals by choosing a particular value for the parameter `k`. In [@fig:hof:rose] we show more rose curves, this time with `k` as 5, 8, and 9 respectively.

![Examples of rose curves, with the parameter k chosen as 5, 8, or 9.](src/pages/hof/rose.pdf+svg){#fig:hof:rose}


Let's look at some other interesting curves. In [@fig:hof:lissajous] we show examples of a family of curves called [Lissajous curves][lissajous].

![Examples of Lissajous curves, with the parameters a and b chosen as 1, 2, or 3.](src/pages/hof/lissajous.pdf+svg){#fig:hof:lissajous}

The code for this is

```scala
def lissajous(a: Int, b: Int, offset: Angle): Angle => Point =
  (angle: Angle) =>
    Point(100 * ((angle * a) + offset).sin, 100 * (angle * b).sin)
```

The examples in [@fig:hof:lissajous] use values of `a` and `b` of 1, 2, or 3, and the `offset` set to 90 degrees.

There are an unlimited number of functions we could use to create interesting curves. Let's see one more example, this time of what known as an [epicycloid][epicycloid]. An epicycloid is produced when we trace a point on a circle rotating around another circle. We can stack circles on top of circles, and change the speed at which they rotate, to produce many different curves. For our example we are going to fix the number and radius of the circles and allow their speed of rotation to vary. Here is the code:

```scala
def epicycloid(a: Int, b: Int, c: Int): Angle => Point =
  (angle: Angle) =>
    (Point(75, angle * a).toVec + Point(32, angle * b).toVec + Point(15, angle * c).toVec).toPoint
```

You might notice this code converts points to vectors and back again. This is a little technical detail (we cannot add points but we can add vectors) that isn't important if you aren't familiar with vectors.

In [@fig:hof:epicycloid] we see three examples created by choosing the parameters a, b, c, as (1, 6, 14), (7, 13, 25), and (1, 7, -21) respectively.

![Examples of epicycloid curves, with the parameters chosen as (1, 6, 14), (7, 13, 25), and (1, 7 -21).](src/pages/hof/epicycloid.pdf+svg){#fig:hof:epicycloid}


#### Exercise {-}

We now have a lot of tools to play with. Your task here is simply to use some of the ideas we've just seen to make an image that you like. For inspiration you could use the image that we started the chapter with, but don't let it constrain you if you think of something else to explore.


[lissajous]: https://en.wikipedia.org/wiki/Lissajous_curve
[epicycloid]: https://en.wikipedia.org/wiki/Epicycloid
[rose-curve]: https://en.wikipedia.org/wiki/Rose_(mathematics)
