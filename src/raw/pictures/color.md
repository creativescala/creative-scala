### Color

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
The simplest are the predefined colours in [CommonColors.scala][common-colors]
`shared/src/main/scala/doodle/core/CommonColors.scala`.
Here are a few of the most important:

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

## Exercise

#### Stay on Target

Colour your target red and white, the stand in brown (if applicable),
and some ground in green:

![Colour archery target](src/pages/expressions/target3.png)

<div class="solution">
The trick here is using parentheses to control the order of composition.
The `fillColor()`, `lineColor()`, and `lineWidth()` methods
apply to a single image---we need to make sure that image
comprises the correct set of shapes:

~~~ scala
(
  ( Circle(10) fillColor Color.red ) on
  ( Circle(20) fillColor Color.white ) on
  ( Circle(30) fillColor Color.red lineWidth 2 ) above
  ( Rectangle(6, 20) above Rectangle(20, 6) fillColor Color.brown ) above
  ( Rectangle(80, 25) lineWidth 0 fillColor Color.green )
).draw
~~~
</div>
