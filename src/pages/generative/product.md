## Combining Random Values

```scala mdoc:invisible
import doodle.core._
import doodle.image._
import doodle.image.syntax._
import doodle.image.syntax.core._
import doodle.java2d._
```

<div class="callout callout-info">
In addition to the standard imports given at the start of the chapter, in this section we're assuming the following:

```tut:silent
import doodle.random._
```
</div>

So far we've seen how to represent functions generating random values using the `Random` type, and how to make deterministic transformations of a random value using `map`. In this section we'll see how we can combine two independent random values.

To motivate the problem lets try writing `randomConcentricCircles`, which will generate concentric circles with randomly chosen hue using the utility methods we developed in the previous section.

We start with the code to create concentric circles with deterministic colors and the utilities we developed previously.

```tut:book
def concentricCircles(n: Int, color: Color): Image =
  n match {
    case 0 => circle(10) fillColor color
    case n => concentricCircles(n-1, color.spin(15.degrees)) on (circle(n * 10) fillColor color) 
  }
  
def randomAngle: Random[Angle] =
  Random.double.map(x => x.turns)

def randomColor(s: Normalized, l: Normalized): Random[Color] =
  randomAngle map (hue => Color.hsl(hue, s, l))
  
def randomCircle(r: Double, color: Random[Color]): Random[Image] =
  color map (fill => Image.circle(r) fillColor fill)
```

Our first step might be to replace `circle` with `randomCircle` like so

```tut:book
val randomPastel = randomColor(0.7.normalized, 0.7.normalized)
```

```tut:fail:book
def randomConcentricCircles(n: Int): Random[Image] =
  n match {
    case 0 => randomCircle(10, randomPastel)
    case n => randomConcentricCircles(n-1) on randomCircle(n*10, randomPastel)
  }
```

(Note that `randomConcentricCircles` returns a `Random[Image]`.)

This does not compile, due to the line

```scala
randomConcentricCircles(n-1) on randomCircle(n*10, randomPastel)
```

Both `randomConcentricCircles` and `randomCircle` evaluate to `Random[Image]`. There is no method `on` on `Random[Image]` so this code doesn't work.

Since this is a deterministic transformation of two `Random[Image]` values, it seems like we need some kind of method that allows us to transform *two* `Random[Image]`, not just one like we can do with `map`.  We might call this method `map2` and we could imagine writing code like

```scala
randomConcentricCircles(n-1).map2(randomCircle(n*10, randomPastel)){ 
  (circles, circle) => circles on circle
}
```

Presumably we'd also need `map3`, `map4`, and so on. Instead of these special cases we have a more general operator, provided by a library called [Cats][cats]. If we add the following import

```tut:book
import cats.syntax.all._
```

we can now write

```scala
(randomConcentricCircles(n-1), (randomCircle(n*10, randomPastel))) mapN { 
  (circles, circle) => circles on circle
}
```

The complete code becomes

```tut:book
import cats.syntax.all._

val randomPastel = randomColor(0.7.normalized, 0.7.normalized)

def randomConcentricCircles(n: Int): Random[Image] =
  n match {
    case 0 => randomCircle(10, randomPastel)
    case n =>
      (randomConcentricCircles(n-1), randomCircle(n * 10, randomPastel)) mapN {
        (circles, circle) => circles on circle
      }
  }
```

Example output is shown in [@fig:generative:random-concentric-circles].

![The output of one run of `randomConcentricCircles(10).run().draw`](./src/pages/generative/random-concentric-circles.png){#fig:generative:random-concentric-circles}

So what is this new `mapN` method? Let's look in more depth.


### Tuples and the `mapN` method

The `mapN` method is something that Cats adds to *tuples*. We haven't encountered tuples yet, so let's learn about them first.

A tuple is a container that lets us store a fixed number of elements of different types together. Here are some examples.

```tut:book
("freakout", 1)
("in", 2, 4.0)
("moonage", 42, circle(10), "daydream")
```

We can construct tuples using the syntax above. What about deconstructing them? For this we can use pattern matching.

```tut:book
("a", "b", "c") match {
  case (x, y, z) => s"$x $y $z"
}
```

Now the `mapN` method allows us to transform tuples that contain ...

*TODO: complete description.*


This makes more sense! The result is a `List` where the first element of the left-hand list has been paired with all the elements of the right-hand list, then the second element, and so on.


Now we can say more precisely what the product operator is doing to the values in the boxes: it tuples them together.

Now let's return to `Random`. What is the product operator doing here? `Random[A] |@| Random[B]` is merging together two programs or computations, one producing a value of type `A` at the random, and the other producing a value of type `B` at random. The result is a program that produces at random a value of type `(A, B)`. Once we have a box containing a value of `(A, B)` we can `map` over it to perform a deterministic transform. This is exactly what we did in `randomConcentricCircles`.

Finally, we should ask if this means we can pass, say, a tuple of two elements to a function that expects two parameters. Let's answer this experimentally.

```tut:book
val f = (a: Int, b: Int) => a + b
val tuple = (1, 2)
```

```tut:fail:book
f(tuple)
```

We cannot. We'd have to write something like

```tut:book
val tupleToF = (in: (Int, Int)) => {
    in match {
      case (a, b) => f(a, b)
    }
}

tupleToF(tuple)
```

That's quite a lengthy explanation. The good news is we don't ever run into this if we just immediately `mapN` over the result of using the product operator, which is the usual case.


### Exercises {-}

Don't forget the following imports when you attempt these exercises.

```tut:book
import doodle.random._
import cats.syntax.cartesian._
```


#### Randomness and Randomness {-}

What is the difference between the output of `programOne` and `programTwo` below? Why do
they differ?

```tut:book
def randomCircle(r: Double, color: Random[Color]): Random[Image] =
  color map (fill => Image.circle(r) fillColor fill)

def randomConcentricCircles(n: Int): Random[Image] =
  n match {
    case 0 => randomCircle(10, randomPastel)
    case n =>
      (randomConcentricCircles(n-1), randomCircle(n * 10, randomPastel)) mapN {
        (circles, circle) => circles on circle
      }
  }

val circles = randomConcentricCircles(5)
val programOne = 
  (circles, circles, circles) mapN { (c1, c2, c3) => c1 beside c2 beside c3 }
val programTwo =
  circles map { c => c beside c beside c }
```

<div class="solution">
`programOne` displays three different circles in a row, while `programTwo` repeats the same circle three times. The value `circles` represents a program that generates an image of randomly colored concentric circles. Remember `map` represents a deterministic transform, so the output of `programTwo` must be the same same circle repeated thrice as we're not introducing new random choices. In `programOne` we merge `circle` with itself three times. You might think that the output should be only one random image repeated three times, not three, but remember `Random` preserves substitution. We can write `programOne` equivalently as

```tut:book
val programOne = 
  (randomConcentricCircles(5), randomConcentricCircles(5), randomConcentricCircles(5)) mapN { 
    (c1, c2, c3) => c1 beside c2 beside c3 
  }
```

which makes it clearer that we're generating three different circles.


#### Colored Boxes {-}

Let's return to a problem from the beginning of the book: drawing colored boxes. This time we're going to make the gradient a little more interesting, by making each color randomly chosen.

Recall the basic structural recursion for making a row of boxes

```tut:book
def rowOfBoxes(n: Int): Image =
  n match {
    case 0 => rectangle(20, 20)
    case n => rectangle(20, 20) beside rowOfBoxes(n-1)
  }
```

Let's alter this, like with did with concentric circles, to have each box filled with a random color. *Hint:* you might find it useful to reuse some of the utilities we created for `randomConcentricCircles`. Example output is shown in [@fig:generative:random-color-boxes].

![Boxes filled with random colors.](./src/pages/generative/random-color-boxes.png){#fig:generative:random-color-boxes}

<div class="solution">
This code uses exactly the same pattern as `randomConcentricCircles`.

```tut:book
import cats.syntax.all._

val randomAngle: Random[Angle] =
  Random.double.map(x => x.turns)

val randomColor: Random[Color] =
  randomAngle map (hue => Color.hsl(hue, 0.7.normalized, 0.7.normalized))

def coloredRectangle(color: Color): Image =
  rectangle(20, 20) fillColor color
  
val randomColorBox: Random[Image] = randomColor.map(c => coloredRectangle(c))

def randomColorBoxes(n: Int): Random[Image] =
  n match {
    case 0 => randomColorBox
    case n =>
      val box = randomColorBox
      val boxes = randomColorBoxes(n-1)
      (box, boxes) mapN { (b, bs) => b beside bs }
  }
```
</div>

</div>

#### Structured Randomness {-}

We've gone from very structured to very random pictures. It would be nice to find a middle ground that incorporates elements of randomness and structure. 


[cats]: http://typelevel.org/cats
[idiom]: http://strictlypositive.org/Idiom.pdf
