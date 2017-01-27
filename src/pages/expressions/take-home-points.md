## Take Home Points

### Substitution {#sec-substitution}

In the absence of side-effects,
an expression will always evaluate to the same value.
For example, `3 + 4` will always evaluate to `7`
no matter how many times we compile or run the code.

Given these restrictions, the expressions `3 + 4` and `7`
become interchangeable from a user's point of view.
This is known as the *subs􏰂titution model* of evaluation,
although you may remember it as "simplifying formulae"
from your maths class at school.

As programmers we must develop a mental model of how our code operates.
In the absence of side-effects, the subs􏰂tution model always works.
If we know the types and values of each component of an expression,
we know the type and value of the expression as a whole.

Functional programmers aim to avoid side-effects for this reason:
it makes our programs easy to reason about
without having to look beyond the current block of code.

### Types in Scala

We've seen several types so far,
including primitive Scala types such as `Int`, `Boolean`, and `String`,
the `Date` type from the Java standard library,
`List` from the Scala standard library,
and the `Circle`, `Rectangle`, `Image`, and `Color` types from Doodle.
Let's take a moment to see how all of these fit together:

![Scala's type hierarchy](src/pages/expressions/scala-type-hierarchy.pdf+svg)

All types in Scala fall into a single *inheritance hierarchy*,
with a grand supertype called `Any` at the top.
`Any` has two subtypes, `AnyVal` and `AnyRef`.

 - `AnyVal` is a supertype of the JVM's fixed set of "value types",
   all of which which we know from Java:
   `Int` is `int`, `Boolean` is `boolean`, and so on.
   `AnyVal` is also the supertype of `Unit`, which we will discuss in a moment.

 - `AnyRef` is the supertype of all JVM "reference types".
   It is an alias for Java's `Object` type.
   `AnyRef` is the supertype of all Java and Scala classes.

The `Unit` type is Scala's equivalent of `void` in Java or C---we use it
to write code that evaluates to "no interesting value":

~~~ scala
val uninteresting = println("Hello world!")
// Hello world!
// uninteresting: Unit = ()
~~~

While `void` is simply a syntax,
`Unit` is an actual type with a single value, `()`.
Having an concrete type for `Unit` and value allows us to reason about
side-effecting code with the same principles as functional code.
This is essential for a language like Scala that bridges the
worlds of the imperative and the functional.

We have so far seen two imperative-style methods that return `Unit`:
the `println` method from the Scala standard library and Doodle's `draw` method.
Each of these methods does something useful but neither returns a useful result:

~~~ scala
val alsoUninteresting = Circle(10).draw
// alsoUninteresting: Unit = ()
~~~

Designing programs in a functional way involves limiting
the side-effects spread throughout our code.
Doodle is a classic example of functional design---we assemble
a *representation* of the scene we want in a purely functional manner,
and then *interpret* the scene to produce output.
The `draw` method---our interpreter---can use imperative libraries
and mutable state without them intruding into the rest of our application.
