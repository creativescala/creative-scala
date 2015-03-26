## Extended Exercise: Color Palettes, Again

In this chapter we have seen how to create complex pictures using recursion.
In the previous chapter we looked at creating attractive color palettes. 
In this exercise we combine these two ideas,
and explore some interesting concepts from mathematics and computer science.

### Coloring Concentric Circles

We saw that we can create attractive color palettes using complementary and analogous colors. We can combine these ideas with our programs to draw concentric circles giving us pictures like the below.

![Concentric circles using a palette of complementary colors](src/pages/fp/complement-circles.png)

Our definitions of `complement` and `analogous` were:

~~~ scala
def complement(color: Color): Color =
  color.spin(180.degrees)

def analogous(color: Color): Color =
  color.spin(15.degrees)
~~~

Implement a variant on `concentricCircles` called `complementCircles` that renders concentric circles with complementary colors. Hint: you might use a signature for `complementCircle` like `def complementCircle(n: Int, c: Color): Image` and change `singleCircle` to accept a `Color` parameter.

<div class="solution">
My implementation looks like

~~~ scala
def singleCircle(n: Int, color: Color): Image =
  Circle(50 + 10 * n) lineColor color lineWidth 10

def complementCircles(n: Int, c: Color): Image = {
  val color = complement(c)
  if(n == 1) {
    singleCircle(n, color)
  } else {
    complementCircles(n - 1, color) on singleCircle(n, color)
  }
}
~~~

Given the implementation of `complement` it is natural to pass around the current color, which we transform every iteration. Strictly speaking we don't need the current color, just the iteration number `n`. We can use the base color when `n` is odd and its complement when `n` is even. We still need to known the base color, however, so this isn't going to save us passing around that parameter ... unless we use nested method declarations. We can write a method that, given a base color, returns a function that creates an image of concentric circles like so:

~~~ scala
def makeComplementCircles(baseColor: Color): Int => Image = {
  val complementColor = complement(base)

  def singleCircle(n: Int): Image = {
    val color = if(n % 2 == 0) baseColor else complementColor

    Circle(circleMinimum + circleIncrement * n) lineColor color lineWidth circleIncrement
  }

  def complementCircles(n: Int): Image =
    if(n == 1) {
      singleCircle(n)
    } else {
      complementCircles(n - 1) on singleCircle(n)
    }

  complementCircles _
}
~~~

This works because `baseColor` is declared in code that surrounds the declaration of `complementCircle`. The declarations that are visible at a point in the program are called the declarations in *scope*. Scala is a *lexically scoped* language, meaning that scope is determined by purely by the structure of the program text. Specifically, the declarations that are in scope are those declarations that are made within code blocks that enclose the current place (including declarations imported into scope; something we haven't discussed). The important upshot of this is that `complementCircles` maintains its scope even when it is returned as a value from a call to `makeComplementCircles`. This behaviour is essential to maintaining the substitution model.

The decision of which color to use is made by the line `if(n % 2 == 0) baseColor else complementColor`. The `%` method, called the [modulo](http://en.wikipedia.org/wiki/Modulo_operation), returns the remainder of dividing `n` by 2. This will be `0` if `n` is even, and `1` otherwise.
</div>

### Never Being Boring

There is a problem with these color palettes -- they are a bit boring. With only a few colors to play with our pictures quickly get repetitious. What if we wanted to make like Kandinsky and really rock out with the palettes? We'll have to do something a bit more involved.

If we rotate hue by 170 degrees, instead of the 180 degree rotation we perform in `complement`, we will generate many more colors before we get a repeat.

Implement a method `nearComplement` that creates a nearly complementary color using a spin of 170 degrees. Then implement `nearComplementCircles` that renders concentric circles using a nearly complementary color scheme.

<div class="solution">
This code is very similar to `complement and `complementCircles`.

~~~ scala
def nearComplement(c: Color): Color =
  c.spin(170.degrees)

def nearComplementCircles(n: Int, c: Color): Image = {
  val color = nearComplement(c)
  if(n == 1) {
    singleCircle(n, color)
  } else {
    nearComplementCircles(n - 1, color) on singleCircle(n, color)
  }
}
~~~
</div>

The code for a nearly complementary color scheme is exactly the same as that for a complementary color scheme except for a single change (the choice of color scheme). Surely we avoid this duplication? Write a method `coloredCircles` that renders concentric circles using a color scheme provided by the user. Hint: what are we trying to abstract over, and what tool do we use to make this abstraction?

<div class="solution">
`nearComplementCircles` and `complementCircles` differ only in the method they call to generate the color palette. We have just learned that we can abstract over methods using functions, and that is a natural abstraction to make in `coloredCircles`. Here's my implementation:

~~~ scala
def coloredCircles(n: Int, c: Color, palette: Color => Color) = {
  val color = palette(c)
  if(n == 1) {
    singleCircle(n, color)
  } else {
    coloredCircles(n - 1, color, palette) on singleCircle(n, color)
  }
}
~~~
</div>


It's natural to ask how many colors we will get before the pattern starts repeating. This quantity is known as the *period* of the sequence. We can answer this question by looking at the *greatest common divisor* (GCD) of 360 degrees (a full rotation of the circle) and the rotation we apply to hue. The greatest common divisor of two positive integers is the largest integer that divides both without a remainder. The GCD of 360 and 180 is 180. If we divide 360 by 180 we get 2, which is the number of colours in the palette that `complement` creates, or in other words its period. If we try to use more than two colors we will find it starts to repeat. What about 360 and 170?

We could exhaustively search to find the GCD, but there is a better way known as Euclid's algorithm[^euclid]. Euclid's algorithm is very simple to state:

~~~
gcd(a, 0) = a
gcd(a, b) = gcd(a - b, b), if a > b
gcd(a, b) = gcd(a, b - a), if b > a
~~~

[^euclid]: Euclid was a Greek mathematician who lives around 300BC. His algorithm for finding the greatest common divisor is one of the oldest known numeric algorithms. How cool is it to link the ancient and modern worlds like this?

Implement Euclid's algorithm in Scala.

<div class="solution">
The code is almost a direct translation of the algorithm.

~~~ scala
def gcd(a: Int, b: Int): Int =
  if(b == 0)
    a
  else if(a > b)
    gcd(a - b, b)
  else
    gcd(a, b - a)
~~~~
</div>

If we rotate hue by 170 degrees, how many colors are there in the palette?

<div class="solution">
`gcd(360, 170)` is 10, so we get 36 colors (360 / 10) before the sequence repeats.
</div>

How many colours can we possibly generate? Given we're (implicitly) restricting hue to be an integer, there are only 360 in a circle. If we want to generate near complementary colors but still have 360 colors before repeats, by how many degrees should we rotate hue? You might be able to reason your way to an answer, but we can ask the computer in any case. What we are looking for is the number closest to 180 that is *relatively prime* or *coprime* with 360. This means the GCD between this number and 360 is 1. We can search over the numbers less than 180 till we find it (we don't have to look at numbers greater than 180 because they are equivalent to a smaller rotation in the opposite direction). Find this number.

<div class="solution">
The answer is to rotate by 179 degrees. We can prove this is the case with the following proceed that searches for the largest coprime number, calling it as `coprime(360, 180)`.

~~~ scala
def coprime(base: Int, start: Int): Int =
  if(start == 0)
    start
  else if(gcd(base, start) == 1)
    start
  else coprime(base, start - 1)
~~~
</div>

Is this really the maximum number of colors we can generate? No, because

1. hue is not restricted to being an integer; and
2. we can also adjust saturation and lightness.

Before we look at changing saturation and lightness lets talk about fractional hues. Internally a computer represents colors as a triple of red, green, and blue components. Each component uses 8 bits. This gives a total of 24 bits of resolution, yielding 2^24 = 16777216 colors in total[^24-bit]. In Doodle we represent hue, saturation, and lightness as a `Double`, with 64 bits of precision each. This gives us a total 192 bits. Clearly we can't retain all the information in 192 bits if we only have 24 bits in the "true" representation of colors that the computer uses. Thus in most cases fractional hues are going to yield no discernable change in color.

[^24-bit]: If you are old enough you might remember when computers could only render considerably fewer colors. The Amiga, for example, could render 4096 colors. The VGA graphics standard, popular in early 90s, could display 256 colors in a stunning 320x240 resolution. There are about this many pixels in a square inch on a modern phone. 

We know how to manipulate hue so we can get interesting changes in color. What about hue and lightness. They are numbers between 0 and 1, but we could use the same trick of "wrap-around" or modulo arithmetic to generate new values. We don't want changes in the three color components to be correlated. That is, we don't want all the reds to have similar saturation and lightness, or the greens, or the blues. We would like colors that are close in hue to be different in saturation and lightness. We could implement this by using rotations with different periods for each of hue, lightness, and saturation. This simple idea is an example of a more general concept in Computer Science, the hash function, with many useful applications. Let's take a look at hash functions.

A *hash function* is a kind of function that transforms its inputs in a way that is difficult to predict. The output of a good hash function is uncorrelated, meaning if we know the output of the hash function for one input this knowledge doesn't help predict the output for other inputs.

We can use a hash function to transform hue into saturation and lightness and, if we choose a good hash function, colors that are similar in hue will not be similar in the two other attributes. Designing good hash functions is a complex topic but we can get acceptable results for our purposes using a very simple class of hash functions called *linear congruential generators*.

A linear congruential generator has the form

~~~ scala
output = (a * input + c) % m
~~~

for some input and constants `a`, `c`, and `m`, where:

- the input and constants are all integers; and
- `a` < `m` and `c` < `m`.

The output of a linear congruential generator is an integer. We will discuss how to convert it to a number between 0 and 1 in a bit.

Implement a linear congruential generator in Scala, choosing values for `a`, `c`, and `m`. Your method should have the signature `lcg(input: Int): Int`. Explore the effect of different values for the constants. In particular look at:

- constants that have a GCD close to `m`;
- constants that are coprime, and
- patterns of odd and even numbers in the output if you use the output of `lcg` as its input.

You might find it simplest to set `c` to zero while you investigate the behavior of the other two values.

<div class="solution">
The implementation of `lcg` should be straightforward, but there is considerable variability in the results depending on the choice of `a`, `c`, and `m`. Here is an implementation of `lcg` with a choice of constants that will generate numbers with a distribution that is usable for our purposes.

~~~ scala
def lcg(input: Int): Int = {
  // These values of a, c, and m come from the Wikipedia page
  // on linear congruential generators and are reported to
  // be from Knuth. This a rather short period, but for our
  // purposes any period greater than 256 (2^8) will
  // generate sufficient results.
  val a = 8121
  val c = 28411
  val m = 134456
  (a * input + c) % m 
}
~~~

This implementation will generate alternatively odd and even outputs if we feed the output into the input. This lack of variability in the lower-order bits is one well known problem with this algorithm. The [Wikipedia page](http://en.wikipedia.org/wiki/Linear_congruential_generator) lists a number of other properties, which go beyond what we have time to explore, but we can make some elementary observations.

Fundamentally, a linear congruential generator is very similar to the hue manipulation we've just discussed. We can view `a` as a rotation around a circle with `m` elements. The constant `c` just acts as an offset to the rotation.  Thus if `a` and `m` are coprime our generator will have a large period before the pattern starts to repeat. If the GCD of `a` and `m` is close to `m` the pattern will repeat after only a few numbers.

A linear congruential generator will never output a number greater than `m`. A signed `Int` (32-bits), can represent numbers from -2147483648 to 2147483647, so the choice of `m` above means the output won't cover the full range of `Ints`. That's ok, as we're only inputing hues from 0 to 360, and we're converting the output to a number between 0 and 1. Since the maximum output of the linear congruential generator is `m`, we can simply divide by `m` to scale the output to be between 0 and 1.

We can see that similar inputs to the linear congruential generator will still generate similar outputs, unless they happen to straddle a boundary around `m`. With the choice of constants above each degree of hue will result in a (8121 / 134456) = 0.06 = 6% change in output. A 360 degree change of hue will result in (8121 * 360 / 134456) = 21 periods. These numbers seem alright -- a one degree change of hue should give a perceptable change in output, and the period is short enough that moderate changes in hue will lead to widely different lightness and saturation. Of course the proof is in the result, so let's see what it looks like!
</div>

With our linear congruential generator we can now transform hue into saturation and lightness. There are two ways to do this: we can either create two generators with different constants, or we can apply the generator once to generate saturation from hue, and again to generate lightness from saturation. For either strategy we need to convert the `Int` that our generator returns into a `Normalized` value. Since the maximum value our generator can return is `m` we can simply divide the output by `m` (after converting to a `Double`) to get a value between zero and one.

Implement a method `normalize` that accepts two `Ints` (the current value and maximum) and returns a `Normalized`. Hint: use the method `toDouble` to convert an `Int` to a `Double`.

<div class="solution">
~~~ scala
def normalize(value: Int, max: Int): Normalized =
  (value.toDouble / max.toDouble).normalized
~~~
</div>

<div class="callout callout-warn">
*Implement two lcgs with different constants*
*Implement Color => Color function using these transforms*
*Implement rescale to change the range of our output to be in useful range*
*Draw pictures*
*Compare to Scala's built-in hash functions. Do you see an appreciable difference?*
</div>
