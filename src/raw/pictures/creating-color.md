## Creating Colors

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

The RGB color representation is not very easy to use.

### Transparency


### Manipulating Colors

[^byte]: A byte is a number with 256 possible values, which takes 8 bits within a computer to represent. A signed byte has values from -128 to 127, while an unsigned byte can be any integer from 0 to 255.
