## Exercises

#### Flat Polygon

Using a `Range` and `flatMap`, rewrite your method to create a polygon. The signature of `polygon` is

```tut:book
def polygon(sides: Int, sideLength: Double): Image = 
  ???
```


<div class="solution">
Using `flatMap` we can make the code more compact the explicit structural recursion we had to use before.

```tut:book
def polygon(sides: Int, sideLength: Double): Image = {
  val rotation = Angle.one / sides
  
  Turtle.draw((1 to n).toList.flatMap { n =>
    List(turn(rotation), forward(sideLength))
  })
}
```
</div>


#### Flat Spiral

Using a `Range` and `flatMap`, rewrite your method to create the square spiral. The signature of `squareSpiral` is

```tut:book
def squareSpiral(steps: Int, distance: Double, angle: Angle, increment: Double): Image =
  ???
```

<div class="solution">
Again, the result is more compact than the previous implementation without `flatMap`. Isthis easier to read? I find it about the same. I belive comprehensibility is a function of familiarity, and we're (hopefully) by now becoming familiar with `flatMap`.

```tut:book
def squareSpiral(steps: Int, distance: Double, angle: Angle, increment: Double): Image = {
  Turtle.draw((1 to n).toList.flatMap { n =>
   List(forward(distance + (n * increment)), turn(angle)) 
  })
}
```
</div>
