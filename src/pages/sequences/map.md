## Transforming Sequences

```scala mdoc:invisible
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._
```

We've seen that using structural recursion we can create and transform lists. 
This pattern is simple to use and to understand, but it requires we write the same skeleton time and again. 
In this section we'll learn that we can replace structural recursion in some cases by using a method on `List` called `map`. 
We'll also see that other useful datatypes provide this method and we can use it as a common interface for manipulating sequences.


### Transforming the Elements of a List

In the previous section we saw several examples where we transformed one list to another. 
For example, we incremented the elements of a list with the following code.

```scala mdoc
def increment(list: List[Int]): List[Int] =
  list match {
    case Nil => Nil
    case hd :: tl => (hd + 1) :: tl
  }
  
increment(List(1, 2, 3))
```

In this example the *structure* of the list doesn't change. 
If we start with three elements we end with three elements. 
We can abstract this pattern in a method called `map`. 
If we have a list with elements of type `A`, we pass `map` a function of type `A => B` and we get back a list with elements of type `B`. 
For example, we can implement `increment` using `map` with the function `x => x + 1`.

```scala mdoc:reset
def increment(list: List[Int]): List[Int] =
  list.map(x => x + 1)
  
increment(List(1, 2, 3))
```

Each element is transformed by the function we pass to `map`, in this case `x => x + 1`. 
With `map` we can transform the elements, but we cannot change the number of elements in the list.

We find a graphical notation helps with understanding `map`. 
If we had some type `Circle` we can draw a `List[Circle]` as a box containing a circle, as shown in @:fref(sequences:circle-box).

@:figure{
  caption = "A `List[Circle]` representing by a circle within a box"
  img = "./src/pages/sequences/circle-box.pdf+svg"
  key = "fig:sequences:circle-box"
}

Now we can draw an equation for `map` as in @:fref(sequences:map). 
If you prefer symbols instead of pictures, the picture is showing that `List[Circle].map(Circle => Triangle) = List[Triangle]`. 
One feature of the graphical representation is it nicely illustrates that `map` cannot create a new "box", which represents the structure of the list---or more concretely the number of elements and their order.

@:figure{ img = "./src/pages/sequences/map.pdf+svg", key = "#fig:sequences:map", caption = "`map` shown graphically" }

The graphical drawing of `map` exactly illustrates what holds at the type level for `map`. 
If we prefer we can write it down symbolically:

```scala
List[A].map(A => B) = List[B]
```

The left hand side of the equation has the type of the list we map and the function we use to do the mapping. 
The right hand is the type of the result. 
We can perform algebra with this representation, substituting in concrete types from our program.


### Transforming Sequences of Numbers

We have also written a lot of methods that transform a natural number to a list. 
We briefly discussed how we can represent a natural number as a list. 
`3` is equivalent to `1 + 1 + 1 + 0`, which in turn we could represent as `List(1, 1, 1)`. 
So what? 
Well, it means we could write a lot of the methods that accepts natural numbers as methods that worked on lists.

For example, instead of

```scala mdoc
def fill[A](n: Int, a: A): List[A] =
  n match {
    case 0 => Nil
    case n => a :: fill(n-1, a)
  }
  
fill(3, "Hi")
```

we could write

```scala mdoc:reset
def fill[A](n: List[Int], a: A): List[A] =
  n.map(x => a)
  
fill(List(1, 1, 1), "Hi")
```

The implementation of this version of `fill` is more convenient to write, but it is much less convenient for the user to call it with `List(1, 1, ,1)` than just writing `3`.


If we want to work with sequences of numbers we are better off using `Ranges`.
We can create these using the `until` method of `Int`.

```scala mdoc
0 until 10
```

`Ranges` have a `by` method that allows us to change the step
between consecutive elements of the range:

```scala mdoc
0 until 10 by 2
```

`Ranges` also have a `map` method just like `List`

```scala mdoc
(0 until 3) map (x => x + 1) 
```

You'll notice the result has type `IndexedSeq` and is implemented as a `Vector`---two types we haven't seen yet. 
We can treat an `IndexedSeq` much like a `List`, but for simplicity we can convert a `Range` or an `IndexedSeq` to a `List` using the `toList` method.

```scala mdoc
(0 until 7).toList
(0 until 3).map(x => x + 1).toList
```

With `Ranges` in our toolbox we can write `fill` as

```scala mdoc:reset
def fill[A](n: Int, a: A): List[A] =
  (0 until n).toList.map(x => a)
  
fill(3, "Hi")
```

### Ranges over Doubles

If we try to create a `Range` over `Double` we get an error.

```scala mdoc:fail
0.0 to 10.0 by 1.0
```

There are two ways around this. We can use an equivalent `Range` over `Int`. In this case we could just write

```scala mdoc:silent
0 to 10 by 1
```

We can use the `.toInt` method to convert a `Double` to an `Int` if needed.

Alternatively we can write a `Range` using `BigDecimal`.

```scala mdoc:silent
import scala.math.BigDecimal
BigDecimal(0.0) to 10.0 by 1.0
```

`BigDecimal` has methods `doubleValue` and `intValue` to get `Double` and `Int` values respectively.

```scala mdoc
BigDecimal(10.0).doubleValue()
BigDecimal(10.0).intValue()
```

#### Exercises {-}

##### Ranges, Lists, and map {-}

Using our new tools, reimplement the following methods.

Write a method called `ones` that accepts an `Int` `n` and returns a `List[Int]` with length `n` and every element is `1`. For example

```scala mdoc:invisible
def ones(n: Int): List[Int] =
  (0 until n).toList.map(x => 1)
```

```scala mdoc
ones(3)
```

<div class="solution">
We can just `map` over a `Range` to achieve this.

```scala mdoc:reset
def ones(n: Int): List[Int] =
  (0 until n).toList.map(x => 1)
  
ones(3)
```
</div>


Write a method `descending` that accepts an natural number `n` and returns a `List[Int]` containing the natural numbers from `n` to `1` or the empty list if `n` is zero. For example

```scala mdoc:invisible
def descending(n: Int): List[Int] =
  (n until 0 by -1).toList
```

```scala mdoc
descending(0)
descending(3)
```

<div class="solution">
We can use a `Range` but we have to set the step size or the range will be empty.

```scala mdoc:reset
def descending(n: Int): List[Int] =
  (n until 0 by -1).toList

descending(0)
descending(3)
```

</div>


Write a method `ascending` that accepts a natural number `n` and returns a `List[Int]` containing the natural numbers from `1` to `n` or the empty list if `n` is zero.

```scala mdoc:invisible
def ascending(n: Int): List[Int] =
  (0 until n).toList.map(x => x + 1)
```

```scala mdoc
ascending(0)
ascending(3)
```

<div class="solution">
Again we can use a `Range` but we need to take care to start at `0` and increment the elements by `1` so we have the correct number of elements.

```scala mdoc:reset
def ascending(n: Int): List[Int] = 
  (0 until n).toList.map(x => x + 1)
  
ascending(0)
ascending(3)
```
</div>


Write a method `double` that accepts a `List[Int]` and returns a list with each element doubled.

```scala mdoc:invisible
def double(list: List[Int]): List[Int] =
  list map (x => x * 2)
```

```scala mdoc
double(List(1, 2, 3))
double(List(4, 9, 16))
```

<div class="solution">
This is a straightforward application of `map`.

```scala mdoc:reset
def double(list: List[Int]): List[Int] =
  list map (x => x * 2)

double(List(1, 2, 3))
double(List(4, 9, 16))
```
</div>


##### Polygons, Again! {-}

Using our new tools, rewrite the `polygon` method from the previous section.


<div class="solution">
Here's one possible implementation. Much easier to read than the previous implementation!

```scala mdoc:reset:invisible
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._
```
```scala mdoc:silent
def polygon(sides: Int, size: Int, initialRotation: Angle): Image = {
  import Point._
  import PathElement._

  val step = (Angle.one / sides).toDegrees.toInt
  val path = 
    (0 to 360 by step).toList.map{ deg => 
      lineTo(polar(size, initialRotation + deg.degrees))
    }
    
  Image.closedPath(moveTo(polar(size, initialRotation)) :: path)
}
```
</div>


##### Challenge Exercise: Beyond Map {-}

Can we use `map` to replace all uses of structural recursion? 
If not, can you characterise the problems that we can't implement with `map` but can implement with general structural recursion over lists?

<div class="solution">
We've seen many examples that we cannot implement with `map`: the methods `product`, `sum`, `find`, and more in the previous section cannot be implemented with `map`.

In abstract, methods implemented with map obey the following equation:

```bash
List[A] map A => B = List[B]
```

If the result is not of type `List[B]` we cannot implement it with `map`. 
For example, methods like `product` and `sum` transform `List[Int]` to `Int` and so cannot be implemented with `map`.

`Map` transforms the elements of a list, but cannot change the number of elements in the result. 
Even if a method fits the equation for `map` above it cannot be implemented with `map` if it requires changing the number of elements in the list.
</div>


### Tools with Ranges

We've seen the `until` method to construct `Ranges`, and the `by` method to change the increment in a `Range`. 
There is one more method that will be useful to know about: `to`. 
This constructs a `Range` like `until` but the `Range` includes the endpoint. 
Compare

```scala mdoc
1 until 5
1 to 5
```

In technical terms, the `Range` constructed with `until` is a *half-open interval*, while the range constructed with `to` is an *open interval*.

#### Exercises {-}

##### Using Open Intervals {-}

Write a method `ascending` that accepts a natural number `n` and returns a `List[Int]` containing the natural numbers from `1` to `n` or the empty list if `n` is zero. 
*Hint:* use `to`

```scala mdoc:invisible
def ascending(n: Int): List[Int] =
  (1 to n).toList
```

```scala mdoc
ascending(0)
ascending(3)
```

<div class="solution">
Now that we now about `to` this is trivial to implement.

```scala mdoc:reset
def ascending(n: Int): List[Int] = 
  (1 to n).toList
  
ascending(0)
ascending(3)
```
</div>
