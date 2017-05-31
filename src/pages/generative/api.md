## Exploring Random

```tut:invisible
import doodle.core._
import doodle.core.Image._
import doodle.syntax._
import doodle.jvm.Java2DFrame._
import doodle.backend.StandardInterpreter._
```

So far we've seen only the very basics of using `Random`. In this section we'll see more of its features, and use these features to create more interesting pictures. 

<div class="callout callout-info">
In addition to the standard imports given at the start of the chapter, in this section we're assuming the following:

```tut:silent
import doodle.random._
```
</div>

### Normal Distributions

Often when using random numbers in generative art we will choose specific distributions for the shape they provide. 
For example, [@fig:generative:distributions] shows a thousand random points generated using a uniform, normal (or Gaussian) distribution, and a squared normal distribution respectively.

![Points distributed according to uniform, normal, and squared normal distributions](./src/pages/generative/distributions.pdf+svg){#fig:generative:distributions}

As you can see, the normal distribution tends to generate more points nearer the center than the uniform distribution.

Doodle provides two methods to create normally distributed numbers, from which we can create many other distributions. 
A normal distribution is defined by two parameters, it's *mean*, which specifies the center of the distribution, and it's *standard deviation*, which determines the spread of the distribution. 
The corresponding methods in Doodle are

- `Random.normal`, which generates a `Double` from a normal distribution with mean 0 and standard deviation 1.0; and
- `Random.normal(mean, stdDev)`, which generates a `Double` from a normal distribution with the specified mean and standard deviation.


### Structured Randomness

We've gone from very structured to very random pictures. 
It would be nice to find a middle ground that incorporates elements of randomness and structure. 
We can use `flatMap` to do this---with `flatMap` we can use one randomly generated value to create another `Random` value.
This creates a dependency between values---the prior random value has an influence on the next one we generate.

For example, we can create a method that given a color randomly perturbs it.

```tut:silent:book
def nextColor(color: Color): Random[Color] = {
  val spin = Random.normal(15.0, 10.0)
  spin map { s => color.spin(s.degrees) }
}
```

Using `nextColor` we can create a series of boxes with a gradient that is partly random and partly structured: the next color in the gradient is a random perturbation of the previous one.

```tut:silent:book
def coloredRectangle(color: Color, size: Int): Image =
  rectangle(size, size).
    lineWidth(5.0).
    lineColor(color.spin(30.degrees)).
    fillColor(color)

def randomGradientBoxes(count: Int, color: Color): Random[Image] =
  count match {
    case 0 => Random.always(Image.empty)
    case n =>
      val box = coloredRectangle(color, 20)
      val boxes = nextColor(color) flatMap { c => randomGradientBoxes(n-1, c) }
      boxes map { b => box beside b }
  }
```

Example output is shown in [@fig:generative:structured-gradient-boxes].

![Boxes filled with gradient that is partly random.](./src/pages/generative/structured-gradient-boxes.pdf+svg){#fig:generative:structured-gradient-boxes}

### Exercises {-}

#### Particle Systems {-}

A *particle system* is a technique used in computer graphics to create large numbers of "particles" that move according to simple rules.
In [@fig:generative:smoke] there is an example of a particle system simulating a fire and smoke.
For the mathematically inclined, a particle system is basically a *stochastic process* or *random walk*.

![A simulation of a smoky fire, generating using a particle system.](src/pages/generative/smoke.pdf+svg){#fig:generative:smoke}

In this exercise we'll build a particle system, which will give you a flexible system to experiment with these ideas.
We'll start with a fixed system and then abstract it to create reusable components.

Here's a sketch of how a particle system works.
To draw a single particle we

- choose a starting position;
- decide how many time steps we want to move the particle system for; and
- at each timestep the new position of the particle is equal to the position at the previous timestep plus some random noise (and potentially some non-random (deterministic) movement such as velocity or acceleration).

A particle system is just a collection of a number of particles---20 particles over 20 steps in [@fig:generative:smoke].

In the above description we've broken down the components of a partcile system.
Now we just need to implement them.

The starting position can be any `Random[Point]`.
Create one now.

<div class="solution">
This will do.
You can create a more complicated (and interesting) distribution over starting position if you want.

```tut:silent:book
val start = Random.always(Point.zero)
```
</div>

Let's implement a method `step` that will take a single step in particle system.
It will have skeleton

```tut:silent:book
def step(current: Point): Random[Point] =
  ???
```

We need to decide how we will modify the `current` point to create the next point.
I suggest adding some random noise, and a constant "drift" that will ensure the points tend to move in a particular direction.
For example, we could increment the `x` coordinate by 10, which will cause a drift towards the right of the screen, plus some normally distributed noise to the `x` and `y` coordinates.

<div class="solution">
I've chosen to use normally distributed noise that is the same in both directions.
Changing the noise will change the shape of the result---it's worth playing around with different settings.

```tut:silent:book
def step(current: Point): Random[Point] = {
  val drift = Point(current.x + 10, current.y)
  val noise = 
    Random.normal(0.0, 5.0) flatMap { x =>
      Random.normal(0.0, 5.0) map { y =>
        Vec(x, y)
      }
    }
    
  noise.map(vec => drift + vec)
}
```
</div>

Now that we can `step` a particle we need to connect a sequence of steps to get a `walk`.
There is one wrinkle here: we want to draw the intermediate stages so we're going to define two methods:

- a method that transforms a `Point` to an `Image`; and
- a method `walk` that produces a `Random[Image]`

The skeletons are

```tut:silent:book
def render(point: Point): Image =
  ???
  
def walk(steps: Int): Random[Image] =
  ???
```

The implementation of `render` can be whatever you fancy.
In the implementation of `walk`, you will have to call `step` to get the next `Point`, and then call `render` to convert the point to something that can be draw.
You will also want to have an accumulator of the `Image` so far.
Hint: you might find it useful to define an auxillary parameter for `walk`.

<div class="solution">
In my definition of `render` I've shown how we can use information from the point to modify the shape in an interesting way.

The definition of `walk` is a structural recursion over the natural numbers with an internal accumulator and the recursion going through `flatMap`.

```tut:silent:book
def render(point: Point): Image = {
  val length = (point - Point.zero).length
  val sides = (length / 20).toInt + 3
  val hue = (length / 200).turns
  val color = Color.hsl(hue, 0.7.normalized, 0.5.normalized)
  Image.
    star(sides, 5, 3, 0.degrees).
    noFill.
    lineColor(color).
    at(point.toVec)
}

def walk(steps: Int): Random[Image] = {
  def loop(count: Int, current: Point, image: Image): Random[Image] = {
    count match {
      case 0 => Random.always(image on render(current))
      case n =>
        val next = step(current)
        next flatMap { pt =>
          loop(count - 1, pt, image on render(current))
        }
    }
  }
 
  start flatMap { pt => loop(steps, pt, Image.empty) }
}
```
</div>

Now you should be able to call `walk` and render the result.

The final step is create a number of particles and render them all.
Create a method `particleSystem` with skeleton

```tut:silent:book
def particleSystem(particles: Int, steps: Int): Random[Image] =
  ???
```

that does just this.

<div class="solution">
Once again we have a structural recursion over the natural numbers.
Unlike `walk` the recursion goes through `map`, not `flatMap`. 
This is because `particleSystem` adds no new random choices.

```tut:silent:book
def particleSystem(particles: Int, steps: Int): Random[Image] = {
  particles match {
    case 0 => Random.always(Image.empty)
    case n => walk(steps) flatMap { img1 => 
      particleSystem(n-1, steps) map { img2 => 
        img1 on img2
      }
    }
  }
}
```
</div>

Now render the result, and tweak it till you have something you're happy with.
I'm not particulary happy with the result of my code. 
I think the stars are too bunched up, and the colors are not very interesting.
To make a more interesting result I'd consider adding more noise and changing the start color and perhaps compressing the range of colors.

#### Random Abstractions {-}

The implementation of `particleSystem` above hard-codes in a particular choice of particle system.
To make it easier to experiment with we might like to abstract over the particular choice of `walk` and `start`.
How do you think we could do this?

<div class="solution">
We could make `walk` `start`, and `render` parameters to `particleSystem`, and make `start` and `render` parameters to `walk`.
</div>

Implement this.

<div class="solution">
If we add parameters with the correct name and type the code changes required are minimal.
This is like doing the opposite of substitution---lifting concrete representations out of our code and replacing them with method parameters.

```tut:silent:book
def walk(
  steps: Int, 
  start: Random[Point],
  render: Point => Image
): Random[Image] = {
  def loop(count: Int, current: Point, image: Image): Random[Image] = {
    count match {
      case 0 => Random.always(image on render(current))
      case n =>
        val next = step(current)
        next flatMap { pt =>
          loop(count - 1, pt, image on render(current))
        }
    }
  }
 
  start flatMap { pt => loop(steps, pt, Image.empty) }
}

def particleSystem(
  particles: Int, 
  steps: Int,
  start: Random[Point],
  render: Point => Image,
  walk: (Int, Random[Point], Point => Image) => Random[Image]
): Random[Image] = {
  particles match {
    case 0 => Random.always(Image.empty)
    case n => walk(steps, start, render) flatMap { img1 => 
      particleSystem(n-1, steps, start, render, walk) map { img2 => 
        img1 on img2
      }
    }
  }
}
```
</div>

This code doesn't make me happy.
Most of the parameters to `particleSystem` are only needed to pass on to `walk`.
These parameters don't change is any way within the structural recursion that makes up the body of `particleSystem`.
At this point we can apply our principle of substitution---we can replace a method call with the value it evaluates to---to remove `walk` and associated parameters from `particleSystem`.

```tut:silent:book
def particleSystem(particles: Int, walk: Random[Image]): Random[Image] = {
  particles match {
    case 0 => Random.always(Image.empty)
    case n => walk flatMap { img1 => 
      particleSystem(n-1, walk) map { img2 => 
        img1 on img2
      }
    }
  }
}
```

If you're used to programming in imperative languages this may seem mind-bending.
Remember that we've gone to some lengths to ensure that working with random numbers obeys substitution, up to the point that `run` is called.
The `walk` method doesn't actually create a random walk.
It instead describes how to create a random walk when that code is actually run.
This separation between description and action means that substitution can be used.
The description of how to perform a random walk can be used to create many different random walks.
