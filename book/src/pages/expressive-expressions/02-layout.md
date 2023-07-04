# Layout

```scala mdoc:invisible
import doodle.core.*
import doodle.image.*
import doodle.syntax.all.*
import doodle.image.syntax.all.*
import doodle.java2d.*
```

We can seen how to create basic images. 
We can combine images using layouts methods to create more complex images. 
Try the following code. 
You should see a circle and a rectangle displayed beside one another, as shown below.

```scala
(Image.circle(100).beside(Image.rectangle(100, 200))).draw()
```

@:figure{ img = "circle-beside-rectangle.svg", key = "#fig:expressive-expressions:circle-rect", caption = "A circle beside a rectangle" }

`Image` contains many layout methods for combining images. 
Here are one we'll use to start with.

* `beside` places images horizontally next to one another. Example:

  ```scala
  Image.circle(100).beside(Image.circle(50)) 
  ```
* `above` places images vertically next to one another. Example:

  ```scala
  Image.circle(100).above(Image.circle(50)) 
  ```

* `on` places images centered on top of one another. Example:

  ```scala
  Image.circle(100).on(Image.circle(50)) 
  ```

Try out these methods until you're sure you understand them.


## Exercises

@:exercise(The Width of a Circle)

Create the picture below using the layout methods and basic images we've covered so far.

@:figure{ img = "width-of-a-circle.svg", key = "#fig:expressive-expressions:width-of-a-circle", caption = "The width of a circle" }
@:@

@:solution
It's three small circles on top of a bigger circle, and we can just about state this as is in code.

```scala mdoc
Image
  .circle(60)
  .beside(Image.circle(60))
  .beside(Image.circle(60))
  .on(Image.circle(180))
```
@:@
