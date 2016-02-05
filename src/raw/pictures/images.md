## Images

*Possible Doodle install here.*

Let's start with some simple shapes, programming at the console as we've done before.

```tut:book
Image.circle(10)
```

What is happening here? `Image` is an object and `circle` a method on that object. We pass to `circle` a parameter, `10` that gives the radius of the circle we're constructing. Note the type of the result---an `Image`.

We can also write just `circle(10)`, as if you run the console within Doodle it automatically makes this and other methods to construct images available.

How do we draw this circle? Call the `draw` method.

```tut:book
circle(10).draw
```

A window should appear containing the following:

![A circle](src/pages/expressions/circle.png)

Doodle supports a handful "primitive" images:
circles, rectangles, and triangles.
Let's try drawing a rectangle:

~~~ scala
rectangle(50, 100).draw
~~~

![A rectangle](src/pages/expressions/rectangle.png)

Finally let's try a triangle:

~~~ scala
triangle(60, 40).draw
~~~

![A triangle](src/pages/expressions/triangle.png)
