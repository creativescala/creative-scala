## Color

```tut:invisible
import doodle.core._
import doodle.core.Image._
import doodle.syntax._
import doodle.jvm.Java2DCanvas._
import doodle.backend.StandardInterpreter._
```

In addition to layout, Doodle has some simple operators
to add a splash of colour to our images.
Try these out to see how they work:

---------------------------------------------------------------------------------------------
Operator                Type    Description                 Example
----------------------- ------- --------------------------- ---------------------------------
`Image fillColor Color` `Image` Fills the image with        `Circle(10) fillColor Color.red`
                                the specified colour.

`Image lineColor Color` `Image` Outlines the image with     `Circle(10) lineColor Color.blue`
                                the specified colour.

`Image lineWidth Int`   `Image` Outlines the image with     `Circle(10) lineWidth 3`
                                the specified stroke width.
---------------------------------------------------------------------------------------------

Doodle has various ways of creating colours.
The simplest are the predefined colours in [CommonColors.scala][common-colors].
Here are a few of the most commonly used:

------------------------------------------------------------------
Color                   Type    Example
----------------------- ------- ----------------------------------
`Color.red`             `Color` `Circle(10) fillColor Color.red`

`Color.blue`            `Color` `Circle(10) fillColor Color.blue`

`Color.green`           `Color` `Circle(10) fillColor Color.green`

`Color.black`           `Color` `Circle(10) fillColor Color.black`

`Color.white`           `Color` `Circle(10) fillColor Color.white`

`Color.gray`            `Color` `Circle(10) fillColor Color.gray`

`Color.brown`           `Color` `Circle(10) fillColor Color.brown`
------------------------------------------------------------------

### Exercise

#### Evil Eye

Make the following image, designed to look like a traditional amulet protecting against the evil eye. I used `cornflowBlue` for the iris, and `darkBlue` for the outer color, but experiment with your own choices!

![No evil eyes here!](src/pages/pictures/evil-eye.png)

<div class="solution">
Here's my amulet:

```tut:book
((circle(10) fillColor Color.black) on
 (circle(20) fillColor Color.cornflowerBlue) on
 (circle(30) fillColor Color.white) on
 (circle(50) fillColor Color.darkBlue))
```
</div>
