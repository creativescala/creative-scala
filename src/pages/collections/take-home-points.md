## Take Home Points

In this chapter we looked at our first Scala collections: `Lists` and `Ranges`.
These are two types of *sequence* with different use cases:

 - `Lists` are convenient for storing finite collections of
   arbitrary values of a similar type;
 - `Ranges` provide a more compact syntax for regular sequences of numbers.

We didn't cover this in the chapter but
`List` and `Range` have a common supertype, `Seq`.
This explains a few things:

 - `List` and `Range` both have a `map` method,
   the behaviour of which is the same in each case.

 - Doodle's n-ary combinators---`allAbove`, `allBeside`, and so on---work
   equally well with sequences produced by transforming `Ranges`, as they do
   sequences produced by transforming `Lists`.

Scala has a large collections library containing many different useful types:
sequences, maps, and sets with mutable, immutable, lazy, and parallel variations.
These types form a single inheritance hierarchy
that provides consistent implementations of methods such as `map`, `flatMap`,
`filter`, `find`, and so on.

We looked at one kind of transform operation in this chapter: the `map` method.
`map` applies a user-specified function to every item in a sequence,
returning a new sequence of the results:

~~~ scala
List(1, 2, 3).map(x => x * 2)
// res0: List[Int] = List(2, 4, 6)
~~~

The key point about `map` is that it only makes sense in a world
where we have functions that are also first class values.
The same can be said for many of the other cohort of combinators
such as `filter`, `find`, and `flatMap`.
First class functions allow us to pass operations to these methods as parameters,
separating user code from the implementational detail of allocating
temporary buffers.

Because many transformation methods return sequences,
we can chain calls to perform complex transformations in a series of simple steps.
Here is an example that demonstrates the power of thie approach:

~~~ scala
// Print all even numbers from 1 to 100 that are also divisible by 3:
(1 to 50).toList.
  map(x => x * 2).
  filter(x => x % 3 == 0).
  foreach(println)
// 6
// 12
// 18
// etc...
~~~

The structure of this computation looks similar
to the structure of our Doodle programs:
we build an intermediate representation
by creating, combining, and transforming primitives,
and perform side-effects at the end of the program.
Obviously we've put less thought into this program than into Doodle,
but it's interesting to note that even ad hoc functional programs
can follow the same basic structure.
