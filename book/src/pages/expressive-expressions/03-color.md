## Color

```scala mdoc:invisible
import doodle.core.*
import doodle.image.*
import doodle.syntax.all.*
import doodle.image.syntax.all.*
import doodle.java2d.*
```

It would be nice to add some color to our images.
No surprises that Doodle has methods to do just this.
In addition to layout, Doodle has some simple operators to add a splash of colour to our images. Try these out the methods described in @:tref(pictures:color) to see how they work.

* `fillColor` fills the image with the given color. Example:

  ```scala
  Image.circle(100).fillColor(Color.hotpink) 
  ```
* `strokeColor` outlines the image with the given color. Example:

  ```scala
  Image.circle(100).strokeColor(Color.chartreuse) 
  ```
* `strokeWidth` sets the width of the image outline. Example:

  ```scala
  Image.circle(100).strokeWidth(3) 
  ```
* `noFill` removes any fill from the image. Example:

  ```scala
  Image.circle(100).noFill
  ```
* `noStroke` removes any stroke from the image. Example:

  ```scala
  Image.circle(100).noStroke
  ```

Doodle has various ways of creating colours.
The simplest are the predefined colours on [`Color`][color].
We use these colors by writing `Color.black`, `Color.blue`, and so on.

Here is a small example illustrating these methods in action.

```scala mdoc:silent
Image
  .circle(100)
  .fillColor(Color.wheat)
  .strokeColor(Color.midnightBlue)
  .strokeWidth(7)
```

@:figure{ img = "color-example.svg", key = "fig:expressive-expressions:color-example", caption = "A circle with a dark blue border and a beige fill." }


## Exercises

@:exercise(Evil Eye)

Make the image in @:fref(pictures:evil-eye), designed to look like a traditional amulet protecting against the evil eye. I used `cornflowerBlue` for the iris, and `darkBlue` for the outer color, but experiment with your own choices!

@:figure{ img = "evil-eye.svg", key = "fig:expressive-expressions:evil-eye", caption = "No evil eyes here!" }

@:@
@:solution
Here's my amulet:

```scala mdoc:silent
Image
  .circle(30)
  .fillColor(Color.black)
  .on(Image.circle(60).fillColor(Color.cornflowerBlue))
  .on(Image.circle(90).fillColor(Color.white))
  .on(Image.circle(150).fillColor(Color.darkBlue))
```
@:@

[color]: https://javadoc.io/doc/org.creativescala/doodle-docs_3/latest/doodle/core/Color$.html
