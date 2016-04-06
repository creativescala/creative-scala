## Parametric Curves

```tut:invisible
import doodle.core._
import doodle.core.Image._
import doodle.syntax._
import doodle.jvm.Java2DCanvas._
import doodle.backend.StandardInterpreter._
```

We'll start by drawing a circle. We already know how to do this. Just write `circle(10).draw`, for example. Instead of using the built-in method, however, let's plot points on a circle ourselves. This will give us the control we need to change the change the shape of the circle, producing a flower shape.

We can define a circle using what's called a *parametric equation*. This is a fancy term for a function that takes some input (the parameter in parametric) and returns an x and y coordinate. 

In Doodle we have a `Point` type to represent x and y coordinates. For our input we'll take an `Angle`, which tells us where on the circle we are. So for us, our parametric equations will be methods or functions from `Angle` to `Point`. A bit of geometry shows us that given an angle `angle` and x and y coordinates should be `cos(angle)` and `sin(angle)` respectively. We can define the parametric equation for a circle like so:

```tut:book
def parametricCircle(angle: Angle): Point =
  Point.cartesian(angle.cos, angle.sin)
```

Now we could sample a point on the circle every, say, 1 degree, giving us some fairly closely spaced points around the perimeter of the circle. To create an image we can draw something at each point (say, a triangle). We haven't yet seen how to do this in Doodle. We can use the `at` method to put an `Image` as a specific point relative. This method takes a vector, with type `Vec`. We can convert a `Point` to a `Vec` using the `toVec` method[^at-layout]. Here's the code.

[^at-layout]: Each `Image` in Doodle is enclosed by a bounding box, which is used for layout. The bounding box defines a local coordinate system with an origin that must be somewhere inside that box. By convention we make the origin the center of the box, but this is not always the case. Layout using `above`, `beside`, and so on, aligns the origins of the bounding boxes. When we layout an image using `at` it creates a bounding box with an origin and the image is displaced relative to that origin. This is why `at` takes a vector (a relative displacement) rather than a point.

```tut:book
def sample(start: Angle, increment: Angle): Image =
  if(start > Angle.One) // Angle.One is one complete turn. I.e. 360 degrees
    Image.empty
  else
    triangle(10, 10) at (parametricCircle(start).toVec) on sample(start+increment, increment)
```

This is a structural recursion, which is hopefully a familiar pattern by now.

If we draw this we'll see a whole lot of triangles on top of each other---the circle we define with `parametricCircle` only has a radius of one! We need to make the circle a bit bigger. We can use the `lengthen` method on `Point` to do this. Let's make the circle have a radius of 100 units.

```tut:book
def sample(start: Angle, increment: Angle): Image = {
  if(start > Angle.One) // Angle.One is one complete turn. I.e. 360 degrees
    Image.empty
  else
    triangle(10, 10) at (parametricCircle(start).lengthen(100).toVec) on sample(start+increment, increment)
}
```

This generates a useful result. [@fig:hof:triangle-circle] shows the result of `sample(0.degrees, 5.degrees)`.

![Triangles arranged in a circle, using the code from `sample` above.](src/pages/hof/triangle-circle.png){#fig:hof:triangle-circle}

### Flowers

The next step to creating a flower is to using a more interesting shape than a circle. That means changing `parametricCircle` for a more interesting equation. Perhaps `rose` below.

```tut:book
// Parametric equation for rose with k = 7
def rose(angle: Angle) =
  Point.cartesian((angle * 7).cos * angle.cos, (angle * 7).cos * angle.sin)
```

If would be nice if we could pass to `sample` the method that creates points (i.e. the parametric equation). Can we do this? To do so we would have to be able to differentiate between calling a method (e.g. `rose(0.degress)`) and referring to the method itself. If we try referring to method without calling it we get an error.

```tut:fail
rose
// error: missing argument list for method rose
// Unapplied methods are only converted to functions when a function type is expected.
// You can make this conversion explicit by writing `rose _` or `rose(_,_)` instead of `rose`.
//  rose
//  ^
```

The error message handily tells us what we need to do, and this is where we introduce functions.
