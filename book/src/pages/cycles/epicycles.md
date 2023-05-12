# Epicycles

We're now going to see a different example of composition, in creating a type of curve known as an epicycle. An epicycle is produced when we trace a point on a circle rotating on another circle. We can stack circles on top of circles, and change the speed at which they rotate, to produce many different curves. 

@:doodle("epicycle", "CyclesEpicycles.epicycleTwoWheels")

For our example we are going to fix the number and radius of the circles and allow their speed of rotation to vary. Here is the code:

```scala
def epicycle(a: Int, b: Int, c: Int): Angle => Point =
  (angle: Angle) =>
    (Point(75, angle * a).toVec + Point(32, angle * b).toVec + Point(15, angle * c).toVec).toPoint
```

You might notice this code converts points to vectors and back again. This is a little technical detail (we cannot add points but we can add vectors) that isn't important if you aren't familiar with vectors.

Below are three examples created by choosing the parameters a, b, c, as (1, 6, 14), (7, 13, 25), and (1, 7, -21) respectively.

@:figure{ img = "./epicycle.svg", key = "#fig:cycles:epicycle", caption = "Examples of epicycle curves, with the parameters chosen as (1, 6, 14), (7, 13, 25), and (1, 7 -21)." }
