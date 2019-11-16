## Reactors

```scala mdoc:invisible
import doodle.core._
import doodle.image._
import doodle.image.syntax._
import doodle.image.syntax.core._
import doodle.java2d._
import doodle.reactor._
```

We will create animations using a tool called a *reactor*. A reactor allows us to write an animation in terms of three things:

- some initial value, such as a `Point`; 
- an update function that transforms the value into its next value every clock tick, such as moving the `Point`; and
- a rendering function that turns the value into an `Image`.

Here's an example.

```scala mdoc:silent
val animation =
  Reactor.init(Point(0, 300))
    .onTick(pt => pt.rotate(2.degrees))
    .render(pt => Image.circle(10).at(pt.toVec))
```

As you can see we call three methods on `Reactor`, `init`, `onTick`, and `render` passing the initial value, the update function, and the rendering function respectively.

This constructs a reactor but it does not draw it. To do this we must call the `run` method, passing a `Frame` that tells the reactor how big to make the canvas it draws on. Here's an example:

```scala
animation.run(Frame.size(600, 600))
```

This generates the animation shown in [@fig:reactor:orbit].

![A circle moving in a circular orbit.](./src/pages/reactor/orbit.pdf+svg){#fig:reactor:orbit}


#### Exercise: Rose Curve

Make an animation where an image moves in a rose curve (we saw the rose curve in an earlier chapter). Be as creative as you wish. You might find it fun to change the background of the `Frame` on which you draw the animation; a dark background is often more effective than a light one. You can do this by calling the `background` method on `Frame`. For example, here is how you'd create a 600 by 600 frame with a dark blue background.

```scala mdoc:silent
Frame.size(600, 600).background(Color.midnightBlue)
```

Remember you will need to import `doodle.reactor._` to make the reactor library available.
