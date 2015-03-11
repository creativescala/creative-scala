\appendix

# Syntax Quick Reference {#syntax-quick-reference}

## Literals and Expressions

~~~ scala
// Literals:
123      // Int
123.0    // Double
"Hello!" // String
true     // Boolean

// Math:
10 + 2   // Int + Int    = Int
10 + 2.0 // Int + Double = Double
10 / 2   // Int / Int    = Double

// Boolean logic:
true && false // logical AND
true || false // logical OR
!true         // logical NOT

// String concatenation:
"abc" + "def" // String
"abc" + 123   // auto-conversion from Int to String

// Method calls and infix operators:
1.+(2)    // method call style
1 + 2     // infix operator style
1 + 2 + 3 // equivalent to 1.+(2).+(3)

// Conditionals:
if(booleanExpression) expressionA else expressionB

// Blocks:
{
  sideEffectExpression1
  sideEffectExpression2
  resultExpression
}
~~~

## Value and Method Declarations

~~~ scala
// Value declaration syntax:
val valueName: SomeType = resultExpression // declaration with explicit type
val valueName = resultExpression           // declaration with inferred type

// Method with parameter list and explicit return type:
def methodName(argName: ArgType, argName: ArgType): ReturnType =
  resultExpression

// Method with parameter list and inferred return type:
def methodName(argName: ArgType, argName: ArgType) =
  resultExpression

// Multi-expression method (using a block):
def methodName(argName: ArgType, argName: ArgType): ReturnType = {
  sideEffectExpression1
  sideEffectExpression2
  resultExpression
}

// Method with no parameter list:
def methodName: ReturnType =
  resultExpression

// Calling a method that has a parameter list:
methodName(arg, arg)

// Calling a method that has no parameter list:
methodName
~~~

## Functions as Values

~~~ scala
// Function value syntax:
(argName: ArgType, argName: ArgType) => resultExpression

// Use blocks if you want multi-line bodies:
(argName: ArgType, argName: ArgType) => {
  sideEffectExpression1
  sideEffectExpression2
  resultExpression
}

// Function type syntax
// (use as a return type or parameter type):
(ArgType, ArgType) => ResultType

// Passing a function as a parameter:
def myMethod(funcName: (ArgType, ArgType) => ResultType) =
  resultExpression

// Returning a function:
def myMethod(...): (ArgType, ArgType) => ResultType =
  resultExpression
~~~

## Doodle Reference Guide

### Imports

~~~ scala
// These imports get you everything you need:
import doodle.core._
import doodle.syntax._
~~~

### Creating Images

~~~ scala
// Primitive images (black outline, no fill):
val i: Image = Circle(radius)
val i: Image = Rectangle(width, height)
val i: Image = Triangle(width, height)

// Compound images written using operator syntax:
val i: Image = imageA beside imageB // horizontally adjacent
val i: Image = imageA above  imageB // vertically adjacent
val i: Image = imageA below  imageB // vertically adjacent
val i: Image = imageA on     imageB // superimposed
val i: Image = imageA under  imageB // superimposed

// Compound images written using method call syntax:
val i: Image = imageA.beside(imageB)
// etc...
~~~

### Styling Images

~~~ scala
// Styling images written using operator syntax:
val i: Image = image fillColor color   // new fill color (doesn't change line)
val i: Image = image lineColor color   // new line color (doesn't change fill)
val i: Image = image lineWidth integer // new line width (doesn't change fill)
val i: Image = image fillColor color lineColor otherColor // new fill and line

// Styling images using method call syntax:
val i: Image = imageA.fillColor(color)
val i: Image = imageA.fillColor(color).lineColor(otherColor)
// etc...
~~~

### Colours

~~~ scala
// Basic colors:
val c: Color = Color.red                       // predefined colors
val c: Color = Color.rgb(255, 127, 0)          // RGB color
val c: Color = Color.rgba(255, 127, 0, 0.5)    // RGBA color
val c: Color = Color.hsl(0.5, 0.25, 0.5)       // HSL color
val c: Color = Color.hsla(0.5, 0.25, 0.5, 0.5) // HSLA color

// Transforming/mixing colors using operator syntax:
val c: Color = someColor spin       10.degrees     // change hue
val c: Color = someColor lighten    0.1.normalized // change brightness
val c: Color = someColor darken     0.1.normalized // change brightness
val c: Color = someColor saturate   0.1.normalized // change saturation
val c: Color = someColor desaturate 0.1.normalized // change saturation
val c: Color = someColor fadeIn     0.1.normalized // change opacity
val c: Color = someColor fadeOut    0.1.normalized // change opacity

// Transforming/mixing colors using method call syntax:
val c: Color = someColor.spin(10.degrees)
val c: Color = someColor.lighten(0.1.normalized)
// etc...
~~~

### Paths

~~~ scala
// Create path from list of PathElements:
val i: Image = Path(List(
  MoveTo(Vec(0, 0)),
  LineTo(Vec(10, 10))
))

// Create path from other sequence of PathElements:
val i: Image = Path(
  (0 until 360 by 30) map { i =>
    LineTo(Vec.polar(i.degrees, 100))
  }
)

// Types of element:
val e1: PathElement = MoveTo(toVec)                        // no line
val e2: PathElement = LineTo(toVec)                        // straight line
val e3: PathElement = BezierCurveTo(cp1Vec, cp2Vec, toVec) // curved line

// NOTE: If the first element isn't a MoveTo,
//       it is converted to one
~~~

### Angles and Vecs

~~~ scala
val a: Angle = 30.degrees                // angle in degrees
val a: Angle = 1.5.radians               // angle in radians
val a: Angle = math.Pi.radians           // π radians
val a: Angle = 1.turns                   // angle in complete turns

val v: Vec = Vec.zero                    // zero vector (0,0)
val v: Vec = Vec.unitX                   // unit x vector (1,0)
val v: Vec = Vec.unitY                   // unit y vector (0,1)

val v: Vec = Vec(3, 4)                   // vector from cartesian coords
val v: Vec = Vec.polar(30.degrees, 5)    // vector from polar coords
val v: Vec = Vec(2, 1) * 10              // multiply length
val v: Vec = Vec(20, 10) / 10            // divide length
val v: Vec = Vec(2, 1) + Vec(1, 3)       // add vectors
val v: Vec = Vec(5, 5) - Vec(2, 1)       // subtract vectors
val v: Vec = Vec(5, 5) rotate 45.degrees // rotate counterclockwise

val x: Double = Vec(3, 4).x              // x coordinate
val y: Double = Vec(3, 4).y              // y coordinate
val a: Angle  = Vec(3, 4).angle          // counterclockwise from (1, 0)
val l: Double = Vec(3, 4).length         // length
~~~