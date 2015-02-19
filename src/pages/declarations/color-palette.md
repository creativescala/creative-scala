## Extended Exercise: Color Palettes

In this exercise we will explore the creation of color palettes.
An attractive picture must make good choices for color.
Color theory has developed to explain combinations of color that go together.
We will use color theory
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
