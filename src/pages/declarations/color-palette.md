## Extended Exercise: Color Palettes

In this exercise we will explore the creation of color palettes.
An attractive picture must make good choices for color.
Color theory has developed to explain combinations of color that go together.
We will use color theory,
and some neat tricks from mathematics and computer science,
to create programs that can automatically create attractive color palettes.

### Color Theory

We have see that we can represent colors in two ways:

1. as triples containing red, green, and blue values (RGB); or
2. as hue, saturation, and lightness (HSL).

We will use the HSL representation as it
better corresponds to our perception of color.
If we arrange colors in the familiar color wheel,
distance from the center corrsponds to lightness
and steps around the outside correspond to changes in hue:

![A color wheel. A full turn around the wheel represents a 360 degree change in hue.](src/pages/declarations/color-wheel.png)

#### Complementary Colors

A simple way to generate colors that look good together
is to use *complementary colors*.
Given a color, it's complement is the one opposite it on the color wheel.
In other words, it has hue rotated by 180 degrees.
Complementary pairs have high contrast and make for striking compositions:

![Aubergines by <a href="https://www.flickr.com/photos/36179943@N00/219265991">Estaban Cavrico</a> <a href="https://creativecommons.org/licenses/by-nc-nd/2.0/">CC BY-NC-ND 2.0</a>. The green and purple of the aubergins are near complements.](src/pages/declarations/aubergines.jpg)

**Exercise: Complementary Colors**

Create a method `complement` that takes a `Color` as input and returns its complement.
You can use the method `spin` on a `Color` to rotate its hue by a given `Angle`.

<div class="solution">
~~~ scala
def complement(color: Color): Color =
  color.spin(180.degrees)
~~~
</div>

**Exercise: Complementary Chess Boards** 

Using `complement` write a method `concentricCircles` that creates concentric circles,
where adjacent circles have complementary colors.
You will have to pass the current color to `concentricCircles`,
so use this method declaration:

~~~ scala
def concentricCircles(level: Int, color: Color): Image = ???
~~~

You should end up with a picture like the below.

![Concentric circles colored using complementary colors](src/pages/declarations/complements-concentric-circles.png)

### Analogous Colors

Complementary colors can be quite harsh on the eyes.
We can play around with saturation and lightness to decrease the contrast
but ultimately this color scheme is quite limited.
Let's explore another color scheme, *analogous colors*, that gives us more flexibility.

In analogous color is simply one that is close on the color wheel to a given color.
We can generate an analogous color by spinning hue, say, fifteen degrees.

**Exercise: Analogous Colors**

Create a method `analogous` that takes a `Color` as input and returns an analogous color.

<div class="solution">
~~~ scala
def analogous(color: Color): Color =
  color.spin(15.degrees)
~~~
</div>

**Exercise: Analogous Concentric Circles**

<div class="callout callout-danger">
TODO: Complete this exercise and examples
</div>
