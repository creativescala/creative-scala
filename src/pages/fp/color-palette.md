## Extended Exercise: Color Palettes, Again

In this chapter we have seen how to create complex pictures using recursion.
In the previous chapter we looked at creating attractive color palettes. 
In this exercise we combine these two ideas,
and explore some interesting concepts from mathematics and computer science.

### Coloring Concentric Circles

We saw that we can create attractive color palettes using complementary and analogous colors. We can combine these ideas with our programs to draw concentric circles giving us pictures like the below.

<div class="callout callout-danger">
// Description here. First class function closing over local scope

// blah ... blah
</div>

### Never Being Boring

There is a problem with these color palettes -- they are a bit boring. With only a few colors to play with our pictures quickly get repetitious. What is we want to make like Kandinsky and really rock out with the palettes? We'll have to do something a bit more involved.

If we rotate hue by 170 degrees, instead of the 180 degree rotation we perform in `complement`, we will generate many more colors before we get a repeat.

<div class="callout callout-danger">Implement this</div>

It's natural to ask how many colors we will get before they start repeating. We can answer this question by looking at the *greatest common divisor* (GCD) of 360 degrees (a full rotation of the circle) and the rotation we apply to hue. The greatest common divisor of two positive integers is the largest integer that divides both without a remainder. The GCD of 360 and 180 is 180. If we divide 360 by 180 we get 2, which is the number of unique colours that `complement` gives us before a repeat. What about 360 and 170?

We could exhaustively search to find this number, but there is a better way known as Euclid's algorithm[^euclid]. Euclid's algorithm is very simple to state:

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

If we rotate hue by 170 degrees, how many unique colors will we generate before we start to repeat?

<div class="solution">
`gcd(360, 170)` is 10, so we get 36 unique colors (360 / 10) before repeats.
</div>

How many colours can we possibly generate? Given we're dealing with integer hues there are only 360 in a circle. If we want to generate near complementary colors but still have 360 colors before repeats, by how many degrees should we rotate hue? You might be able to reason your way to an answer, but we can ask the computer in any case. What we are looking for is the number closest to 180 that is *relatively prime* or *coprime* with 360. This means that GCD between this number and 360 is 1. We can search over the numbers less than 180 till we find it (we don't have to look at numbers greater than 180 because they are equivalent to a smaller rotation in the opposite direction). Find this number.

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

Before we look at changing saturation and lightness let's just consider why looking at fractional hues is not that useful. Internally a computer represents colors as a triple of red, green, and blue components with 8 bits each. This gives a total of 24 bits of resolution, yielding 2^24 = 16777216 colors in total[^24-bit]. We represent hue, saturation, and lightness with 64 bits of precision each, giving a total 192 bits. Clearly we can't retrain all this information when we go down to the 24 bits actually used to talk to the graphics card. Thus in most cases fractional hues are going to yield no discernable change in color.

[^24-bit]: If you are old enough you might remember when computers could only render considerably fewer colors. The Amiga, for example, could render 4096 colors. The VGA graphics standard, popular in early 90s, could display 256 colors in a stunning 320x240 resolution. There are about this many pixels in a square inch on a modern phone. 

We know how to manipulate hue so we can get interesting changes in color. What about hue and lightness. They are numbers between 0 and 1, but we could use the same trick of "wrap-around" arithmetic to generate new values. There is a problem with this, however. We don't want changes in the three color components to be correlated. That is, we don't want all the reds to have similar saturation and lightness, or the greens, or the blues. We would like colors that are close in hue to be different in saturation and lightness.

A *hash function* is a kind of function that transforms its inputs in a way that is difficult to predict. The output of a good hash function is uncorrelated, meaning if we know the output of the hash function for one input this knowledge doesn't help predict the output for other input.

We can use a hash function to transform hue into saturation and lightness and, if we choose a good hash function, colors that are similar in hue will not be similar in the two other attributes. Designing good hash functions is a complex topic but we can get acceptable results for our purposes using a very simple class of hash functions called *linear congruential generators*.

A linear congruential generator has the form

~~~ scala
output = (a * input + c) % m
~~~

for some constants `a`, `c`, and `m`.

Implement a linear congruential generator in Scala, choosing values for `a`, `c`, and `m`. Your method should have the signature `lcg(input: Int): Int`. Explore the effect of different values for the constants. In particular look at:

- constants that have a large GCD;
- constants that are coprime, and
- patterns of odd and even numbers in the output if you use the output of `lcg` as its input.

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

This implementation will generate alternatively odd and even outputs if we feed the output into the input. This lack of variability in the lower-order bits is one well known problem with this algorithm. The Wikipedia pages lists a number of other properties, which go beyond what we have time to explore, but we can make some elementary observations.
</div>


Choose some values `a`, `b`, and `c` and implement this. Pro-tip: use relatively prime numbers. Does it work well. (Scaling results to between 0 and 1.)

Compare to Scala's built-in hash functions. Do you see an appreciable difference?