## Flowers and Other Curves

```scala mdoc:invisible
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._
```

We have seen that is is useful for methods to accept functions as parameters. In this section we'll see that it is useful for methods to return functions. In doing so we'll create the flower-like pictures that were our goal going into this chapter.

We've seen all the basic steps we need to make our flowers. Now we just need to know the curve that makes the flower shape! The shape I used is known as the [rose curve][rose-curve]. One example is shown below. 

@:figure{ img = "./rose7.svg", key = "#fig:hof:rose7", caption = "An example of the rose curve." }

Here's the code for the parametric curve.

```scala mdoc:silent
// Parametric curve for rose with k = 7
val rose7 = (angle: Angle) =>
  Point((angle * 7).cos * 200, angle)
```

You may wonder why I called this function `rose7`. It's because we can vary the shape by changing the value `7` to something else. We could make a method or function to which we pass the value of this parameter and this function would return a particular rose curve. Here's that idea in code.

```scala mdoc:silent
def rose(k: Int): Angle => Point =
  (angle: Angle) => Point((angle * k).cos * 200, angle)
```

The `rose` method describes a family of curves. They all look similar, and we create individual curves by choosing a particular value for the parameter `k`. Below we show more rose curves, this time with `k` as 5, 8, and 9 respectively.

@:figure{ img = "./rose.svg", key = "#fig:hof:rose", caption = "Examples of rose curves, with the parameter k chosen as 5, 8, or 9." }


Let's look at some other interesting curves. Below we show examples of a family of curves called [Lissajous curves][lissajous].

@:figure{ img = "./lissajous.svg", key = "#fig:hof:lissajous", caption = "Examples of Lissajous curves, with the parameters a and b chosen as 1, 2, or 3." }

The code for this is

```scala
def lissajous(a: Int, b: Int, offset: Angle): Angle => Point =
  (angle: Angle) =>
    Point(100 * ((angle * a) + offset).sin, 100 * (angle * b).sin)
```

The examples use values of `a` and `b` of 1, 2, or 3, and the `offset` set to 90 degrees.


@:exercise(Pick Your Own)
We now have a lot of tools to play with. Your task here is simply to use some of the ideas we've just seen to make an image that you like. For inspiration you could use the image that we started the chapter with, but don't let it constrain you if you think of something else to explore.
@:@

[lissajous]: https://en.wikipedia.org/wiki/Lissajous_curve
[rose-curve]: https://en.wikipedia.org/wiki/Rose_(mathematics)
