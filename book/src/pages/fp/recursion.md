## Recursive Algorithms

Recursion is a natural part of functional programming.
The classic functional data structure---the single linked list---is recursive in nature.
We can similarly create interesting drawings using recursion.

Let's start with a simple example---a set of concentric circles.
We can create this image by recursing over the natural numbers.
Each number corresponds to the next layer of the image:

@:figure{ img = "src/pages/fp/concentric-circles-steps.pdf+svg", caption = "Concentric circles (n = 1 to 3)" }

Note the recursive pattern here:

 - the `n = 1` case is the *base case*;
 - every other case is the `n - 1` case (shown in black)
   surrounded by an extra circle (shown in red).

Given these two rules, we can generate a picture for any value of `n >= 1`.
We can even model the rules directly in Scala:

``` scala
def concentricCircles(n: Int): Image =
  if(n == 1) {
    // Return a circle
  } else {
    // Return a circle superimposed on the image from n - 1
  }
```

**Exercise: Concentric circles**

Create an image containing 20 concentric circles
using the approach described above:

@:figure{ img = "src/pages/fp/concentric-circles.png", caption = "Concentric circles (n = 20)" }

For extra credit, give each circle its own hue or opacity
by gradually changing the colour at each level of recursion:

@:figure{ img = "src/pages/fp/concentric-circles2.png", caption = "Concentric circles IN COLOUR! (n = 20)" }

<div class="solution">
The basic structure of our solution involves two methods:
one for drawing a single circle and one for drawing `n` circles:

``` scala
def singleCircle(n: Int): Image =
  ???

def concentricCircles(n: Int): Image =
  if(n == 1) {
    singleCircle(n)
  } else {
    singleCircle(n) on concentricCircles(n - 1)
  }

concentricCircles(20)
```

There is a clean division of labour here:
`concentricCircles` handles the recursion through values of `n`
and the composition of the shapes at each level,
while `singleCircle` decides which actual shapes we draw at each level.

Here is the implementation of `singleCircle` we need to draw monochrome circles.
We calculate an appropriate radius from the value of `n` provided.
The `n = 1` circle has radius `55` and
each successive circle is `5` pixels larger:

``` scala
def singleCircle(n: Int): Image =
  Circle(50 + 5 * n)
```

To create multicolour circles, all we need to do is modify `singleCircle`.
Here is an implementation for the extra credit example above:

``` scala
def singleCircle(n: Int): Image =
  Circle(50 + 5 * n) strokeColor (Color.red spin (n * 10).degrees)
```

Here is another implementation that fades out the further we get from `n = 1`:

``` scala
def singleCircle(n: Int): Image =
  Circle(50 + 5 * n) fadeOut (n / 20).normalized
```

We can make countless different images by tweaking `singleCircle`
without changing the definition of `concentricCircles`.
In fact, `concentricCircles` doesn't care about circles at all!
A more general naming system would be more suitable:

```
def singleShape(n: Int): Image =
  ???

def manyShapes(n: Int): Image =
  if(n == 1) singleShape(n) else (singleShape(n) on manyShapes(n - 1))
```
</div>

**Exercise: Sierpinski triangle**

Sierpinski triangles are a more interesting example of a recursive drawing
algorithm. The pattern is illustrated below:


@:figure{ img = "src/pages/fp/sierpinski-steps.pdf+svg", caption = "Sierpinski triangles (n = 1 to 4)" }

Here is an English description of the recursive pattern:

 -  The base case for `n = 1` is an equilateral triangle.
    We can draw this in Doodle as follows:

    ``` scala
    Triangle(10, 10)
    ```

 -  Every other case involves three copies of the `n - 1` case
    arranged in a triangle.

Use this description to write Scala code to draw a Sierpinski triangle.
Start by dealing with the `n = 1` case, then solve the `n = 2` case,
then generalise your code for any value of `n`. Finish by drawing the
`n = 10` *Sier**pink**ski* triangle below:

@:figure{ img = "src/pages/fp/sierpinski.png", caption = "Sierpinski triangle (n = 10)" }

You may notice that the final result is extremely large!
For extra credit, rewrite your code so you can specify
the size of the triangle up front:

``` scala
def sierpinski(n: Int, size: Double): Image = ???
```

Finally, for double extra credit, answer the following questions:

 1. How many pink triangles are there in your drawing?

 2. How many `Triangle` objects is your code creating?

 3. Is this the answer to question 2 necessarily the same
    as the answer to question 1?

 4. If not, what is the minimum number of `Triangles`
    needed to draw the `n = 10` Sierpinski?

<div class="solution">
The simple solution looks like the following:

``` scala
def triangle: Image =
  Triangle(1, 1) strokeColor Color.magenta

def sierpinski(n: Int): Image =
  if(n == 1) {
    triangle
  } else {
    val smaller = sierpinski(n - 1)
    smaller above (smaller beside smaller)
  }

sierpinski(10)
```

As we hinted above, each successive triangle in the Sierpinski pattern
is twice the size of its predecessor.
Even if we start with an `n = 1` triangle of side 1,
we end up with an `n = 10` triangle of side 1024!

The extra credit solution involves specifying the desired size up front
and dividing it by two each time we recurse:

``` scala
def triangle(size: Double): Image =
  Triangle(size, size) strokeColor Color.magenta

def sierpinski(n: Int, size: Double): Image =
  if(n == 1) {
    triangle(size)
  } else {
    val smaller = sierpinski(n - 1, size / 2)
    smaller above (smaller beside smaller)
  }

sierpinski(10, 512)
```

Finally let's look at the questions:

First, let's consider the number of pink triangles.
There is one triangle in the `n = 1` base Sierpinski,
and we multiply this by 3 for each successive level of recursion.
There are `3^9 = 19,683` triangles in the `n = 10` Sierpinski!

That's a lot of triangles!
Now let's consider the number of `Triangle` objects.
This question is designed to highlight a nice property
of immutable data structures called *structural sharing*.
Each Sierpinski from `n = 2` upwards is created from three smaller
Sierpinskis, but they *don't have to be different objects in memory*.
We can re-use a single smaller Sierpinski three times
to save on computation time and memory use.

The code above actually shows the optimal case.
We use a temporary variable, `smaller`, to ensure
we only call `sierpinski(n - 1)` once at each level of recursion.
This means we only call `triangle()` once,
no matter what value of `n` we start with.

We only need to create one `Triangle` object for the whole picture!
Of course, the `draw` method has to process this single triangle 19,683 times
to draw the picture, but the representation we build to begin with
is extremely efficient.
</div>
