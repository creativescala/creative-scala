## Color

```scala mdoc:invisible
import doodle.core._
import doodle.core.Image._
import doodle.syntax._
import doodle.jvm.Java2DFrame._
import doodle.backend.StandardInterpreter._
```

In addition to layout, Doodle has some simple operators to add a splash of colour to our images. Try these out the methods described in [@tbl:pictures:color] to see how they work.

---------------------------------------------------------------------------------------------
Operator                Type    Description                 Example
----------------------- ------- --------------------------- ---------------------------------
`Image fillColor Color` `Image` Fills the image with        `circle(10) fillColor Color.red`
                                the specified colour.

`Image lineColor Color` `Image` Outlines the image with     `circle(10) lineColor Color.blue`
                                the specified colour.

`Image lineWidth Int`   `Image` Outlines the image with     `circle(10) lineWidth 3`
                                the specified stroke width.
---------------------------------------------------------------------------------------------

: Some of the methods to add color to images in Doodle. {#tbl:pictures:color}

Doodle has various ways of creating colours.
The simplest are the predefined colours in [CommonColors.scala][common-colors].
Some of the most commonly used are described in [@tbl:pictures:colors].

------------------------------------------------------------------
Color                   Type    Example
----------------------- ------- ----------------------------------
`Color.red`             `Color` `circle(10) fillColor Color.red`

`Color.blue`            `Color` `circle(10) fillColor Color.blue`

`Color.green`           `Color` `circle(10) fillColor Color.green`

`Color.black`           `Color` `circle(10) fillColor Color.black`

`Color.white`           `Color` `circle(10) fillColor Color.white`

`Color.gray`            `Color` `circle(10) fillColor Color.gray`

`Color.brown`           `Color` `circle(10) fillColor Color.brown`
------------------------------------------------------------------

: Some of the most common predefined colors. {#tbl:pictures:colors}

### Exercises {-}

#### Evil Eye {-}

Make the image in [@fig:pictures:evil-eye], designed to look like a traditional amulet protecting against the evil eye. I used `cornflowerBlue` for the iris, and `darkBlue` for the outer color, but experiment with your own choices!

![No evil eyes here!](src/pages/pictures/evil-eye.pdf+svg){#fig:pictures:evil-eye}

<div class="solution">
Here's my amulet:

```scala mdoc
((circle(10) fillColor Color.black) on
 (circle(20) fillColor Color.cornflowerBlue) on
 (circle(30) fillColor Color.white) on
 (circle(50) fillColor Color.darkBlue))
```
</div>
