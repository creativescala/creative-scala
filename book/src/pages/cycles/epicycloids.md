# Epicycloids

There are an unlimited number of functions we could use to create interesting curves. Let's see one more example, this time of what known as an [epicycloid][epicycloid]. An epicycloid is produced when we trace a point on a circle rotating around another circle. We can stack circles on top of circles, and change the speed at which they rotate, to produce many different curves. For our example we are going to fix the number and radius of the circles and allow their speed of rotation to vary. Here is the code:

```scala
def epicycloid(a: Int, b: Int, c: Int): Angle => Point =
  (angle: Angle) =>
    (Point(75, angle * a).toVec + Point(32, angle * b).toVec + Point(15, angle * c).toVec).toPoint
```

You might notice this code converts points to vectors and back again. This is a little technical detail (we cannot add points but we can add vectors) that isn't important if you aren't familiar with vectors.

Below are three examples created by choosing the parameters a, b, c, as (1, 6, 14), (7, 13, 25), and (1, 7, -21) respectively.

@:figure{ img = "./epicycloid.svg", key = "#fig:hof:epicycloid", caption = "Examples of epicycloid curves, with the parameters chosen as (1, 6, 14), (7, 13, 25), and (1, 7 -21)." }

[epicycloid]: https://en.wikipedia.org/wiki/Epicycloid
