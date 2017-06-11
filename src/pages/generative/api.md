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
      val box = coloredRectangle(color)
      val boxes = nextColor(color) flatMap { c => randomGradientBoxes(n-1, c) }
      boxes map { b => box beside b }
  }
```

Example output is shown in [@fig:generative:structured-gradient-boxes].

![Boxes filled with gradient that is partly random.](./src/pages/generative/structured-gradient-boxes.pdf+svg){#fig:generative:structured-gradient-boxes}


### For Comprehensions

*TODO*
