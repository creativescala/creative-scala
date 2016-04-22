## Transforming Sequences

```tut:invisible
import doodle.core._
import doodle.core.Image._
import doodle.syntax._
import doodle.jvm.Java2DCanvas._
import doodle.backend.StandardInterpreter._
```

We've seen that using structural recursion we can create and transform lists. This pattern is simple to use and to understand, but it requires we write the same skeleton time and again. In this section we'll learn some ways to remove this boilerplate by using two methods on `List`: `map` and `foldRight`. We'll also see that other useful datatypes provide these methods and we can use them as a common interface for manipulating sequences.


### Transforming the Elements of a List

In the previous section we say several examples where we transformed one list to another. For example, we incremented the elements of a list with the following code.

```tut:book
def increment(list: List[Int]): List[Int] =
  list match {
    case Nil => Nil
    case hd :: tl => (hd + 1) :: tl
  }
  
increment(List(1, 2, 3))
```

In this example the *structure* of the list doesn't change. If we start with three elements we end with three elements.

We can abstract this pattern in a method called `map`. If we have a list with elements of type `A`, we pass `map` a function of type `A => B` and we get back a list with elements of type `B`. For example, we can implement `increment` using `map` with the function `x => x + 1`.

```tut:book
def increment(list: List[Int]): List[Int] =
  list.map(x => x + 1)
  
increment(List(1, 2, 3))
```

Each element is transformed by the function we pass to `map`, in this case `x => x + 1`, but we cannot change the number of elements in the list.


### Transforming Sequences of Integers

We have also written a lot of methods that transform a natural number to a list. We briefly discussed how we can represent a natural number as a list. `3` is equivalent to `1 + 1 + 1 + 0`, which in turn we could represent as `List(1, 1, 1)`. So what? Well, it means we could write a lot of the methods that accepts natural numbers as methods that worked on lists.

For example, instead of

```tut:book
def fill[A](n: Int, a: A): List[A] =
  n match {
    case 0 => Nil
    case n => a :: fill(n-1, a)
  }
  
fill(3, "Hi")
```

we could write

```tut:book
def fill[A](n: List[Int], a: A): List[A] =
  n.map(x => a)
  
fill(List(1, 1, 1), "Hi")
```

This isn't very convenient though---constructing the list `List(1, 1, 1)` is much more painful than simply writing `3`.
