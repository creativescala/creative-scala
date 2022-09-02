## Drawing Paths

Doodle provides another type of `Image` that makes particular use of sequences.
`Paths` represent arbitrary shapes created using sequences of pen movements:

``` scala
val image = OpenPath(List(
  MoveTo(Vec(0, 0).toPoint),
  LineTo(Vec(100, 0).toPoint),
  LineTo(Vec(50, 100).toPoint),
  BezierCurveTo(Vec(50, 0).toPoint, Vec(0, 100).toPoint, Vec(0, 0).toPoint)
))
// image: Path = // ...

image.draw
```

@:figure{ img = "src/pages/collections/path.png", caption = "An example Path" }

Pen movements come in three varieties:

 -  `MoveTo(point)`---move the pen to `point` without drawing a line;

 -  `LineTo(point)`---move the pen to `point` drawing a straight line;

 -  `BezierCurveTo(cp1, cp2, point)`---move the pen to `point` drawing a bezier
    curve---`cp1` and `cp2` are "control points" determining the shape of the curve.

The arguments in each case are objects of type `Vec`, which are 2D vectors representing `x,y` points (the name `Vector` is already taken by the `scala.collection.Vector` class). There are various ways we can create and transform `Vecs`:

--------------------------------------------------------------------------------------------------------
Code                       Result    Description                     Example
-------------------------- --------- ------------------------------- -----------------------------------
`Vec(num, num)`            `Vec`     Create a vector using           `Vec(3, 4)`
                                     `x,y` coordinates

`Vec.polar(angle, length)` `Vec`     Create a vector using           `Vec.polar(30.degrees, 100)`
                                     polar coordinates

`Vec.zero`                 `Vec`     A zero vector (`0,0`)           `Vec.zero`

`Vec.unitX`                `Vec`     A unit X vector (`1,0`)         `Vec.unitX`

`Vec.unitY`                `Vec`     A unit Y vector (`0,1`)         `Vec.unitY`

`vec * num`                `Vec`     Multiply `vec` by `num`         `Vec(2, 1) * 10`

`vec / num`                `Vec`     Divide `vec` by `num`           `Vec(20, 10) / 10`

`vec + vec`                `Vec`     Add vectors                     `Vec(2, 1) + Vec(1, 3)`

`vec - vec`                `Vec`     Subtract vectors                `Vec(5, 5) - Vec(2, 1)`

`vec rotate angle`         `Vec`     Rotate anticlockwise by `angle` `Vec(5, 5) rotate 45.degrees`

`vec.x`                    `Double`  Get the X component of `vec`    `Vec(3, 4).x`

`vec.x`                    `Double`  Get the Y component of `vec`    `Vec(3, 4).y`

`vec.length`               `Double`  Get the length of `vec          `Vec(3, 4).length`

-------------------------------------------------------------------------------------------------------

We can use these operations to create paths quickly by adding vectors. Notice how we start the shape with a `MoveTo` element (all paths implicitly start at the origin). This is a very common pattern.

``` scala
val elements = (0 to 360 by 36).map { angle =>
  val point = (Vec.unitX * 100) rotate angle.degrees toPoint
  val element =
    if(angle == 0)
      MoveTo(point)
    else
      LineTo(point)
  element
}
// elements: scala.collection.immutable.IndexedSeq[doodle.core.PathElement] = // ...

val decagon = OpenPath(elements)
// decagon: doodle.core.Path = // ...

decagon.draw
```

@:figure{ img = "src/pages/collections/decagon.png", caption = "A Decagon" }

### Exercise: My God, It's Full of Stars!

Let's use this pattern to draw some stars.
For the purpose of this exercise let's assume that a star is a polygon with `p` points.
However, instead of connecting each point to its neighbours,
we'll connect them to the `nth` point around the circumference.

For example, the diagram below shows stars with `p=11` and `n=1 to 5`.
`n=1` produces a regular polygon while
values of `n` from `2` upwards produce stars with increasingly sharp points:

@:figure{ img = "src/pages/collections/stars.png", caption = "Stars with `p=11` and `n=1 to 5`" }

Write code to draw the diagram above.
Start by writing a method to draw a `star` given `p` and `n`:

``` scala
def star(p: Int, n: Int, radius: Double): Image =
  ???
```

Create the points for your star using ranges and `Vec.polar`:

@:figure{ img = "src/pages/collections/polar.pdf+svg", caption = "Polar coordinates on a 5 pointed polygon" }

Use your choice of recursion and `beside` or iteration and `allBeside` to create the row of stars.

<div class="solution">
Here's the `star` method. We've renamed `p` and `n` to `points` and `skip` for clarity:

``` scala
def star(sides: Int, skip: Int, radius: Double) = {
  val centerAngle = 360.degrees * skip / sides

  val elements = (0 to sides) map { index =>
    val point = Vec.polar(centerAngle * index, radius)
    if(index == 0)
      MoveTo(point)
    else 
      LineTo(point)
  }

  Path(elements) strokeWidth 2
}
```

We'll use `allBeside` to create the row of stars.
We only need to use values of `skip`
from `1` to `sides/2` rounded down:

``` scala
(allBeside((1 to 5) map { skip =>
  star(sides, skip, 100)
})).draw
```
</div>

When you've finished your row of stars,
try constructing a larger image from different values of `p` and `n`.
Here's an example:

@:figure{ img = "src/pages/collections/stars2.png", caption = "Stars with `p=3 to 33 by 2` and `n=1 to p/2`" }

<div class="solution">
To create the image above, we started by adding colours
and a chunkier outline to the definition of `star`:

``` scala
def star(sides: Int, skip: Int, radius: Double) = {
  val centerAngle = 360.degrees * skip / sides

  val elements = (0 to sides) map { index =>
    val point = Vec.polar(centerAngle * index, radius).toPoint
    if(index == 0)
      MoveTo(point)
    else
      LineTo(point)
  }

  OpenPath(elements).
    strokeWidth(2).
    strokeColor(Color.hsl(centerAngle, 1.normalized, .25.normalized)).
    fillColor(Color.hsl(centerAngle, 1.normalized, .75.normalized))
}
```

The updated scene then becomes:

``` scala
allAbove((3 to 33 by 2) map { sides =>
  allBeside((1 to sides/2) map { skip =>
    star(sides, skip, 20)
  })
})
```
</div>
