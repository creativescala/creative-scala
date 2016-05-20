## Exploring Random

```tut:invisible
import doodle.core._
import doodle.core.Image._
import doodle.syntax._
import doodle.jvm.Java2DCanvas._
import doodle.backend.StandardInterpreter._
```

So far we've seen only the very basics of using `Random`. In this section we'll see more of its features, and use these features to create more interesting pictures. 

<div class="callout callout-info">
In addition to the standard imports given at the start of the chapter, in this section we're assuming the following:

```tut:silent
import doodle.random._
```
</div>

### Basic Distributions

Some of the most basic ways of generating `Random` values are:

- `Random.always(value)`, which constructs a `Random` that always generates the given value.

```tut:book
val starman = Random.always("Starman")
starman.run()
starman.run()
```

- `Random.oneOf(value, ...)`, which chooses one of its parameters with equal probability. If all values are equally likely we say they are uniformly distributed.

```tut:book
val doRayMe = Random.oneOf("do", "ray", "me")
doRayMe.run()
doRayMe.run()
```

- `Random.int`, which generates a uniformly distributed `Int`.

```tut:book
val int = Random.int
int.run()
int.run()
```

- `Random.natural(upperLimit)`, which generates a uniformly distrbuted `Int` between 0 and `upperLimit - 1` inclusive.

```tut:book
val nat = Random.natural(5)
nat.run()
nat.run()
```

- `Random.double`, which generates a uniformly distrbuted `Double` between 0.0 and 1.0.

```tut:book
val double = Random.double
double.run()
double.run()
```


### Complex Distributions

We can also create more complex distributions. Often when using random numbers in generative art, we will choose specific distributions for the shape they provide. For example, [@fig:generative:distributions] shows a thousand random points generated using a uniform, normal (or Gaussian) distribution, and a squared normal distribution respectively.

![Points distributed according to uniform, normal, and squared normal distributions](./src/pages/generative/distributions.png){#fig:generative:distributions}

Doodle provides two methods to create normally distributed numbers, from which we can create many other distributions. A normal distribution is defined by two parameters, it's *mean*, which specifies the center of the distribution, and it's *standard deviation*, which determines the spread of the distribution. The corresponding methods in Doodle are

- `Random.normal`, which generates a `Double` from a normal distribution with mean 0 and standard deviation 1.0; and
- `Random.normal(mean, stdDev)`, which generates a `Double` from a normal distribution with the specified mean and standard deviation.
