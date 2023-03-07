## Reactors

```scala mdoc:invisible
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._
import doodle.reactor._
```

We will create animations using a tool called a *reactor*. A reactor allows us to write an animation in terms of three (and one optional) things:

- some initial value, such as a `Point`; 
- an update function that transforms the value into its next value every clock tick, such as moving the `Point`;
- a rendering function that turns the value into an `Image`; and
- an optional condition that determines when the animation stop.

Here's an example that moves a circle from left to right, stopping when the the circle gets to the point (300, 0).

```scala mdoc:silent
val travellingCircle =
  Reactor.init(Point(-300, 0))
    .withOnTick(pt => Point(pt.x + 1, pt.y))
    .withRender(pt => Image.circle(10).at(pt))
    .withStop(pt => pt.x >= 300)
```

(We could write the `onTick` function as `pt -> pt + Vec(1,0)` if we're comfortable with vector arithmetic.)

This constructs a reactor but it does not draw it. To do this we must call the `run` method, passing a `Frame` that tells the reactor how big to make the window it draws on. Here's an example:

```scala
travellingCircle.run(Frame.size(600, 600))
```

This generates the animation shown in @:fref(reactor:travelling).

@:figure{ img = "./src/pages/reactor/travelling.pdf+svg", key = "#fig:reactor:travelling", caption = "A circle moving from left to right." }


Here's an another example that moves a circle in a circular orbit. This time  the animation has no stopping condition, so it continues forever.

```scala mdoc:silent
val orbitingCircle =
  Reactor.init(Point(0, 300))
    .withOnTick(pt => pt.rotate(2.degrees))
    .withRender(pt => Image.circle(10).at(pt))
```

We run this reactor in the same way.

```scala
orbitingCircle.run(Frame.size(600, 600))
```

This generates the animation shown in @:fref(reactor:orbit).

@:figure{ img = "./src/pages/reactor/orbit.pdf+svg", key = "#fig:reactor:orbit", caption = "A circle moving in a circular orbit." }


#### Exercise: Rose Curve

Make an animation where an image moves in a rose curve (we saw the rose curve in an earlier chapter). Be as creative as you wish. You might find it fun to change the background of the `Frame` on which you draw the animation; a dark background is often more effective than a light one. You can do this by calling the `background` method on `Frame`. For example, here is how you'd create a 600 by 600 frame with a dark blue background.

```scala mdoc:silent

Frame.default.withSize(600, 600).withBackground(Color.midnightBlue)
```

Remember you will need to import `doodle.reactor._` to make the reactor library available.
