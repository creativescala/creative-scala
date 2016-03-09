## Creating Colors

<div class="callout callout-info">
If you run the examples from the SBT console within Doodle they will just work. If not, you will need to start your code with the following imports to make Doodle available.

```tut:book
import doodle.core._
import doodle.core.Image._
import doodle.syntax._
import doodle.jvm.Java2DCanvas._
import doodle.backend.StandardInterpreter._
```
</div>

We've seen how to use predefined colors in our images. What about creating our own colors? In this section we will see how to create colors of our own, and transform existing colors into new ones.

### RGB Colors

Computers work with colors defined by mixing together different amounts of red, green, and blue. This "RGB" model is an [additive model][additive-model] of color. Each red, green, or blue component can have a value between zero and 255. If all three components are set to the maximum of 255 we get pure white. If all components are zero we get black.

We can create our own RGB colors using the `rgb` method on the `Color` object. This method takes three parameters: the red, green, and blue components. These are numbers between 0 and 255, called an `UnsignedByte`[^byte]. There is no literal expression for `UnsignedByte` like there is for `Int`, so we must convert an `Int` to `UnsignedByte`. We can do this with the `uByte` method. An `Int` can take on more values that an `UnsignedByte`, so if the number is too small or too large to be represented as a `UnsignedByte` it will be converted to the closest values is the range 0 to 255. These examples illustrate the conversion.

```tut:book
0.uByte
255.uByte
128.uByte
-100.uByte // Too small, is transformed to 0
1000.uByte // Too big, is transformed to 255
```

(Note that `UnsignedByte` is a feature of Doodle. It is not something provided by Scala.)

Now we know how to construct `UnsignedBytes` we can make RGB colors.

```tut:book
Color.rgb(255.uByte, 255.uByte, 255.uByte) // White
Color.rgb(0.uByte, 0.uByte, 0.uByte) // Black
Color.rgb(255.uByte, 0.uByte, 0.uByte) // Red
```

### HSL Colors

The RGB color representation is not very easy to use. The hue-saturation-lightness (HSL) format more closely correponds to how we perceive color. In this representation a color consists of:

- *hue*, which is an angle between 0 and 360 degrees giving a rotation around the color wheel.
- *saturation*, which is a number between 0 and 1 giving the intensity of the color from a drab gray to a pure color; and
- *lightness* between 0 and 1 giving the color a brightness varying from black to pure white.

![A color wheel showing changes in hue (rotations) and lightness (distance from the center) with saturation fixed at 1.](src/pages/declarations/color-wheel.png)

![A gradient showing how changing saturation effects color, with hue and lightness held constant. Saturation is zero on the left and one on the right.](src/pages/declarations/saturation.png)

We can construct a color in the HSL representation using the `Color.hsl` method. This method takes as parameters the hue, saturation, and lightness. The hue is an `Angle`. We can convert a `Double` to an `Angle` using the `degrees` (or `radians`) methods.

```tut:book
0.degrees
180.degrees
3.14.radians
```

Saturation and lightness are both normalized to between 0.0 and 1.0. We can convert a `Double` to a normalized value with the `.normalized` method.

```tut:book
0.0.normalized 
1.0.normalized
1.2.normalized // Too big, is clipped to 1.0
-1.0.normalized // Too small, is clipped to 0.0
```

We can now create colors using the HSL representation.

```tut:book
Color.hsl(0.degrees, 0.8.normalized, 0.6.normalized) // A pastel red
```

To view this color we can render it in a picture. Here's an example.

![Rendering pastel red in a triangle](./src/pages/pictures/triangle-pastel-red.png)

### Transparency

We can also add a degree of transparency to our colors, by adding an *alpha* value. An alpha value of 0.0 indicates a completely transparent color, while a color with an alpha of 1.0 is completely opaque. The methods `Color.rgba` and `Color.hsla` have a fourth parameter that is a `Normalized` alpha value.

### Manipulating Colors

The effectiveness of a composition often depends as much on the relationships between colors as the actual colors used. Colors have several methods that allow us to create a new color from an existing one. The most commonly used ones are:

- `spin`, which rotates the hue by an `Angle`;
- `saturate` and `desaturate`, which respectively add and subtract a `Normalised` value from the color; and
- `lighten` and `darken`, which respecitvely add and subtract a `Normalised` value from the lightness.

For example,

```tut:book
((Circle(100) fillColor Color.red) beside 
  (Circle(100) fillColor Color.red.spin(15.degrees)) beside
    (Circle(100) fillColor Color.red.spin(30.degrees))).lineWidth(5.0)
```

produces the following picture

![Three circles, starting with Color.red and spinning by 15 degrees for each successive circle](./src/pages/pictures/three-circles-spin.png)

Here's a similar example, this time manipulating saturation and lightness.

```tut:book
(((circle(20) fillColor (Color.red darken 0.2.normalized))
  beside (circle(20) fillColor Color.red)
  beside (circle(20) fillColor (Color.red lighten 0.2.normalized))) above
((rectangle(40,40) fillColor (Color.red desaturate 0.6.normalized)) 
  beside (rectangle(40,40) fillColor (Color.red desaturate 0.3.normalized))
  beside (rectangle(40,40) fillColor Color.red)))
```
![The top three circles show the effect of changing lighteness, and the bottom three squares show the effect of changing saturation.](./src/pages/pictures/three-circles-spin.png)

[^byte]: A byte is a number with 256 possible values, which takes 8 bits within a computer to represent. A signed byte has integer values from -128 to 127, while an unsigned byte ranges from 0 to 255.
