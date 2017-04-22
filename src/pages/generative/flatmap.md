## Combining Random Values

```tut:invisible
import doodle.core._
import doodle.core.Image._
import doodle.syntax._
import doodle.jvm.Java2DCanvas._
import doodle.backend.StandardInterpreter._
```

<div class="callout callout-info">
In addition to the standard imports given at the start of the chapter, in this section we're assuming the following:

```tut:silent
import doodle.random._
```
</div>

So far we've seen how to represent functions generating random values using the `Random` type, and how to make deterministic transformations of a random value using `map`. 
In this section we'll see how we can make a random (or stochastic, if you prefer fancier words) transformation of a random value using `flatMap`.

To motivate the problem lets try writing `randomConcentricCircles`, which will generate concentric circles with randomly chosen hue using the utility methods we developed in the previous section.

We start with the code to create concentric circles with deterministic colors and the utilities we developed previously.

```tut:silent:book
def concentricCircles(count: Int, size: Int, color: Color): Image =
  count match {
    case 0 => Image.empty
    case n => 
      Image.circle(size).fillColor(color) on concentricCircles(n-1, size + 5, color.spin(15.degrees))
  }
  
def randomAngle: Random[Angle] =
  Random.double.map(x => x.turns)

def randomColor(s: Normalized, l: Normalized): Random[Color] =
  randomAngle map (hue => Color.hsl(hue, s, l))
  
def randomCircle(r: Double, color: Random[Color]): Random[Image] =
  color map (fill => Image.circle(r) fillColor fill)
```

Let's create a method skeleton for `randomConcentricCircles`.

```tut:silent:book
def randomConcentricCircles(count: Int, size: Int): Random[Image] =
  ???
```

The important change here is we return a `Random[Image]` not an `Image`.
We know this is a structural recursion over the natural numbers so we can fill out the body a bit.

```tut:silent:book
def randomConcentricCircles(count: Int, size: Int): Random[Image] =
  count match {
    case 0 => ???
    case n => ???
  }
```

The base case will be `Random.always(Image.empty)`, the direct of equivalent of `Image.empty` in the deterministic case.


```tut:silent:book
def randomConcentricCircles(count: Int, size: Int): Random[Image] =
  count match {
    case 0 => Random.always(Image.empty)
    case n => ???
  }
```

What about the recursive case?
We could try using

```tut:silent:book
val randomPastel = randomColor(0.7.normalized, 0.7.normalized)
```

```tut:fail:book
def randomConcentricCircles(count: Int, size: Int): Random[Image] =
  count match {
    case 0 => Image.empty
    case n => 
      randomCircle(size, randomPastel) on randomConcentricCircles(n-1, size + 5)
  }
```

but this does not compile.
Both `randomConcentricCircles` and `randomCircle` evaluate to `Random[Image]`. 
There is no method `on` on `Random[Image]` so this code doesn't work.

Since this is a transformation of two `Random[Image]` values, it seems like we need some kind of method that allows us to transform *two* `Random[Image]`, not just one like we can do with `map`.  
We might call this method `map2` and we could imagine writing code like

```scala
randomCircle(size, randomPastel).map2(randomConcentricCircles(n-1, size + 5)){
  (circle, circles) => circle on circles
}
```

Presumably we'd also need `map3`, `map4`, and so on. 
Instead of these special cases we can use `flatMap` and `map` together.

```scala
randomCircle(size, randomPastel) flatMap { circle =>
  randomConcentricCircles(n-1, size + 5) map { circles =>
    circle on circles
  }
}
```

The complete code becomes

```tut:silent:book
def randomConcentricCircles(count: Int, size: Int): Random[Image] =
  count match {
    case 0 => Random.always(Image.empty)
    case n => 
      randomCircle(size, randomPastel) flatMap { circle =>
        randomConcentricCircles(n-1, size + 5) map { circles =>
          circle on circles
        }
      }
  }
```

Example output is shown in [@fig:generative:random-concentric-circles].

![The output of one run of `randomConcentricCircles(10, 10).run.draw`](./src/pages/generative/random-concentric-circles.pdf+svg){#fig:generative:random-concentric-circles}

Let's now look closer at this use of `flatMap` and `map` to understand how this works.

### Type Algebra

The simplest way, in my opinion, to understand why this code works is to look at the types.
The code in question is

```scala
randomCircle(size, randomPastel) flatMap { circle =>
  randomConcentricCircles(n-1, size + 5) map { circles =>
    circle on circles
  }
}
```

Starting from the inside, we have

```scala
{ circles =>
    circle on circles
}
```

which is a function with type

```scala
Image => Image
```

Wrapping this we have

```scala
randomConcentricCircles(n-1, size + 5) map { circles =>
    circle on circles
  }
```

We known `randomConcentricCircles(n-1, size + 5)` has type `Random[Image]`.
Substituting in the `Image => Image` type we worked out above we get

```scala
Random[Image] map (Image => Image)
```

Now we can deal with the entire expression 

```scala
randomCircle(size, randomPastel) flatMap { circle =>
  randomConcentricCircles(n-1, size + 5) map { circles =>
    circle on circles
  }
}
```

` randomCircle(size, randomPastel)` has type `Random[Image]`.
Performing substitution again gets us a type equation for the entire expression.

```scala
Random[Inage] flatMap (Random[Image] map (Image => Image))
```

Now we can apply the type equations for `map` and `flatMap` that we saw earlier:

```scala
F[A] map (A => B) = F[B]
F[A] flatMap (A => F[B]) = F[B]
```

Working again from the inside out, we first use the type equation for `map` which simplifies the type expression to

```scala
Random[Inage] flatMap (Random[Image])
```

Now we can apply the equation for `flatMap` yielding just

```scala
Random[Image]
```

This tells us the result has the type we want. 
Notice that we've been performing substitution at the type level---the same technique we usually use at the value level.


### Exercises {-}

Don't forget to import `doodle.random._` when you attempt these exercises.

#### Randomness and Randomness {-}

What is the difference between the output of `programOne` and `programTwo` below? Why do
they differ?

```tut:silent:book
def randomCircle(r: Double, color: Random[Color]): Random[Image] =
  color map (fill => Image.circle(r) fillColor fill)

def randomConcentricCircles(count: Int, size: Int): Random[Image] =
  count match {
    case 0 => Random.always(Image.empty)
    case n => 
      randomCircle(size, randomPastel) flatMap { circle =>
        randomConcentricCircles(n-1, size + 5) map { circles =>
          circle on circles
        }
      }
  }

val circles = randomConcentricCircles(5, 10)
val programOne = 
  circles flatMap { c1 => 
    circles flatMap { c2 => 
      circles map { c3 => 
        c1 beside c2 beside c3
      } 
    }
  }

val programTwo =
  circles map { c => c beside c beside c }
```

<div class="solution">
`programOne` displays three different circles in a row, while `programTwo` repeats the same circle three times. The value `circles` represents a program that generates an image of randomly colored concentric circles. Remember `map` represents a deterministic transform, so the output of `programTwo` must be the same same circle repeated thrice as we're not introducing new random choices. In `programOne` we merge `circle` with itself three times. You might think that the output should be only one random image repeated three times, not three, but remember `Random` preserves substitution. We can write `programOne` equivalently as

```tut:book
val programOne = 
  randomConcentricCircles(5, 10) flatMap { c1 => 
    randomConcentricCircles(5, 10) flatMap { c2 => 
      randomConcentricCircles(5, 10) map { c3 => 
        c1 beside c2 beside c3
      } 
    }
  }
```

which makes it clearer that we're generating three different circles.


#### Colored Boxes {-}

Let's return to a problem from the beginning of the book: drawing colored boxes. This time we're going to make the gradient a little more interesting, by making each color randomly chosen.

Recall the basic structural recursion for making a row of boxes

```tut:book
def rowOfBoxes(count: Int): Image =
  count match {
    case 0 => rectangle(20, 20)
    case n => rectangle(20, 20) beside rowOfBoxes(n-1)
  }
```

Let's alter this, like with did with concentric circles, to have each box filled with a random color. *Hint:* you might find it useful to reuse some of the utilities we created for `randomConcentricCircles`. Example output is shown in [@fig:generative:random-color-boxes].

![Boxes filled with random colors.](./src/pages/generative/random-color-boxes.pdf+svg){#fig:generative:random-color-boxes}

<div class="solution">
This code uses exactly the same pattern as `randomConcentricCircles`.

```tut:book
val randomAngle: Random[Angle] =
  Random.double.map(x => x.turns)

val randomColor: Random[Color] =
  randomAngle map (hue => Color.hsl(hue, 0.7.normalized, 0.7.normalized))

def coloredRectangle(color: Color): Image =
  rectangle(20, 20) fillColor color

def randomColorBoxes(n: Int): Random[Image] =
  count match {
    case 0 => randomColor map { c => coloredRectangle(c) }
    case n =>
      val box = randomColor map { c => coloredRectangle(c) }
      val boxes = randomColorBoxes(n-1)
      box flatMap { b =>
        boxes map { bs => b beside bs }
      }
  }
```
</div>
