## Simple Scala Expressions

Let's look at some of the basic kinds of expressions we can write in Scala:

### Literals

The simplest kinds of expressions are *literals*.
These are fragments of code that "stand for themselves".
Scala supports a similar set of literals to Java:

~~~ scala
// Integers:
scala> 1
res0: Int = 1

// Floating point numbers:
scala> 0.1
res1: Double = 0.1

// Booleans:
scala> true
res2: Boolean = true

// Strings:
scala> "Hello world!"
res3: String = Hello world!

// And so on...
~~~

### Method Calls

Scala is a completely object-oriented programming language,
so all values are objects with methods that we can call.
Method calls are another type of expression:

~~~ scala
scala> 123.4.ceil
res4: Double = 124.0

scala> true.toString
res5: String = "true"
~~~

### Constructor Calls

Scala only has literals for a small set of types
(`Int`, `Boolean`, `String`, and so on).
To create values of other types we either need to call a method,
or we need to call a *constructor* using the `new` keyword.
This behaves similarly to `new` in Java:

~~~ scala
scala> import java.util.Date
import java.util.Date

scala> new Date()
res4: java.util.Date = Tue Feb 10 10:30:21 GMT 2015
~~~

The `new` operator tends to be a distraction when writing
larger, more complex expressions. For this reason,
Scala libraries typically provide *factory methods* to wrap constructor calls.
The effective difference is that we can create many
Scala data types without writing `new`:

~~~ scala
scala> List(1, 2, 3)
res6: List[Int] = List(1, 2, 3)
~~~

Other than the lack of a `new` keyword,
the semantics here are similar to the `Date` example above:

 - `List(1, 2, 3)` is an expression that returns a value;
 - `List[Int]` is its type.

### Operators

Operators in Scala are actually method calls under the hood.
Scala has a set of convenient syntactic shorthands
to allow us to write normal-looking code
without excessive numbers of parentheses.
The most common of these is *infix syntax*,
which allows us to write any expression of the form `a.b(c)`
as `a b c`, without the full stop or parentheses:

~~~ scala
scala> 1 .+(2).+(3).+(4) // the space prevents `1` being treated as a double
res0: Int = 10

scala> 1 + 2 + 3 + 4
res1: Int = 10
~~~

### Conditionals

Many other syntactic constructs are expressions in Scala,
including some that are statements in Java.
Conditionals ("if expressions") are a great example:

~~~ scala
// Conditionals ("if expressions"):
scala> if(123 > 456) "Higher!" else "Lower!"
res6: String = Lower!
~~~

### Blocks and Side-Effects

Blocks are another type of expression in Scala.
Running a block runs each contained expression in order.
The type and return value of the block are determined
by the *last* contained expression:

~~~ scala
scala> {
     |   println("First line")
     |   println("Second line")
     |   1 + 2 + 3
     | }
res0: Int = 6
~~~

In functional programming we make the distiction between
"pure expressions" and expressions that have "side effects":

 - *pure expressions* do nothing more than calculate a value;

 - expressions with *side effects* do something else aside from
   calculate their result---for example,
   `println` prints a message to the console.

Because the results of intermediate expressions in a block are thrown away,
it doesn't make sense to use pure expressions there.
The Scala console even warns us when we try this:

~~~ scala
scala> {
     |   1 + 2 + 3
     |   4 + 5 + 6
     | }
<console>:9: warning: a pure expression does nothing in statement position;
             you may be omitting necessary parentheses
               1 + 2 + 3
                     ^
res0: Int = 15
~~~

The message here is warning us that
the intermediate expression `1 + 2 + 3` is wasted computation.
All it does is calculate the value `6`.
We immediately throw the result away and calculate `4 + 5 + 6` instead.
We might as well simply write `4 + 5 + 6` and get rid of the block.

Side-effecting expressions, by contrast, make perfect sense within a block.
`println` expressions are a great example of this---they
do something useful even though they don't return a useful value:

~~~ scala
scala> {
     |   println("Intermediate result: " + (1 + 2 + 3))
     |   4 + 5 + 6
     | }
Intermediate result: 6
res0: Int = 15
~~~

Scala developers tend to prefer pure expressions to side-effects
because they are easier to reason about.
See the section on [Substitution](#sec-substitution) for more information.
We won't use blocks in anger until the next chapter when
we start declaring intermediate values and re-use them in later expressions.
