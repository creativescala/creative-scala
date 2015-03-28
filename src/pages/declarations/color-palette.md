## Extended Exercise: Color Palettes

In this exercise we will explore the creation of color palettes.
An attractive picture must make good choices for color.
Color theory has developed to explain combinations of color that go together.
We will use color theory
to create programs that can automatically construct attractive color palettes.

### Color Representation

In Doodle we can represent a color in one of two ways:

1. as triples containing red, green, and blue values (RGB); or
2. as hue, saturation, and lightness (HSL).

We will use the HSL representation as it
better corresponds to our perception of color.
If we arrange colors in the familiar color wheel,
distance from the center corrsponds to lightness
and steps around the outside correspond to changes in hue:

![A color wheel. A full turn around the wheel represents a 360 degree change in hue.](src/pages/declarations/color-wheel.png)

Saturation, the third dimension,
corresponds to intensity of color.
The strip of colors below shows the effect of varying saturation from 0.0 to 1.0,
for fixed hue (170 degrees) and lightness (0.5).
As you can see,
changing saturation goes from a dull gray to a bright and vibrant color.

![The effect of changing saturation while keeping hue and lightness fixed. Saturation increases from left to right, starting at zero and finishing at one.](src/pages/declarations/saturation.png)

### The Color API

Before we can create color schemes
we need to know how to create and manipulate colors.

#### Creating Colors

There are two main methods to create colours:

~~~ scala
Color.hsl(hue: Angle, saturation: Normalized, lightness: Normalized)
Color.rgb(red: UnsignedByte, green: UnsignedByte, blue: UnsignedByte)
~~~

These methods use types --- `Angle`, `Normalized`, and `UnsignedByte` --- that have not seen before.
They all represent numbers with some special characteristics.
A `Normalized` is a number between 0 and 1.
An `UnsignedByte` is an integer between 0 and 255.
An `Angle` is unrestricted in value but there are several operations that only make sense on angles (sine, cosine, and so on) and several representations (angles, radians) in common use.

The `Normalized` and `UnsignedByte` types make it explicit that some conversion is necessary from raw number types like `Int` and `Double`.
There are many different ways to handle inputs that are out of range,
such as clipping them or raising an error,
and we require the programmer to be explicit about the approach they want.

For `Normalized` and `UnsignedByte` Doodle provides a default conversion of clipping.
For example, if we are creating a `Normalized` (value between 0.0 and 1.0),
any input less than 0.0 is set to 0.0 and greater than 1.0 becomes 1.0.
To use these conversions import `doodle.syntax.normalized._` or `doodle.syntax.uByte._`
and then numbers are *enriched* with methods `normalized` and `uByte` respectively.
Here's a quick example. Notice how values out of range are set to the closest valid value.

~~~ scala
import doodle.syntax.normalized._

0.5.normalized
//res: doodle.core.Normalized = Normalized(0.5)
0.0.normalized
//res: doodle.core.Normalized = Normalized(0.0)
-0.5.normalized
//res: doodle.core.Normalized = Normalized(0.0)
1.5.normalized
//res: doodle.core.Normalized = Normalized(1.0)

import doodle.syntax.uByte._

128.uByte
//res: doodle.core.UnsignedByte = UnsignedByte(0)
0.uByte
//res: doodle.core.UnsignedByte = UnsignedByte(-128)
255.uByte
//res: doodle.core.UnsignedByte = UnsignedByte(127)
-127.uByte
//res: doodle.core.UnsignedByte = UnsignedByte(-128)
512.uByte
//res: doodle.core.UnsignedByte = UnsignedByte(127)
~~~

For `Angle` we ask the programmer to specify if the raw number represents a value in degrees, radians, or turns (fractions of a circle, with a full circle being one turn).
For `Angles` the import is `doodle.syntax.angle._`
which enriches numbers with methods `degrees`, `radians`, and `turns`.
Here's an example:

~~~ scala
import doodle.syntax.angle._

0.degrees
//res: doodle.core.Angle = Angle(0.0)
180.degrees
//res: doodle.core.Angle = Angle(3.141592653589793)
360.degrees
//res: doodle.core.Angle = Angle(6.283185307179586)

math.Pi
//res: Double = 3.141592653589793
math.Pi.radians
//res: doodle.core.Angle = Angle(3.141592653589793)

0.5.turns
//res: doodle.core.Angle = Angle(3.141592653589793)
1.0.turns
//res: doodle.core.Angle = Angle(6.283185307179586)
~~~

We can now create some colors:

~~~ scala
Color.hsl(170.degrees, 1.0.normalized, 0.5.normalized)
// res: doodle.core.HSLA = HSLA(Angle(2.9670597283903604),Normalized(1.0),Normalized(0.5),Normalized(1.0))
~~~

Note that the color we created has four fields. The fourth field is the `alpha` value, which specifies the opacity of the color. There are four parameter methods `Color.hsla` and `Color.rgba` that can be used to specify the `alpha` when creating a color.

#### Modifying Colors

There are several methods to modify colors. These methods all create a new `Color`. No `Color` is ever actually changed after it is created, as doing so breaks substitution.

New `hue`, `saturation`, `lightness`, and `alpha` values can all be set with methods of the same name. Notice how the original color is unchanged.

~~~ scala
val c = Color.hsl(170.degrees, 1.0.normalized, 0.5.normalized)
//c: doodle.core.HSLA = HSLA(Angle(2.9670597283903604),Normalized(1.0),Normalized(0.5),Normalized(1.0))

c.hue(220.degrees)
//res: doodle.core.Color = HSLA(Angle(3.839724354387525),Normalized(1.0),Normalized(0.5),Normalized(1.0))

c.saturation(0.5.normalized)
//res: doodle.core.Color = HSLA(Angle(2.9670597283903604),Normalized(0.5),Normalized(0.5),Normalized(1.0))

c.lightness(0.25.normalized)
//res: doodle.core.Color = HSLA(Angle(2.9670597283903604),Normalized(1.0),Normalized(0.25),Normalized(1.0))

c.alpha(0.5.normalized)
//res: doodle.core.Color = HSLA(Angle(2.9670597283903604),Normalized(1.0),Normalized(0.5),Normalized(0.5))

c
//res: doodle.core.HSLA = HSLA(Angle(2.9670597283903604),Normalized(1.0),Normalized(0.5),Normalized(1.0))
~~~

There are also methods to adjust the existing `hue`, `saturation`, `lightness`, and `alpha`. These methods all create a new color by adding or subtracting from the existing value of the parameter of interest.

~~~ scala
val c = Color.hsl(170.degrees, 1.0.normalized, 0.5.normalized)
//c: doodle.core.HSLA = HSLA(Angle(2.9670597283903604),Normalized(1.0),Normalized(0.5),Normalized(1.0))

c.spin(220.degrees)
//res: doodle.core.HSLA = HSLA(Angle(6.806784082777885),Normalized(1.0),Normalized(0.5),Normalized(1.0))

c.lighten(0.2.normalized)
//res: doodle.core.HSLA = HSLA(Angle(2.9670597283903604),Normalized(1.0),Normalized(0.7),Normalized(1.0))

c.darken(0.2.normalized)
//res: doodle.core.HSLA = HSLA(Angle(2.9670597283903604),Normalized(1.0),Normalized(0.3),Normalized(1.0))

c.saturate(0.2.normalized)
//res: doodle.core.HSLA = HSLA(Angle(2.9670597283903604),Normalized(1.0),Normalized(0.5),Normalized(1.0))

c.desaturate(0.2.normalized)
//res: doodle.core.HSLA = HSLA(Angle(2.9670597283903604),Normalized(0.8),Normalized(0.5),Normalized(1.0))

c.fadeIn(0.2.normalized)
//res: doodle.core.HSLA = HSLA(Angle(2.9670597283903604),Normalized(1.0),Normalized(0.5),Normalized(1.0))

c.fadeOut(0.2.normalized)
//res: doodle.core.HSLA = HSLA(Angle(2.9670597283903604),Normalized(1.0),Normalized(0.5),Normalized(0.8))
~~~

### Complementary Colors

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

Using `complement` write a method `complementaryChessBoard` that creates a four-by-four chess board using a complementary color scheme. This method should take a `Color` input. Here's the method signature:

~~~ scala
def complementaryChessBoard(color: Color): Image = ???
~~~

You should end up with a picture like the below.

![A chess board colored using complementary colors](src/pages/declarations/complementary-chess-board.png)

<div class="solution">
We can build the method using the methods we have already created.

~~~ scala
def complementaryChessBoard(color: Color) =
  fourByFour(color, complement(color))
~~~
</div>

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

**Exercise: Analogous Chess Boards**

Now create a method `analogousChessBoard` that creates a four-by-four chess board with an analogous color scheme. You should get a result like the below.

![A chess board colored using analogous colors](src/pages/declarations/analogous-chess-board.png)

<div class="solution">
This follows the same pattern as `complementaryChessBoard`. Notice how we build big things (a colored chess board) out of smaller component parts. This idea of composing small pieces of code into larger pieces is one of the key ideas in functional programming.

~~~ scala
def analogousChessBoard(color: Color) =
  fourByFour(color, analogous(color))
~~~
</div>

### Beyond Two-Color Palettes

We have seen how we can build very simple color palettes from complementary and analogous colors. Now let's combine these ideas to build more complex palettes. A *tetrad color scheme* consists of two analogous colors and their complements.

![A chess board colored using a tetradic color scheme](src/pages/declarations/tetrad-chess-board.png)

Define a method `tetradChessBoard` that creates a chess board colored with a tetradic color scheme as illustrated. Use the following skeleton

~~~ scala
def tetradChessBoard(color: Color) = ???
~~~

Hint: You will have to call `twoByTwo`, not `fourByFour`, within the body of `tetradChessBoard`.

<div class="solution">
It would be nice to have a method for creating an entire tetradic color scheme from a single color, but we don't currently have a way of wrapping up a collection of data so that we could return all four values from the methods. We'll see ways of doing this later, when we introduce classes and collections.

~~~ scala
def tetradChessBoard(color: Color) = {
  val color1 = color
  val color2 = analogous(color)
  val color3 = complement(color)
  val color4 = complement(color2)

  val square1 = twoByTwo(color1, color3)
  val square2 = twoByTwo(color2, color4)

  (square1 beside square2) above
  (square2 beside square1)
}
~~~
</div>
