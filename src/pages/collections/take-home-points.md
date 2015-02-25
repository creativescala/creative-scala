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
scala> List(1, 2, 3).map(x => x * 2)
res0: List[Int] = List(2, 4, 6)
~~~

The key point about the `map` method is that it only makes sense
in a world where we have functions that are also first class values.
It is the concept of a first class function that allows us to pass
a "doubling operation" as a parameter to `map`.
Without this we have no way of disentangling
the doubling operation from the library code that
creates a new sequence to accumulate values.
