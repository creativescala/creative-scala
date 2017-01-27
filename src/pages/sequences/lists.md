## Working with Lists

```tut:invisible
import doodle.core._
import doodle.core.Image._
import doodle.syntax._
import doodle.jvm.Java2DCanvas._
import doodle.backend.StandardInterpreter._
```

At this point you might be thinking it would be nice to create a method to draw polygons rather than constructing each one by hand. There is clearly a repeating pattern to their construction and we should be able to abstact this pattern but we don't know how to create a list of arbitrary size. It's time we learned more about building and manipulating lists.


### The Recursive Structure of Lists

You'll recall when we introduced structural recursion over the natural numbers we said we could transform their recursive structure into any other recursive structure. We demonstrated this for concentric circles and a variety of other patterns.

Lists have a recursive structure, and one that is very similar to the structure of the natural numbers. A `List` is

- the empty list `Nil`; or
- a pair of an element `a` and a `List`, written `a :: tail`, where `tail` is the rest of the list.

For example, we can write out the list `List(1, 2, 3, 4)` in its "long" form as

```tut:book
1 :: 2 :: 3 :: 4 :: Nil
```

Notice the similarity to the natural numbers. Earlier we noted we can expand the structure of a natural number so we could write, say, `3` as `1 + 1 + 1 + 0`. If we replace `+` with `::` and `0` with `Nil` we get the `List` `1 :: 1 :: 1 :: Nil`.

What does this mean? It means we can easily transform a natural number into a `List` using our famililar tool of structural recursion[^free-monoid]. Here's a very simple example, which given a number builds a list of that length containing the `String` "Hi".

```tut:book
def sayHi(length: Int): List[String] =
  length match {
    case 0 => Nil
    case n => "Hi" :: sayHi(n - 1)
  }

sayHi(5)
```

[^free-monoid]: This connection goes deeper, to something called the free monoid. This isn't relelvant to Creative Scala but you're encouraged to explore on your own!

This recursive structure also means we can transform lists into other recursive structures, such a natural numbers, different lists, chessboards, and so on. Here we increment every element a list---that is, transform a list to a list---using structural recursion.

```tut:book
def increment(list: List[Int]): List[Int] =
  list match {
    case Nil => Nil
    case hd :: tl => (hd + 1) :: tl
  }
  
increment(List(1, 2, 3))
```

Here we sum the elements of a list of integers---that is, transform a list to a natural number---using structural recursion.

```tut:book
def sum(list: List[Int]): Int =
  list match {
    case Nil => 0
    case hd :: tl => hd + sum(tl)
  }
  
sum(List(1, 2, 3)) 
```


### Type Variables

What about finding the length of a list? We know we can use our standard tool of structural recursion to write the method. Here's the code to calculate the length of a `List[Int]`.

```tut:book
def length(list: List[Int]): Int =
  list match {
    case Nil => 0
    case hd :: tl => 1 + length(tl)
  }
```

Note that we don't do anything with the elements of the list---we don't really care about their type. Using the same code skeleton can just as easily calculate the length of a `List[Int]` as a `List[HairyYak]` but we don't currently know how to write down the type of a list where we don't care about the type of the elements.

Scala lets us write methods that can work with any type, by using what is called a *type variable*. A type variable is written in square brackets like `[A]` and comes after the method name and before the parameter list. A type variable can stand in for any specific type, and we can use it in the parameter list or result type to indicate some type that we don't know up front. For example, here's how we can write `length` so it works with lists of any type.

```tut:book
def length[A](list: List[A]): Int =
  list match {
    case Nil => 0
    case hd :: tl => 1 + length(tl)
  }
```


<div class="callout callout-info">
#### Structural Recursion over a List {-}

A `List` of elements of type `A` is:

- the empty list `Nil`; or
- an element `a` of type `A` and a `tail` of type `List[A]`: `a :: tail`

The structural recursion skeleton for transforming `list` of type `List[A]` has shape

```
def doSomething[A,B](list: List[A]): B =
  list match {
    case Nil => ??? // Base case of type B here
    case hd :: tl => f(hd, doSomething(tl))
  }  
```

where `f` is a problem specific method combining `hd` and result of the recursive call to produce something of type `B`.

</div>


#### Exercises {-}

##### Building Lists {-}

In these exercises we get some experience constructing lists using structural recursion on the natural numbers.

Write a method called `ones` that accepts an `Int` `n` and returns a `List[Int]` with length `n` and every element `1`. For example

```tut:invisible
def ones(n: Int): List[Int] =
  n match {
    case 0 => Nil
    case n => 1 :: ones(n - 1)
  }
```

```tut:book
ones(3)
```

<div class="solution">
It's structural recursion over the natural numbers!

```tut:book
def ones(n: Int): List[Int] =
  n match {
    case 0 => Nil
    case n => 1 :: ones(n - 1)
  }
  
ones(3)
```
</div>


Write a method `descending` that accepts an natural number `n` and returns a `List[Int]` containing the natural numbers from `n` to `1` or the empty list if `n` is zero. For example

```tut:invisible
def descending(n: Int): List[Int] =
  n match {
    case 0 => Nil
    case n => n :: descending(n - 1)
  }
```

```tut:book
descending(0)
descending(3)
```

<div class="solution">
Once more, we can employ structural recursion over the natural numbers.

```tut:book
def descending(n: Int): List[Int] =
  n match {
    case 0 => Nil
    case n => n :: descending(n - 1)
  }

descending(0)
descending(3)
```

</div>


Write a method `ascending` that accepts a natural number `n` and returns a `List[Int]` containing the natural numbers from `1` to `n` or the empty list if `n` is zero.

```tut:invisible
def ascending(n: Int): List[Int] = {
  def iter(n: Int, counter: Int): List[Int] =
    n match {
      case 0 => Nil
      case n => counter :: iter(n - 1, counter + 1) 
    }

  iter(n, 1)
}
```

```tut:book
ascending(0)
ascending(3)
```

<div class="solution">
It's structural recursion over the natural numbers, but this time with an internal accumulator.

```tut:book
def ascending(n: Int): List[Int] = {
  def iter(n: Int, counter: Int): List[Int] =
    n match {
      case 0 => Nil
      case n => counter :: iter(n - 1, counter + 1) 
    }

  iter(n, 1)
}
  
ascending(0)
ascending(3)
```
</div>

Create a method `fill` that accepts a natural number `n` and an element `a` of type `A` and constructs a list of length `n` where all elements are `a`.

```tut:invisible
def fill[A](n: Int, a: A): List[A] =
  n match {
    case 0 => Nil
    case n => a :: fill(n-1, a)
  }
```

```tut:book
fill(3, "Hi")
fill(3, Color.blue)
```

<div class="solution">
In this exercise we're asking you to use a type variable. Otherwise it is the same pattern as before.

```tut:book
def fill[A](n: Int, a: A): List[A] =
  n match {
    case 0 => Nil
    case n => a :: fill(n-1, a)
  }

fill(3, "Hi")
fill(3, Color.blue)
```
</div>

##### Transforming Lists {-}

In these exercises we practice the other side of list manipulation---transforming lists into elements of other types (and sometimes, into different lists).

Write a method `double` that accepts a `List[Int]` and returns a list with each element doubled.

```tut:invisible
def double(list: List[Int]): List[Int] =
  list match {
    case Nil => Nil
    case hd :: tl => (hd * 2) :: double(tl)
  }
```

```tut:book
double(List(1, 2, 3))
double(List(4, 9, 16))
```

<div class="solution">
This is a structural recursion over a list, building a list at each step. The destructuring of the input is mirrored by the construction of the output.

```tut:book
def double(list: List[Int]): List[Int] =
  list match {
    case Nil => Nil
    case hd :: tl => (hd * 2) :: double(tl)
  }

double(List(1, 2, 3))
double(List(4, 9, 16))
```
</div>


Write a method `product` that accepts a `List[Int]` and calculates the product of all the elements.

```tut:invisible
def product(list: List[Int]): Int =
  list match {
    case Nil => 1
    case hd :: tl => hd * product(tl)
  }
```

```tut:book
product(Nil)
product(List(1,2,3))
```

<div class="solution">
This is a structural recursion over a list using the same pattern as `sum` in the examples above.

```tut:book
def product(list: List[Int]): Int =
  list match {
    case Nil => 1
    case hd :: tl => hd * product(tl)
  }

product(Nil)
product(List(1,2,3))

```
</div>

Write a method `contains` that accepts a `List[A]` and an element of type `A` and returns true if the list contains the element and false otherwise.

```tut:invisible
def contains[A](list: List[A], elt: A): Boolean =
  list match {
    case Nil => false
    case hd :: tl => (hd == elt) || contains(tl, elt)
  }
```

```tut:book
contains(List(1,2,3), 3)
contains(List("one", "two", "three"), "four")
```

<div class="solution">
Same pattern as before, but using a type variable to allow type of the elements to vary.

```tut:book
def contains[A](list: List[A], elt: A): Boolean =
  list match {
    case Nil => false
    case hd :: tl => (hd == elt) || contains(tl, elt)
  }

contains(List(1,2,3), 3)
contains(List("one", "two", "three"), "four")
```
</div>

Write a method `first` that accepts a `List[A]` and an element of type `A` and returns the first element of the list if it is not empty and otherwise returns the element of type `A` passed as a aprameter.

```tut:invisible
def first[A](list: List[A], elt: A): A =
  list match {
    case Nil => elt
    case hd :: tl => hd
  }
```

```tut:book
first(Nil, 4)
first(List(1,2,3), 4)
```

<div class="solution">
This method is similar to `contains` above, except we now use the type variable in the return type as well as in the parameter types.

```tut:book
def first[A](list: List[A], elt: A): A =
  list match {
    case Nil => elt
    case hd :: tl => hd
  }
  
first(Nil, 4)
first(List(1,2,3), 4)
```
</div>


##### Challenge Exercise: Reverse {-}

Write a method `reverse` that accepts a `List[A]` and reverses the list.

```tut:invisible
def reverse[A](list: List[A]): List[A] = {
  def iter(list: List[A], reversed: List[A]): List[A] =
    list match {
      case Nil => reversed
      case hd :: tl => iter(tl, hd :: reversed)
    }
    
  iter(list, Nil)
}
```

```tut:book
reverse(List(1, 2, 3))
reverse(List("a", "b", "c"))
```

<div class="solution">
The trick here is to use an accumulator to hold the partially reversed list. If you managed to work this one out, congratulations---you really understand structural recursion well!

```tut:book
def reverse[A](list: List[A]): List[A] = {
  def iter(list: List[A], reversed: List[A]): List[A] =
    list match {
      case Nil => reversed
      case hd :: tl => iter(tl, hd :: reversed)
    }
    
  iter(list, Nil)
}

reverse(List(1, 2, 3))
reverse(List("a", "b", "c"))
```
</div>


##### Polygons! {-}

At last, let's return to our example of drawing polygons. Write a method `polygon` that accepts the number of sides of the polygon and the starting rotation and produces a `Image` representing the specified regular polygon. *Hint:* use an internal accumulator.

Use this utility to create an interesting picture combining polygons. Our rather unimaginative example is in [@fig:sequences:concentric-polygons]. We're sure you can do better.

![Concentric polygons with pastel gradient fill.](./src/pages/sequences/concentric-polygons.png){#fig:sequences:concentric-polygons}

<div class="solution">
Here's our code. Note how we factored the code into small components---though we could have taken the factoring further is we wanted to. (Can you see how? Hint: do we need to pass, say, `start` to every call of `makeColor` when it's not changing?)

```tut:book
import Point._

def polygon(sides: Int, size: Int, initialRotation: Angle): Image = {
  def iter(n: Int, rotation: Angle): List[PathElement] =
    n match {
      case 0 =>
        Nil
      case n =>
        LineTo(polar(size, rotation * n + initialRotation)) :: iter(n - 1, rotation)
    }
  closedPath(MoveTo(polar(size, initialRotation)) :: iter(sides, 360.degrees / sides))
}

def style(img: Image): Image = {
  img.
    lineWidth(3.0).
    lineColor(Color.mediumVioletRed).
    fillColor(Color.paleVioletRed.fadeOut(0.5.normalized))
}

def makeShape(n: Int, increment: Int): Image =
    polygon(n+2, n * increment, 0.degrees)

def makeColor(n: Int, spin: Angle, start: Color): Color =
  start spin (spin * n)

val baseColor = Color.hsl(0.degrees, 0.7.normalized, 0.7.normalized)

def makeImage(n: Int): Image = {
  n match {
    case 0 =>
      Image.empty
    case n =>
      val shape = makeShape(n, 10)
      val color = makeColor(n, 30.degrees, baseColor)
      makeImage(n-1) on (shape fillColor color)
  }
}

val image = makeImage(15)
```
</div>
