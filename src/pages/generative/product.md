## Combining Random Values

```tut:invisible
import doodle.core._
import doodle.core.Image._
import doodle.syntax._
import doodle.jvm.Java2DFrame._
import doodle.backend.StandardInterpreter._
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
import cats.syntax.cartesian._
```

we can now write

```scala
randomConcentricCircles(n-1) |@| (randomCircle(n*10, randomPastel)) map { 
  (circles, circle) => circles on circle
}
```

The complete code becomes

```tut:book
import cats.syntax.cartesian._

val randomPastel = randomColor(0.7.normalized, 0.7.normalized)

def randomConcentricCircles(n: Int): Random[Image] =
  n match {
    case 0 => randomCircle(10, randomPastel)
    case n =>
      randomConcentricCircles(n-1) |@| randomCircle(n * 10, randomPastel) map {
        (circles, circle) => circles on circle
      }
  }
```

Example output is shown in [@fig:generative:random-concentric-circles].

![The output of one run of `randomConcentricCircles(10).run().draw`](./src/pages/generative/random-concentric-circles.png){#fig:generative:random-concentric-circles}

So what is this strange `|@|`, how does it work, and most importantly, what do we call it? We now turn to these issues.


### The Product Operator

I call `|@|` the *product operator*, or occasionally the *TIE fighter*. You might see other names used (but they are wrong, for estoric reasons[^esoteric]).

Using the same box notation we saw in the previous chapter we can describe the action of the product operator with [@fig:generative:applicative]. This tells us that the product operator merges together boxes and elements. What exactly do we mean by merging? Let's see an example using `List` to help clear it up.

![The product operator illustrated with boxes and shapes](./src/pages/generative/applicative.png){#fig:generative:applicative}

We can use the product operator with `List` as shown below. Before doing so, think about what you'd expect if we merge two lists using `|@|`. According to our box diagram we should get a `List` where is each element is the result of merging elements from the two lists (and we haven't exactly defined what this merging is).

```tut:book
import cats.instances.list._
val merged = List(1, 2, 3) |@| List(4, 5)
```

This isn't giving us much insight. What is the type `CartesianBuilder` doing here? For technical reasons these things exist in the implementation of product. We can get back a result that coincides with our model if we call the `tupled` method.

```tut:book
val merged = (List(1, 2, 3) |@| List(4, 5)).tupled
```

This makes more sense! The result is a `List` where the first element of the left-hand list has been paired with all the elements of the right-hand list, then the second element, and so on.

How exactly have the elements been paired? They are stored in an object called a *tuple*. A tuple is just a container that lets us store a fixed number of elements of different types together. Here are some examples.

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

This explains why we saw the `CartesianBuilder` when we first used the product operator---it converts tuples into function calls like `tupleToF` above.

That's quite a lengthy explanation. The good news is we don't ever run into this if we just immediately `map` over the result of using the product operator, which is the usual case.

[^esoteric]: You really want to know the estorica? Ok! There are four operators associated with the "applicative functor", the abstraction we are using. They are `|@|`, `<*>`, `*>`, and `<*`. The first two are clearly a TIE figher and a TIE interceptor respectively, and the latter two are a TIE interceptor after tangling with the Millenium Falcon (as at the end of A New Hope.) These goofy symbols are mostly found in [the paper][idiom] that introduced the concept but failed to make the connection to Star Wars.


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
      randomConcentricCircles(n-1) |@| randomCircle(n * 10, randomPastel) map {
        (circles, circle) => circles on circle
      }
  }

val circles = randomConcentricCircles(5)
val programOne = 
  (circles |@| circles |@| circles) map { (c1, c2, c3) => c1 beside c2 beside c3 }
val programTwo =
  circles map { c => c beside c beside c }
```

<div class="solution">
`programOne` displays three different circles in a row, while `programTwo` repeats the same circle three times. The value `circles` represents a program that generates an image of randomly colored concentric circles. Remember `map` represents a deterministic transform, so the output of `programTwo` must be the same same circle repeated thrice as we're not introducing new random choices. In `programOne` we merge `circle` with itself three times. You might think that the output should be only one random image repeated three times, not three, but remember `Random` preserves substitution. We can write `programOne` equivalently as

```tut:book
val programOne = 
  (randomConcentricCircles(5) |@| randomConcentricCircles(5) |@| randomConcentricCircles(5)) map { 
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
val randomAngle: Random[Angle] =
  Random.double.map(x => x.turns)

val randomColor: Random[Color] =
  randomAngle map (hue => Color.hsl(hue, 0.7.normalized, 0.7.normalized))

def coloredRectangle(color: Color): Image =
  rectangle(20, 20) fillColor color

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

</div>

#### Structured Randomness {-}

We've gone from very structured to very random pictures. It would be nice to find a middle ground that incorporates elements of randomness and structure. 


[cats]: http://typelevel.org/cats
[idiom]: http://strictlypositive.org/Idiom.pdf
