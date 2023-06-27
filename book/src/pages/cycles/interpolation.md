# Interpolation

```scala mdoc:invisible
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._
```

Interpolation is our final topic before we can create the pictures we're after.
Interpolation means guessing at a value inbetween points.
In our case we're going to use interpolation to create smooth curves that connect the points we sample from our parametric curves.

The example below sampling between 3 and 15 points from a parametric circle, and interpolating a curve from these points.

@:doodle("circle-interpolation", "CyclesInterpolation.circleInterpolation")

We can see that the curve more closely resembles a circle as we increase the number of points it's based off.

We use the `Image.interpolatingSpline` method to interpolate a curve. 
The method expects a `List[Point]`, which is a type in two parts:

1. `List`, which is what's known as a container or collection, meaning it holds other values; and
2. `Point`, which is the type of the values inside the list.

Notice that all the elements of a list must have the same type.

Right now we only need to know how to create lists.
In later chapters we'll learn a lot more about how to work with lists and other collections.

The simplest way to construct a list is by calling the `List` constructor with the values we want in the list.
The example below creates a `List[Int]`, with the elements `1`, `2`, and `3`.

```scala mdoc
List(1, 2, 3)
```

The order of the elements in the list is important, so the following two lists are different.

```scala mdoc:silent
val oneToThree = List(1, 2, 3)
val threeToOne = List(3, 2, 1)
```
```scala mdoc
oneToThree == threeToOne
```

This way of constructing lists requires we know all the elements in advance.
More often we'll want to compute the elements as part of our program.
This is exactly what we'll be doing here,
as the elements of the list will be the points from our parametric curve.

There is a pattern for creating lists an element at a time.
We either:

- create an empty list, using the expression `List.empty`; or
- add an element to the front of an existing list, using an expression like `anElement :: aList`.

Here's a quick example, creating the same list as `List(1, 2, 3)`.

```scala mdoc
1 :: 2 :: 3 :: List.empty
```

Let's talk about the expression `anElement :: aList` a little bit.
We know that in Scala we always interact with objects using method calls,
and we can write method calls in two styles:

1. operator style: `1 + 2`; or
2. normal method call style: `1.+(2)`.

There is another rule to operator style.
A method that ends in a colon (`:`) is *right-associative*.
This means that, in operator style, instead of writing `object method parameter`
we write `parameter method object`.
So

```scala mdoc:silent
1 :: 2 :: 3 :: List.empty
```

is the same as 

```scala mdoc:silent
(1 :: (2 :: (3 :: List.empty)))
```

which is the same as

```scala mdoc:silent
List.empty.::(3).::(2).::(1)
```

Let's now see a bigger example.
We will write a method that creates a list of natural numbers.
This list will have as many elements as the natural number parameter passed to the method.
The elements will decrease from left to right, ending at zero.

In other words, the method skeleton is

```scala
def range(count: Int): List[Int] =
  ???
```

and we want results like

```scala
range(0) == List.empty
range(3) == List(2, 1, 0)
range(5) == List(4, 3, 2, 1, 0)
```

Our first step is to recognize we have a structural recursion over the natural numbers.

```scala
def range(count: Int): List[Int] =
  count match {
    case 0 => ???
    case n => ??? range(n - 1)
  }
```

We can use our normal strategies for reasoning about structural recursion,
alongside the pattern for creating lists.
Let's walk through it.

The first step is to consider each case independently.
We'll start with the base case.
The pattern for creating lists tells us we must either use

- `List.empty`; or
- `anElement :: aList`

In this case the answer is `List.empty`, 
which we can tell from the method description.

Moving to the next case, 
we have the recursion which produces a `List[Int]`.
This suggests we should use the `anElement :: aList` pattern.
We might guess that we should write

```scala
case n => n :: range(n - 1)
```

but this will give us a list that ends at 1, not 0.
The solution is to instead use

```scala
case n => (n - 1) :: range(n - 1)
```

This gives us the full solution

```scala mdoc:silent
def range(count: Int): List[Int] =
  count match {
    case 0 => List.empty
    case n => (n - 1) :: range(n - 1)
  }
```

We can check we have the correct solution by comparing the output to the examples above.

```scala mdoc
// Expect List.empty, also written List()
range(0) 

// Expect List(2, 1, 0)
range(3)

// Expect List(4, 3, 2, 1, 0)
range(5) 
```

@:exercise(Ranging About)
In the example above the list elements decrease from left to right,
which is not how we usually order numbers.
Write an alternative method where the values *increase* from left to right.
@:@

@:solution
We can use the same general structure as `range`, 
but we need an auxillary parameter to hold the current value to put into the list.
We then use a nested method so that the user doesn't have to deal with this additional parameter.

```scala mdoc:silent
def rangeReversed(count: Int): List[Int] = {
  def loop(count: Int, value: Int): List[Int] =
    count match {
      case 0 => List.empty
      case n => value :: loop(n - 1, value + 1)
    }
    
  loop(count, 0)
}
```
```scala mdoc
rangeReversed(0)

rangeReversed(3)

rangeReversed(5)
```
@:@


## Interpolating Parametric Curves

Now we can put all the pieces together:

1. sample a `List[Point]` from a parametric curve; and
2. interpolate that `List[Point]` to produce a smooth curve.

The first part requires a method, which we'll call `sampleCurve`, that is very similar to `drawCurve` that we've used earlier.
The second part is a call to `Image.interpolatingSpline`.

@:exercise(Getting to the Point)
Implement `sampleCurve` with the following skeleton

```scala 
def sampleCurve(points: Int, curve: Angle => Point): List[Point] =
  ???
```
@:@

@:solution
This is a modification of `drawCurve` that produces a `List[Point]` instead of an `Image`.

```scala mdoc:silent
def sampleCurve(points: Int, curve: Angle => Point): List[Point] = {
  val turn = Angle.one / points
  def loop(count: Int): List[Point] =
    count match {
      case 0 => List.empty
      case n => 
        val pt = curve(turn * n)
        pt :: loop(n - 1)
    }
    
  loop(points)
}
```
@:@
