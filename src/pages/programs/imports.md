## Packages and Imports

When we changed our code to compile we had to add many *import statements* to it.
In this section we learn about them.

We've seen that one name can shadow another.
This can cause problems in larger programs as many parts of a program many want to put a common name to different uses.
We can create scopes to hide names from the outside, but we must still deal with names defined at the top-level.


We have the same problem in natural language.
For example, if both your brother and friend were called "Ziggy" you would have to qualify which one you meant when you used their name.
Perhaps you could tell from context, or perhaps your friend was "Ziggy S" and your brother was just "Ziggy".

In Scala we can use *packages* to organise names.
A package creates a scope for names defined at the top-level.
All top-level names within the same package are defined in the same scope.
To bring names in a package into another scope we must *import* them.

Creating a package is simple: we write

```scala
package <name>
```

at the top of the file, replace `<name>` with the name of our package.

When we want to use names defined in a package we use an `import` statement, specifying the package name followed by `_` for all names, or the just the name we want if we only want one or a few names.

Here's an example.

<div class="info">
You can't define packages in the console.
To get the following code to work you must put the code within the package `example` into a file and compile it.
</div>

Let's start by defining some names within a package.

```scala
package example

object One {
  val one = 1
}

object Two {
  val two = 2
}

object Three {
  val three = 3
}
```

Now to bring these names into scope we must import them.
We could import just one name.

```scala
import example.One

One.one
```

Or both `One` and `Two`.

```scala
import example.{One, Two}

One.one + Two.two
```

Or all the names in `example`.

```scala
import example._

One.one + Two.two + Three.three
```

In Scala we can also import just about anything that defines a scope, including objects.
So the following code brings `one` into scope.

```scala
import example.One._

one
```

### Package Organisation

Packages stop top-level names from colliding, but what about collisions between package names?
It's common to organise packages in a hierarchy, which helps to avoid collisions.
For example, in Doodle the package `core` is defined within the package `doodle`.
When we use the statement

```scala mdoc:silent
import doodle.core._
```

we're indicating we want the package `core` within the package `doodle`, and not some other package that might be called `core`.
