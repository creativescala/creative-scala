# DISCUSSION POINTS

Values and expressions
 - Archery target

Types:
 - Coloured target (Image, Circle, Color, etc)
 - Extra questions in there...
   (if(a) b else c)

Value declarations:
 - Chessboard
 - ???

Methods
 - Colour palettes / complimentary colours / etc
 - Polygons?

Recursion
 - Fractal - sierpinski?

Sealed traits and structural recursion
 - Skip this?

Function composition / higher order functions
 - Animations:
   ~~~ scala
   ( Long => (Double, Double) ) andThen ( (Double, Double) => Image )

   trait Drawable {
     def draw: Image
   }

   trait Animation[A <: Drawable] {
     def animate(input: A): A = ???
   }
   ~~~

Collections
 - Caterpillar eating stuff?
 - Starfield? Particle system?
 - Drawing a chart?
   List[Point] -> Image

---

Animations as parametric functions

Classes
- represent points

~~~ scala
trait Scene {
  def apply(time: Long): Image
}

case class Star(size: Int, color: Color, ???) {
  def draw(time: Long): Image = ???
}
~~~

Comprehensions / flatMap / map:
 - ???

Types as error handling:
 - Normalised

Colors:
 - Function literals
 - Method declarations
 - Complimentary / analagous colors / triads / tetrads

- Traffic lights
