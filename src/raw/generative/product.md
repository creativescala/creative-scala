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

Note that `randomConcentricCircles` returns a `Random[Image]`.

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

We can use the product operator with `List` as shown below.

```tut:book
import cats.std.list._
val merged = List(1, 2, 3) |@| List(4, 5)
```

This isn't giving us much insight. 

To start thinking about the product operator let's return to lists, which are a bit more concrete than `Random`. We know a list contains zero or more elements. We can abstract away details to just the type `List[A]`, being a list containing zero or more elements of type `A`. We can draw this in a picture using the boxes and circles notation

[^esoteric]: You really want to know the estorica? Ok! There are four operators associated with the "applicative functor", the abstraction we are using. They are `|@|`, `<*>`, `*>`, and `<*`. The first two are clearly a TIE figher and a TIE interceptor respectively, and the latter two are a TIE interceptor after tangling with the Millenium Falcon (as at the end of A New Hope.) These goofy symbols are mostly found in [the paper][idiom] that introduced the concept but failed to make the connection to Star Wars.

[cats]: http://typelevel.org/cats
[idiom]: http://strictlypositive.org/Idiom.pdf
