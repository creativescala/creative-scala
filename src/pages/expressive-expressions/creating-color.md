## Creating Colors

```scala mdoc:invisible
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._
```

We've seen how to use predefined colors in our images. What about creating our own colors? In this section we will see how to create colors of our own, and transform existing colors into new ones.

### RGB Colors

Computers work with colors defined by mixing together different amounts of red, green, and blue. This "RGB" model is an [additive model][additive-model] of color, which means adding more colors gets us closer to white. This is the oppositive of paint, which is a subtractive model where adding more paints gets us closer to black. Each red, green, or blue component can have a value between zero and 255. If all three components are set to the maximum of 255 we get pure white. If all components are zero we get black.

We can create our own RGB colors using the `rgb` method on the `Color` object. This method takes three parameters: the red, green, and blue components. These are numbers between 0 and 255, called an `UnsignedByte`[^byte]. There is no literal expression for `UnsignedByte` like there is for `Int`, so we must convert an `Int` to `UnsignedByte`. We can do this with the `uByte` method. An `Int` can take on more values that an `UnsignedByte`, so if the number is too small or too large to be represented as a `UnsignedByte` it will be converted to the closest values is the range 0 to 255. These examples illustrate the conversion.

```scala mdoc
0.uByte.get
255.uByte.get
128.uByte.get
-100.uByte.get // Too small, is transformed to 0
1000.uByte.get // Too big, is transformed to 255
```

(Note that `UnsignedByte` is a feature of Doodle. It is not something provided by Scala.)

Now we know how to construct `UnsignedBytes` we can make RGB colors.

```scala mdoc:silent
Color.rgb(255.uByte, 255.uByte, 255.uByte) // White
Color.rgb(0.uByte, 0.uByte, 0.uByte) // Black
Color.rgb(255.uByte, 0.uByte, 0.uByte) // Red
```

### HSL Colors

The RGB color representation is not very easy to use. The hue-saturation-lightness (HSL) format more closely corresponds to how we perceive color. In this representation a color consists of:

- *hue*, which is an angle between 0 and 360 degrees giving a rotation around the color wheel.
- *saturation*, which is a number between 0 and 1 giving the intensity of the color from a drab gray to a pure color; and
- *lightness* between 0 and 1 giving the color a brightness varying from black to pure white.

[@fig:pictures:color-wheel] shows how colors vary as we change hue and lightness, and [@fig:pictures:saturation] shows the effect of changing saturation.

![A color wheel showing changes in hue (rotations) and lightness (distance from the center) with saturation fixed at 1.](src/pages/pictures/color-wheel.pdf+svg){#fig:pictures:color-wheel}

![A gradient showing how changing saturation effects color, with hue and lightness held constant. Saturation is zero on the left and one on the right.](src/pages/pictures/saturation.pdf+svg){#fig:pictures:saturation}

We can construct a color in the HSL representation using the `Color.hsl` method. This method takes as parameters the hue, saturation, and lightness. The hue is an `Angle`. We can convert a `Double` to an `Angle` using the `degrees` (or `radians`) methods.

```scala mdoc
0.degrees
180.degrees
3.14.radians
```

Saturation and lightness are both `Doubles` that should be between 0.0 and 1.0. Values outside this range will be converted to the closest number within the range. 

We can now create colors using the HSL representation.

```scala mdoc:silent
Color.hsl(0.degrees, 0.8, 0.6) // A pastel red
```

To view this color we can render it in a picture. See [@fig:pictures:triangle-pastel-red] for an example.

![Rendering pastel red in a triangle](./src/pages/pictures/triangle-pastel-red.pdf+svg){#fig:pictures:triangle-pastel-red}


### Manipulating Colors

The effectiveness of a composition often depends as much on the relationships between colors as the actual colors used. Colors have several methods that allow us to create a new color from an existing one. The most commonly used ones are:

- `spin`, which rotates the hue by an `Angle`;
- `saturate` and `desaturate`, which respectively add and subtract a `Normalised` value from the color; and
- `lighten` and `darken`, which respectively add and subtract a `Normalised` value from the lightness.

For example,

```scala mdoc:silent
Image.circle(100)
     .fillColor(Color.red)
     .beside(Image.circle(100).fillColor(Color.red.spin(15.degrees)))
     .beside(Image.circle(100).fillColor(Color.red.spin(30.degrees)))
     .strokeWidth(5.0)
```

produces [@fig:pictures:three-circles-spin].

![Three circles, starting with Color.red and spinning by 15 degrees for each successive circle](./src/pages/pictures/three-circles-spin.pdf+svg){#fig:pictures:three-circles-spin}

Here's a similar example, this time manipulating saturation and lightness, shown in [@fig:pictures:saturate-and-lighten].

```scala mdoc:silent
Image.circle(40)
   .fillColor(Color.red.darken(0.2.normalized))
   .beside(Image.circle(40).fillColor(Color.red))
   .beside(Image.circle(40).fillColor((Color.red.lighten(0.2.normalized))))
   .above(Image.rectangle(40, 40).fillColor(Color.red.desaturate(0.6.normalized))
               .beside(Image.rectangle(40,40).fillColor(Color.red.desaturate(0.3.normalized)))
               .beside(Image.rectangle(40,40).fillColor(Color.red)))
```

![The top three circles show the effect of changing lightness, and the bottom three squares show the effect of changing saturation.](./src/pages/pictures/saturate-and-lighten.pdf+svg){#fig:pictures:saturate-and-lighten}

[^byte]: A byte is a number with 256 possible values, which takes 8 bits within a computer to represent. A signed byte has integer values from -128 to 127, while an unsigned byte ranges from 0 to 255.


### Transparency

We can also add a degree of transparency to our colors, by adding an *alpha* value. An alpha value of 0.0 indicates a completely transparent color, while a color with an alpha of 1.0 is completely opaque. The methods `Color.rgba` and `Color.hsla` have a fourth parameter that is a `Normalized` alpha value. We can also create a new color with a different transparency by using the `alpha` method on a color. Here's an example, shown in [@fig:pictures:rgb-alpha].

```scala mdoc:silent
Image.circle(40)
     .fillColor(Color.red.alpha(0.5.normalized))
     .beside(Image.circle(40).fillColor(Color.blue.alpha(0.5.normalized)))
     .on(Image.circle(40).fillColor(Color.green.alpha(0.5.normalized)))
```

![Circles with alpha of 0.5 showing transparency](./src/pages/pictures/rgb-alpha.pdf+svg){#fig:pictures:rgb-alpha}


### Exercises {-}

#### Analogous Triangles {-}

Create three triangles, arranged in a triangle, with analogous colors. Analogous colors are colors that are similar in hue. See a (fairly elaborate) example in [@fig:pictures:analogous-triangles].

![Analogous triangles. The colors chosen are variations on `darkSlateBlue`](./src/pages/pictures/complementary-triangles.pdf+svg){#fig:pictures:analogous-triangles}

<div class="solution">
These sort of examples are getting a bit too long to write out at the console. We'll look at a way around this next.

```scala mdoc
Image.triangle(40, 40)
  .strokeWidth(6.0)
  .strokeColor(Color.darkSlateBlue)
  .fillColor(Color.darkSlateBlue
               .lighten(0.3.normalized)
               .saturate(0.2.normalized)
               .spin(10.degrees))
  .above(Image.triangle(40, 40)
           .strokeWidth(6.0)
           .strokeColor(Color.darkSlateBlue.spin(-30.degrees))
           .fillColor(Color.darkSlateBlue
                        .lighten(0.3.normalized)
                        .saturate(0.2.normalized)
                        .spin(-20.degrees))
           .beside(Image.triangle(40, 40)
                     .strokeWidth(6.0)
                     .strokeColor(Color.darkSlateBlue.spin(30.degrees))
                     .fillColor(Color.darkSlateBlue
                                  .lighten(0.3.normalized)
                                  .saturate(0.2.normalized)
                                  .spin(40.degrees))))
```
</div>
