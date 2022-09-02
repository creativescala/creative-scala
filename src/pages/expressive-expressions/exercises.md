## Exercises

```scala mdoc:invisible
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._
```

### Compilation Target

Create a line drawing of an archery target with three concentric scoring bands, as shown in @:fref(pictures:target1).

@:figure{ img = "src/pages/pictures/target1.pdf+svg", key = "#fig:pictures:target1", caption = "Simple archery target" }

For bonus credit add a stand so we can place the target on a range, as shown in @:fref(pictures:target2).

@:figure{ img = "src/pages/pictures/target2.pdf+svg", key = "#fig:pictures:target2", caption = "Archery target with a stand" }

<div class="solution">
The simplest solution is to create three concentric circles using the `on` method:

```scala mdoc:silent
Image
  .circle(20)
  .on(Image.circle(40))
  .on(Image.circle(60))
```

For the extra credit we can create a stand using two rectangles:

```scala mdoc:silent
Image
  .circle(20)
  .on(Image.circle(40))
  .on(Image.circle(60))
  .above(Image.rectangle(6, 20))
  .above(Image.rectangle(20, 6))
```
</div>


### Stay on Target

Colour your target red and white, the stand in brown (if applicable),
and some ground in green. See @:fref(pictures:target3) for an example.

@:figure{ img = "src/pages/pictures/target3.pdf+svg", key = "#fig:pictures:target3", caption = "Colour archery target" }

<div class="solution">
The trick here is using parentheses to control the order of composition.
The `fillColor()`, `strokeColor()`, and `strokeWidth()` methods
apply to a single image---we need to make sure that image
comprises the correct set of shapes:

```scala mdoc:silent
Image
  .circle(20).fillColor(Color.red)
  .on(Image.circle(40).fillColor(Color.white))
  .on(Image.circle(60).fillColor(Color.red))
  .above(Image.rectangle(6, 20).fillColor(Color.brown))
  .above(Image.rectangle(20, 6).fillColor(Color.brown))
  .above(Image.rectangle(80, 25).noStroke.fillColor(Color.green))
```
</div>
