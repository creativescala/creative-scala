# Creative Extensions

We're now going to look at a few creative extensions of what we've been working with so far. This gives us more practice with these core ideas: polar coordinates, paths, and structural recursion over the natural numbers. It also might show you a few tricks to use if you're interested in your own creative computing applications.

## Concentric Polygons

We've created concentric shapes before. It's natural to use this same idea with polygons. In my example I've also increased the number of sides in the polygon as the shape gets bigger. The colors are created by smoothly reducing the lightness to zero as the polygons get larger.

@:doodle("concentric", "Creative.concentricExercise")


## Polygon Spiral

This next example takes the idea of concentric polygons and adds a small rotation at each step. Here I've used a fill instead of a stroke. Alternating between white and black fills give the result an [Op Art](https://en.wikipedia.org/wiki/Op_art) style.

@:doodle("spiral", "Creative.spiralExercise")


## Star Polygons

Our method of constructing polygons was to join each vertex to its neighbours with a straight line. If we skip one or more points we get what is called a star polygon.

@:doodle("star-polygon", "Creative.starPolygonExercise")


## Curvygons

I couldn't resist animating this one, but I'm not expecting you to do so. (We haven't discussed how to create animations, but you can check the [Doodle documentation](https://www.creativescala.org/doodle/interact/animation.html).)

@:doodle("curvygon", "Creative.curvygonExercise")


