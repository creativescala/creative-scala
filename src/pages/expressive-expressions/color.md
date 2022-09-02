## Color

```scala mdoc:invisible
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._
```

In addition to layout, Doodle has some simple operators to add a splash of colour to our images. Try these out the methods described in @:tref(pictures:color) to see how they work.

+-----------------------------+-----------+-------------------------+------------------------------+
| Method                      | Parameter | Description             | Example                      |
+=============================+===========+=========================+==============================+
| `fillColor`                 | `Color`   | Fills the image with    | `Image.circle(10)            |
|                             |           | the specified color.    |    .fillColor(Color.red)`    |
+-----------------------------+-----------+-------------------------+------------------------------+
| `strokeColor`               | `Color`   | Outlines the image with | `Image.circle(10)            |
|                             |           | the specified color.    |    .strokeColor(Color.blue)` |
+-----------------------------+-----------+-------------------------+------------------------------+
| `strokeWdith`               | `Double`  | Sets the width of the   | `Image.circle(10)            |
|                             |           | image outline.          |    .strokeWidth(3)`          |
+-----------------------------+-----------+-------------------------+------------------------------+
| `noFill`                    | None      | Removes any fill from   | `Image.circle(10).noFill`    |
|                             |           | the image.              |                              |
+-----------------------------+-----------+-------------------------+------------------------------+
| `noStroke`                  | None      | Removes any stroke from | `Image.circle(10).noStroke`  |
|                             |           | the image.              |                              | 
+-----------------------------+-----------+-------------------------+------------------------------+

: Some of the methods to add color to images in Doodle. @:tref(pictures:color)

Doodle has various ways of creating colours.
The simplest are the predefined colours in [CommonColors.scala][common-colors].
Some of the most commonly used are described in @:tref(pictures:colors).

+--------------+-------+------------------------------------------+
| Color        | Type  | Example                                  |
+==============+=======+==========================================+
|`Color.red`   |`Color`| `Image.circle(10).fillColor(Color.red)`  |
+--------------+-------+------------------------------------------+
|`Color.blue`  |`Color`| `Image.circle(10).fillColor(Color.blue)` |
+--------------+-------+------------------------------------------+
|`Color.green` |`Color`| `Image.circle(10).fillColor(Color.green)`|
+--------------+-------+------------------------------------------+
|`Color.black` |`Color`| `Image.circle(10).fillColor(Color.black)`|
+--------------+-------+------------------------------------------+
|`Color.white` |`Color`| `Image.circle(10).fillColor(Color.white)`|
+--------------+-------+------------------------------------------+
|`Color.gray`  |`Color`| `Image.circle(10).fillColor(Color.gray)` |
+--------------+-------+------------------------------------------+
|`Color.brown` |`Color`| `Image.circle(10).fillColor(Color.brown)`|
+--------------+-------+------------------------------------------+

: Some of the most common predefined colors. @:tref(pictures:colors)

### Exercises {-}

#### Evil Eye {-}

Make the image in @:fref(pictures:evil-eye), designed to look like a traditional amulet protecting against the evil eye. I used `cornflowerBlue` for the iris, and `darkBlue` for the outer color, but experiment with your own choices!

@:figure{ img = "evil-eye.svg", key = "pictures:evil-eye", caption = "No evil eyes here!" }

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
