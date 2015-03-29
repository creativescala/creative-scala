## Transforming Sequences

In imperative programs we use loops to iterate over lists
and transform them to produce new values.
For example, here is a Java 7 method
to double the numbers in an `ArrayList`:

~~~ java
List<Integer> doubleAll(List<Integer> numbers) {
  List<Integer> ans = new ArrayList<Integer>();

  for(int i : numbers) {
    ans.push(i * 2);
  }

  return ans;
}

List<Integer> result = doubleAll(new ArrayList<Integer>(1, 2, 3, 4, 5));
~~~

There are a lot of lines in this example, many of which
aren't to do with the desired operation of doubling numbers.
We have to allocate a temporary list and push numbers onto it
before returning it, all of which ought to be be handled by library code.

We can't easily abstract away the temporary list allocation in Java 7
because we have no direct way of abstracting the doubling operation.
In Scala and Java 8 we can represent doubling succinctly
using a function literal, aka a "closure":

~~~ scala
(x: Int) => x * 2
// res2: Int => Int = <function1>

res2(10)
// res3: Int = 20
~~~

Scala's `List` class has a method called `map` that allows us to
exploit functions to remove all of the boilerplate from our Java 7 example.
`map` accepts a function as a parameter and returns a new `List`
created by applying the function to every item:

~~~ scala
List(1, 2, 3, 4, 5).map(i => i * 2)
// res4: List[Int] = List(2, 4, 6, 8, 10)
~~~

We can use the `map` method to convert `Lists` of values
to `Lists` of `Images`:

~~~ scala
val radii = List(10, 20, 30, 40, 50)
// radii: List[Int] = List(10, 20, 30, 40, 50)

val circles = radii.map(i => Circle(i * 10))
// circles: List[doodle.core.Circle] = // ...
~~~

Doodle contains a handful of convenient methods to convert
values of type `List[Image]` to single `Images`.
One of these is `allBeside`,
which lays a list of images out beside one another:

~~~ scala
draw(allBeside(circles))
~~~

![A row of circles](src/pages/collections/circles.png)

Other combinators are listed below:

------------------------------------------------------------------------------------------------
Operator                  Type    Description                Example
------------------------- ------- -------------------------- -----------------------------------
`allBeside(listOfImages)` `Image` Places images horizontally `allBeside(Circle(10), Circle(20))`
                                  next to one another.

`allAbove(listOfImages)`  `Image` Places images vertically   `allAbove(Circle(10), Circle(20))`
                                  above one another.

`allBelow(listOfImages)`  `Image` Places images vertically   `allBelow(Circle(10), Circle(20))`
                                  below one another.

`allOn(listOfImages)`     `Image` Places images centered     `allOn(Circle(10), Circle(20))`
                                  on top of one another.

`allUnder(listOfImages)`  `Image` Places images centered     `allUnder(Circle(10), Circle(20))`
                                  underneath one another.
-----------------------------------------------------------------------------------------------

We can recreate our concentric circles example trivially
using `allOn` or `allUnder`.
Much simpler than writing a recursive method!

~~~ scala
val radii = List(10, 20, 30, 40, 50)
// radii: List[Int] = List(10, 20, 30, 40, 50)

val circles = radii.map(i => Circle(i))
// circles: List[Circle] = // ...

draw(allOn(circles))
~~~

**Exercise: Colour Palette**

Create an application to show the range of colours you can get in HSL space.
Create a two-dimensional grid of rectangles,
with hue varying from 0 to 360 degrees on the x-axis and
lightness varying from 0.0 to 1.0 on the y-axis.

![HSL Colour Palette](src/pages/collections/colour-palette-squares.png)

Here are some tips:

 1. Think about the structure of your image
    and build it from the bottom up.
    Start by building a single square of constant hue and lightness,
    then compose a sequence of squares into a single column,
    then compose a sequence of columns together to form the palette.

 2. You can create an HSL colour as follows:

    ~~~ scala
    val hue = 180.degrees

    val lightness = 0.5.normalized

    val color = Color.hsl(hue, 1.0.normalized, lightness)
    ~~~

For extra credit, allow the user to specify parameters for
the step size along each axis and the basic shape used in each cell:

![HSL Colour Palette](src/pages/collections/colour-palette-circles.png)

<div class="solution">
First let's define a method to create a single square.
We'll call the method `cell` to keep the naming shape-independent
and specify `size`, `hue`, and `lightness` as parameters:

~~~ scala
def cell(size: Int, hue: Int, lightness: Double): Image =
  Rectangle(size, size) lineWidth 0 fillColor Color.hsl(hue.degrees, 1.0.normalized, lightness.normalized)
~~~

Next let's create a single column of varying lightness.
We start with a list of lightness values,
map over the list to produce the squares,
and build the column using `allAbove` or `allBelow`:

~~~ scala
def column(cellSize: Int, hue: Int): Image = {
  val cells =
    (0.0 until 1.0 by 0.01).toList map { lightness =>
      cell(cellSize, hue, lightness)
    }

  allAbove(cells)
}
~~~

Finally let's assemble the columns into a palette.
We start with a list of hues, map over it to create columns,
and build the palette using `allBeside`:

~~~ scala
def palette(cellSize: Int): Image = {
  val columns =
    (0 until 360 by 2).toList map { hue =>
      column(cellSize, hue)
    }

  allBeside(columns)
}
~~~

For the extra credit solution
we add `hStep`, `lStep`, and `cell` parameters.
`hStep` and `lStep` are of type `Int` and `Double` respectively,
and `cell` is of type `(Int, Double) => Image`.
In the example below we use a *type alias*
to make the type of the `cell` parameter more explicit.
Type aliases are simply a way of naming types---the compiler
treats an aliased type exactly the same as an unaliased one:

~~~ scala
// Type alias for cell constructor functions:

type CellFunc = (Int, Double) => Image

// Different types of cell:

def squareCell(size: Int): CellFunc =
  (hue: Int, lightness: Double) =>
    Rectangle(size, size) lineWidth 0 fillColor Color.hsl(hue.degrees, 1.0.normalized, lightness.normalized)

def circleCell(size: Int): CellFunc =
  (hue: Int, lightness: Double) =>
    Circle(size/2) lineWidth 0 fillColor Color.hsl(hue.degrees, 1.0.normalized, lightness.normalized)

// Code to construct a palette:

def column(hue: Int, lStep: Double, cell: CellFunc): Image = {
  val cells =
    (0.0 until 1.0 by lStep).toList map { lightness =>
      cell(hue, lightness)
    }

  allAbove(cells)
}

def palette(hStep: Int, lStep: Double, cell: CellFunc): Image = {
  val columns =
    (0 until 360 by hStep).toList map { hue =>
      column(hue, lStep, cell)
    }

  allBeside(columns)
}

// Example use of the palette() method:

palette(2, 0.01, circleCell(10))
~~~
</div>

There are many other methods in the Scala standard library
for transforming sequences using functions as parameters.
We won't cover them in this course,
but here is a sample of a few really useful ones:

----------------------------------------------------------------------------------
Start with Method    Parameter      Result      Description
---------- --------- -------------- ----------- ----------------------------------
`List[A]`  `map`     `A => B`       `List[B]`   Return all elements,
                                                transformed by the function.

`List[A]`  `filter`  `A => Boolean` `List[A]`   Return all elements for which the
                                                function returns `true`.

`List[A]`  `flatMap` `A => List[B]` `List[B]`   Return all elements,
                                                transformed by the function and
                                                concatenated into a single `List`.

`List[A]`  `find`    `A => Boolean` `Option[B]` Return the first element for which
                                                the function returns `true`.
----------------------------------------------------------------------------------
