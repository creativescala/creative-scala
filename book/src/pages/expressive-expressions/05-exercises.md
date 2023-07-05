# Exercises

```scala mdoc:invisible
import doodle.core.*
import doodle.image.*
import doodle.syntax.all.*
import doodle.image.syntax.all.*
import doodle.java2d.*
```

@:exercise(Compilation Target)

Create a line drawing of an archery target with three concentric scoring bands, as shown below.

@:figure{ img = "target1.svg", key = "#fig:pictures:target1", caption = "Simple archery target" }

For bonus credit add a stand so we can place the target on a range, as below.

@:figure{ img = "target2.svg", key = "#fig:pictures:target2", caption = "Archery target with a stand" }
@:@

@:solution
The simplest solution is to create three concentric circles using the `on` method:

```scala mdoc:silent
Image
  .circle(40)
  .on(Image.circle(80))
  .on(Image.circle(120))
```

For the extra credit we can create a stand using two rectangles:

```scala mdoc:silent
Image
  .circle(40)
  .on(Image.circle(80))
  .on(Image.circle(120))
  .above(Image.rectangle(12, 40))
  .above(Image.rectangle(40, 12))
```
@:@


@:exercise(Stay on Target)

Colour your target red and white, the stand in brown (if applicable),
and some ground in green. The example below shows what we are aiming for.

@:figure{ img = "target3.svg", key = "#fig:pictures:target3", caption = "Colour archery target" }
@:@

@:solution
The trick here is using parentheses to control the order of composition.
The `fillColor()`, `strokeColor()`, and `strokeWidth()` methods
apply to a single image, so we need to make sure that image
comprises the correct set of shapes.

```scala mdoc:silent
Image
  .circle(40)
  .fillColor(Color.red)
  .on(Image.circle(80).fillColor(Color.white))
  .on(Image.circle(120).fillColor(Color.red))
  .above(Image.rectangle(12, 40).fillColor(Color.brown))
  .above(Image.rectangle(40, 12).fillColor(Color.brown))
  .above(Image.rectangle(160, 50).noStroke.fillColor(Color.green))
```
@:@

