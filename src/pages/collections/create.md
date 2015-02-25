## Creating Sequences

The standard library in Scala contains many types of sequence:
mutable and immutable, lazy and eager, parallel and sequential.
In this course we will use two types of sequence: `Lists` and `Ranges`.
Both are simple, immutable, eager data types. Let's see them in action.

We can create a list in Scala by calling the `List`
factory method as follows:

~~~ scala
scala> List(1, 2, 3, 4, 5)
res0: List[Int] = List(1, 2, 3, 4, 5)
~~~

The result of the expression is of type `List[Int]`,
which we read as "list of integers".
If we call the factory method with `String` arguments,
the type of the result changes accordingly:

~~~ scala
scala> List("a", "b", "c", "d", "e")
res1: List[String] = List(a, b, c, d, e)
~~~

`Lists` are useful for storing short sequences of values.
If we want to create long sequences of numbers, however,
we are better off using `Ranges`.
We can create these using the `until` method of `Int` or `Double`:

~~~ scala
scala> 0 until 10
res0: scala.collection.immutable.Range.Inclusive =
  Range(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)

scala> 0.0 until 5.0
res1: scala.collection.immutable.Range.Inclusive =
  Range(0.0, 1.0, 2.0, 3.0, 4.0)
~~~

`Ranges` have a `by` method that allows us to change the step
between consecutive elements of the range:

~~~ scala
scala> 0 until 10 by 2
res1: scala.collection.immutable.Range.Inclusive =
  Range(0, 2, 4, 6, 8)

scala> 0.0 until 1.0 by 0.3
res2: scala.collection.immutable.Range.Inclusive =
  Range(0.0, 0.3, 0.6, 0.9)
~~~

Many methods in Doodle are designed to work with `Lists` and `Ranges`,
but you can use the `toList` of any `Range` to convert it to a `List`
if you run into problems:

~~~ scala
scala> (0 until 10).toList
res0: List[Int] =
  List(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
~~~

Let's see what we can do with these sequences.
