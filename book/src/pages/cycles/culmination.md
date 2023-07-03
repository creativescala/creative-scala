# Bringing it Together

We're now going to bring together everything we've used in this chapter to create our final image and close out this part of the book.

We've built a whole raft of methods that all work with a common abstraction of `Angle => Point` functions.
For example, we can use `on`, which we developed to compose wheels, with any parametric curve. 
In the example below we have a rose curve rotating around (`on`) a Lissajous curve, which gives an interesting curve that would be difficult to come up with by hand.

@:figure{ img = "./lissajous-rose.svg", key = "#fig:cycles:lissajous-rose", caption = "A rose curve rotating around a Lissajous curve." }

Placing a wheel on a Lissajous curve also gives an interesting result.

@:figure{ img = "./wheel-lissajous.svg", key = "#fig:cycles:wheel-lissajous", caption = "A rose curve rotating around a Lissajous curve." }

Remember we're not restricted to interpolating splines.
Here's the same curve, but I'm drawing circles at selected points (where the size and color of the circle depends on the angle).

@:figure{ img = "./wheel-lissajous-circles.svg", key = "#fig:cycles:wheel-lissajous-circles", caption = "A rose curve rotating around a Lissajous curve." }

Now it's over to you.
Give yourself a good chunk of time and, using the tools we've made, create an image that you're proud of to mark completion of this part of the book.
