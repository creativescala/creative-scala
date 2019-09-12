## Exercises

```scala mdoc:invisible
import doodle.core._
import doodle.image._
import doodle.image.syntax._
import doodle.image.syntax.core._
import doodle.java2d._
```

### Compilation Target

Create a line drawing of an archery target with three concentric scoring bands, as shown in [@fig:pictures:target1].

![Simple archery target](src/pages/pictures/target1.pdf+svg){#fig:pictures:target1}

For bonus credit add a stand so we can place the target on a range, as shown in [@fig:pictures:target2].

![Archery target with a stand](src/pages/pictures/target2.pdf+svg){#fig:pictures:target2}

<div class="solution">
The simplest solution is to create three concentric circles using the `on` operator:

```scala mdoc:silent
(Image.circle(10) on Image.circle(20) on Image.circle(30))
```

For the extra credit we can create a stand using two rectangles:

```scala mdoc:silent
(
  Image.circle(10)
    .on(Image.circle(20))
    .on(Image.circle(30))
    .above(Image.rectangle(6, 20)
      .above(Image.rectangle(20, 6)))
)
```
</div>


### Stay on Target

Colour your target red and white, the stand in brown (if applicable),
and some ground in green. See [@fig:pictures:target3] for an example.

![Colour archery target](src/pages/pictures/target3.pdf+svg){#fig:pictures:target3}

<div class="solution">
The trick here is using parentheses to control the order of composition.
The `fillColor()`, `strokeColor()`, and `strokeWidth()` methods
apply to a single image---we need to make sure that image
comprises the correct set of shapes:

```scala mdoc:silent
(
  ( Image.circle(10).fillColor(Color.red) ) on
  ( Image.circle(20).fillColor(Color.white) ) on
  ( Image.circle(30).fillColor(Color.red).strokeWidth(2) ) above
  ( Image.rectangle(6, 20).above(Image.rectangle(20, 6)).fillColor(Color.brown) ) above
  ( Image.rectangle(80, 25).strokeWidth(0).fillColor(Color.green) )
)
```
</div>
