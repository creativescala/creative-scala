## Images

Numbers and text are boring.
Let's switch to something more interesting---images!
Grab the *Doodle* project from [https://github.com/underscoreio/doodle]().
This toy drawing library will provide the basis for
most of the exercises in this course.
Start a Scala console to try Doodle out:

~~~ bash
bash$ git clone https://github.com/underscoreio/doodle.git
# Cloning ...

bash$ cd doodle

bash$ ./sbt.sh console
[info] Loading project definition from /.../doodle/project
[info] Set current project to doodle (in build file:/.../doodle/)
[info] Compiling 1 Scala source to /.../doodle/jvm/target/scala-2.11/classes...
[info] Starting scala interpreter...
[info]
import doodle.core._
import doodle.syntax._
import doodle.jvm._
Welcome to Scala version 2.11.5 (Java HotSpot(TM) 64-Bit Server VM, Java 1.7.0_45).
Type in expressions to have them evaluated.
Type :help for more information.

scala>
~~~

### Primitive Images

The Doodle console gives us access to some useful drawing tools
as well as the regular Scala standard library.
Try creating a simple shape:

~~~ scala
Circle(10)
// res0: doodle.core.Circle = Circle(10.0)
~~~

<div class="callout callout-info">
*How To Run This Example*

When you see an example like the one above,
enter the line of Scala code at the `scala>` prompt in the console.
You should see the text in the comment as output:

~~~ scala
scala> Circle(10)
res0: doodle.core.Circle = Circle(10.0)
~~~

We haven't written the `scala>` prompts in the examples in this book
because they make it difficult to copy and paste text into the console.
We've written the console output as comments for the same reason.
</div>

Notice the type and value of the expression we just entered.
The type is `doodle.core.Circle`
and the value is `Circle(10.0)`---a circle with a 10 pixel radius.

We can draw this circle (and other images) using Doodle's `draw()` function,
which has been brought into scope automatically.
Try drawing the circle now:

~~~ scala
draw(res0)
~~~

A window should appear containing the following:

![A circle](src/pages/expressions/circle.png)

Doodle supports a handful "primitive" images:
circles, rectangles, and triangles.
Let's try drawing a rectangle:

~~~ scala
draw(Rectangle(50, 100))
~~~

![A rectangle](src/pages/expressions/rectangle.png)

Finally let's try a triangle:

~~~ scala
draw(Triangle(60, 40))
~~~

![A triangle](src/pages/expressions/triangle.png)

### Layout

We can write expressions to combine images producing more complex images.
Try the following code---you should see a circle and a rectangle
displayed beside one another:

~~~ scala
draw(Circle(10) beside Rectangle(10, 20))
~~~

![A circle beside a rectangle](src/pages/expressions/circle-beside-rectangle.png)

Doodle contains several layout operators for combining images.
Try them out now to see what they do:

----------------------------------------------------------------------------------------
Operator              Type    Description                Example
--------------------- ------- -------------------------- -------------------------------
`Image beside Image`  `Image` Places images horizontally `Circle(10) beside Circle(20)`
                              next to one another.

`Image above Image`   `Image` Places images vertically   `Circle(10) above Circle(20)`
                              next to one another.

`Image below Image`   `Image` Places images vertically   `Circle(10) below Circle(20)`
                              next to one another.

`Image on Image`      `Image` Places images centered     `Circle(10) on Circle(20)`
                              on top of one another.

`Image under Image`   `Image` Places images centered     `Circle(10) under Circle(20)`
                              on top of one another.
----------------------------------------------------------------------------------------

**Exercise: Compilation Target**

Create a line drawing of an archery target with three concentric scoring bands:

![Simple archery target](src/pages/expressions/target1.png)

For bonus credit add a stand so we can place the target on a range:

![Archery target with a stand](src/pages/expressions/target2.png)

<div class="solution">
The simplest solution is to create three concentric circles using the `on` operator:

~~~ scala
draw(Circle(10) on Circle(20) on Circle(30))
~~~

For the extra credit we can create a stand using two rectangles:

~~~ scala
draw(
  Circle(10) on
  Circle(20) on
  Circle(30) above
  Rectangle(6, 20) above
  Rectangle(20, 6)
)
~~~
</div>

### Colour

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
The simplest are the predefined colours in
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

**Exercise: Stay on Target**

Colour your target red and white, the stand in brown (if applicable),
and some ground in green:

![Colour archery target](src/pages/expressions/target3.png)

<div class="solution">
The trick here is using parentheses to control the order of composition.
The `fillColor()`, `lineColor()`, and `lineWidth()` methods
apply to a single image---we need to make sure that image
comprises the correct set of shapes:

~~~ scala
draw(
  ( Circle(10) fillColor Color.red ) on
  ( Circle(20) fillColor Color.white ) on
  ( Circle(30) fillColor Color.red lineWidth 2 ) above
  ( Rectangle(6, 20) above Rectangle(20, 6) fillColor Color.brown ) above
  ( Rectangle(80, 25) lineWidth 0 fillColor Color.green )
)
~~~
</div>
