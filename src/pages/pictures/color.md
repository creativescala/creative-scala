## Color

```scala mdoc:invisible
import doodle.core._
import doodle.image._
import doodle.image.syntax._
import doodle.image.syntax.core._
import doodle.java2d._
```

In addition to layout, Doodle has some simple operators to add a splash of colour to our images. Try these out the methods described in [@tbl:pictures:color] to see how they work.

---------------------------------------------------------------------------------------------
Operator                Type    Description                 Example
----------------------- ------- --------------------------- ---------------------------------
`Image.fillColor(Color)` `Image` Fills the image with        `Image.circle(10).fillColor(Color.red)`
                                the specified colour.

`Image.strokeColor(Color)` `Image` Outlines the image with     `Image.circle(10).strokeColor(Color.blue)`
                                the specified colour.

`Image.strokeWidth(Int)`   `Image` Outlines the image with     `Image.circle(10).strokeWidth(3)`
                                the specified stroke width.
---------------------------------------------------------------------------------------------

: Some of the methods to add color to images in Doodle. {#tbl:pictures:color}

Doodle has various ways of creating colours.
The simplest are the predefined colours in [CommonColors.scala][common-colors].
Some of the most commonly used are described in [@tbl:pictures:colors].

--------------------------------------------------------------------
Color                   Type    Example
----------------------- ------- ------------------------------------
`Color.red`             `Color` `Image.circle(10).fillColor(Color.red)`

`Color.blue`            `Color` `Image.circle(10).fillColor(Color.blue)`

`Color.green`           `Color` `Image.circle(10).fillColor(Color.green)`

`Color.black`           `Color` `Image.circle(10).fillColor(Color.black)`

`Color.white`           `Color` `Image.circle(10).fillColor(Color.white)`

`Color.gray`            `Color` `Image.circle(10).fillColor(Color.gray)`

`Color.brown`           `Color` `Image.circle(10).fillColor(Color.brown)`
------------------------------------------------------------------

: Some of the most common predefined colors. {#tbl:pictures:colors}

### Exercises {-}

#### Evil Eye {-}

Make the image in [@fig:pictures:evil-eye], designed to look like a traditional amulet protecting against the evil eye. I used `cornflowerBlue` for the iris, and `darkBlue` for the outer color, but experiment with your own choices!

![No evil eyes here!](src/pages/pictures/evil-eye.pdf+svg){#fig:pictures:evil-eye}

<div class="solution">
Here's my amulet:

```scala mdoc
Image
  .circle(10)
  .fillColor(Color.black)
  .on(Image.circle(20).fillColor(Color.cornflowerBlue))
  .on(Image.circle(30).fillColor(Color.white))
  .on(Image.circle(50).fillColor(Color.darkBlue))
```
</div>
