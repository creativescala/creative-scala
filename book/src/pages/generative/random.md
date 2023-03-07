## Randomness without Effect

```scala mdoc:invisible
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._
```

The solution to our problem is to separate describing how we'll use random numbers from the process of actually generating them. This sounds complicated, but it's exactly what we've be doing with `Image` throughout this book. We

- describe an `Image` using paths and methods like `beside`, `above`, and `on`; and
- we only draw an `Image` when we call `draw`.

We do the same thing with Doodle's `Random` type. To access this code we first need to import the `doodle.random` package.

```scala mdoc:silent
import doodle.random._
```

Now we can create values that describe creating a random number

```scala mdoc
val randomDouble = Random.double
```

No random numbers are actually created until we call the `run` method.

```scala mdoc
randomDouble.run
```

The type `Random[Double]` indicates we have something that will produce a random `Double` when we `run` it. Just like with `Image` and `draw`, substitution holds with `Random` up until the point we call `run`.

Table @:tref(generative:random) shows some of the ways to construct `Random` values.

----------------------------------------------------------------------------------------
Method                     Description                    Example
-------------------------- ------------------------------ ------------------------------
`Random.always(value)`     Creates a `Random` that        `Random.always(10)`
                           always produces the given
                           value.

`Random.double`            Creates a `Random` that        `Random.double`
                           generates a `Double`
                           uniformly distributed between
                           `0.0` and `1.0`.

`Random.int`               Creates a `Random` that        `Random.int`
                           generates an `Int`
                           uniformly distributed across
                           the entire range.

`Random.natural(limit)`    Creates a `Random` that        `Random.natural(10)`
                           generates a `Int`
                           uniformly distributed in
                           the range greater than or 
                           equal to `0` and less than
                           `1`.

`Random.oneOf(value, ...)` Creates a `Random` that        `Random.oneOf("A", "B", "C")`
                           generates one of the given
                           values with equal chance.
----------------------------------------------------------------------------------------

: Some of the methods to create `Random` values. @:tref(generative:random)


### Composing Random

Now we've seen how to create a `Random`, how do we compose them into more interesting programs? For example, how could we turn a random `Double` into a random `Angle`? It might be tempting to call `run` every time we want to manipulate a random result, but this will break substitution and is exactly what we're trying to avoid.

Remember when we talked about `map` in the previous chapter we said it transforms the elements but keeps the structure (number of elements) in the `List`. The same analogy applies to the `map` method on `Random`. It lets us transform the element of a `Random`---the value it produces when it is run---but doesn't let us change the structure. Here the "structure" means introducing more randomness, or making a random choice. 

We can create a random value and apply a *deterministic* transformation to it using `map`, but we can't create a random value and them use that value as input to a process that creates another random value.

Here's how we can create a random angle.

```scala mdoc:silent
val randomAngle: Random[Angle] =
  Random.double.map(x => x.turns)
```

When we `run` `RandomAngle` we can generate randomly created `Angle`

```scala mdoc
randomAngle.run
randomAngle.run
```

### Exercises {-}

#### Random Colors {-}

Given `randomAngle` above, create a method that accepts saturation and lightness and generates a random color. Your method should have the signature

```scala mdoc:silent
def randomColor(s: Normalized, l: Normalized): Random[Color] =
  ???
```

<div class="example">
This is a deterministic transformation of the output of `randomAngle`, so we can implement it using `map`.

```scala mdoc:reset:invisible
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._
import doodle.random._
val randomAngle: Random[Angle] =
  Random.double.map(x => x.turns)
```
```scala mdoc:silent
def randomColor(s: Double, l: Double): Random[Color] =
  randomAngle.map(hue => Color.hsl(hue, s, l))
```
</div>

#### Random Circles {-}

Write a method that accepts a radius and a `Random[Color]`, and produces a circle of the given radius and filled with the given random color. It should have the signature

```scala mdoc:silent
def randomCircle(r: Double, color: Random[Color]): Random[Image] =
  ???
```

<div class="example">
Once again this is a deterministic transformation of the random color, so we can use `map`.

```scala mdoc:reset:invisible
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._
import doodle.random._
```
```scala mdoc:silent
def randomCircle(r: Double, color: Random[Color]): Random[Image] =
  color.map(fill => Image.circle(r).fillColor(fill))
```
</div>
