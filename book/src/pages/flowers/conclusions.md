## Conclusions

```scala mdoc:invisible
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._
```

In this chapter we looked at functions. We saw that functions are, unlike methods *first-class values*. This means we can pass functions to methods (or other functions), return them from methods and functions, and give them a name using `val`.

Functions are one of our core tools for abstraction. Remember that abstraction is removing irrelevant details. We've seen that names allow us to abstract over expressions. We can replace multiple occurrences of an expression with a name. So, we can replace

```scala mdoc:silent
Image.circle(10).fillColor(Color.royalBlue).beside(Image.circle(10).fillColor(Color.royalBlue))
```

with

```scala mdoc:silent
val circle = Image.circle(10).fillColor(Color.royalBlue)
circle.beside(circle)
```

We've also seen that methods allow us to abstract over several expressions that are the same in some places but differ in others. The parts that differ become the parameters to the method. Here's an example. We can replace

```scala mdoc:silent
Image.circle(10).fillColor(Color.royalBlue).beside(Image.circle(20).fillColor(Color.royalBlue))
```

with

```scala mdoc:silent
def makeCircle(size: Int): Image =
  Image.circle(size).fillColor(Color.royalBlue)
  
makeCircle(10).beside(makeCircle(20))
```

A method captures a computation that allows the caller to change certain parts of the computation.

What new form of abstraction do functions provide? We've seen two uses in this chapter: passing functions to methods and returning functions from methods. Passing a function to a method allows the method to choose the parameters that the function is applied to. This allows us to split the computation between the method and the function, so the method can abstract over *part* of the computation and the function can abstract over the rest. Returning a function from a method is essentially the same. Therefore we can see that first-class functions allow us to better split up computations than we can without them. In the next chapter we'll see one more use of functions, function composition, which takes this idea and builds new functions from functions.  This will close out this first part of the book focused on creative computing.
