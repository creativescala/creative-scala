## Reasoning about Recursion

```scala mdoc:invisible
import doodle.core._
import doodle.core.Image._
import doodle.syntax._
import doodle.jvm.Java2DFrame._
import doodle.backend.StandardInterpreter._
```

We're now experienced users of structural recursion over the natural numbers.
Let's now return to our substitution model and see if it works with our new tool of recursion.

Recall that substitution says we can substitute the value of an expression wherever we see a value.
In the case of a method call, we can substitute the body of the method with appropriate renaming of the parameters.

Our very first example of recursion was `boxes`, written like so:

```scala mdoc:silent
val aBox = Image.rectangle(20, 20).fillColor(Color.royalBlue)

def boxes(count: Int): Image =
  count match {
    case 0 => Image.empty
    case n => aBox beside boxes(n-1)
  }
```

Let's try using substitution on `boxes(3)` to see what we get.

Our first substitution is

```scala mdoc:silent
boxes(3)
// Substitute body of `boxes`
3 match {
  case 0 => Image.empty
  case n => aBox beside boxes(n-1)
}
```

Knowing how to evaluate a `match` expression and using substitution again gives us

```scala mdoc:silent
3 match {
  case 0 => Image.empty
  case n => aBox beside boxes(n-1)
}
// Substitute right-hand side expression of `case n`
aBox beside boxes(2)
```

We can substitute again on `boxes(2)` to obtain

```scala mdoc:silent
aBox beside boxes(2)
// Substitute body of boxes
aBox beside {
  2 match {
    case 0 => Image.empty
    case n => aBox beside boxes(n-1)
  }
}
// Substitute right-hand side expression of `case n`
aBox beside {
  aBox beside boxes(1)
}
```

Repeating the process a few more times we get

```scala mdoc:silent
aBox beside {
  aBox beside {
    1 match {
      case 0 => Image.empty
      case n => aBox beside boxes(n-1)
    }
  }
}
// Substitute right-hand side expression of `case n`
aBox beside {
  aBox beside {
      aBox beside boxes(0)
  }
}
// Substitute body of boxes
aBox beside {
  aBox beside {
    aBox beside {
      0 match {
        case 0 => Image.empty
        case n => aBox beside boxes(n-1)
      }
    }
  }
}
// Substitute right-hand side expression of `case 0`
aBox beside {
  aBox beside {
    aBox beside {
      Image.empty
    }
  }
}
```

Our final result, which simplifies to

```scala mdoc:silent
aBox beside aBox beside aBox beside Image.empty
```

is exactly what we expect.
Therefore we can say that substitution works to reason about recursion.
This is great!
However the substitutions are quite complex and difficult to keep track of without writing them down.
A more practical way to reason about recursion is to assume that the recursion works and only worry about what new comes from each step.

For example, when reasoning about `boxes`

```scala mdoc:reset:invisible
import doodle.core._
import doodle.core.Image._
import doodle.syntax._
val aBox = Image.rectangle(20, 20).fillColor(Color.royalBlue)
```
```scala mdoc:silent
def boxes(count: Int): Image =
  count match {
    case 0 => Image.empty
    case n => aBox beside boxes(n-1)
  }
```

we can tell the base case is correct by inspection.
Looking at the recursive case we *assume* that `boxes(n-1)` will do the right thing.
We then ask ourselves "is what we do in the recursion case correct and is the recursion itself correct?"
The answer is yes: if the recursion `boxes(n-1)` creates `n-1` boxes in a line, sticking a box in front of them is the right thing to do.
This way of reasoning is much more compact that using substitution *and* guaranteed to work *if* we're using structural recursion.


### Exercises {-}

Below are some rather silly examples of structural recursion.
Work out if the methods do what they claim to do *without* running them.

```scala mdoc:silent
// Given a natural number, returns that number
// Examples:
//   identity(0) == 0
//   identity(3) == 3
def identity(n: Int): Int =
  n match {
    case 0 => 0
    case n => 1 + identity(n-1)
  }
```

<div class="solution">
It sure does!
The base case is straightforward.
Looking at the recursive case, we assume that `identity(n-1)` returns the identity for `n-1` (which is exactly `n-1`).
The identity for `n` is then `1 + identity(n-1)`.
</div>

```scala mdoc:silent
// Given a natural number, double that number
// Examples:
//   double(0) == 0
//   double(3) == 6
def double(n: Int): Int =
  n match {
    case 0 => 0
    case n => 2 * double(n-1)
  }
```

<div class="solution">
No way!
This method is broken in two different ways.
Firstly, because we're multiplying in the recursive case we will eventualy end up multiplying by base case of zero, and therefore the entire result will be zero.

We might try and fix this by adding a case for `1` (and perhaps wonder why the structural recursion skeleton let us down).

```scala mdoc:reset:silent
def double(n: Int): Int =
  n match {
    case 0 => 0
    case 1 => 1
    case n => 2 * double(n-1)
  }
```

This doesn't give us the correct result, however! We're doing the wrong thing at the recursive case: we should be adding, not multiplying.

A bit of algebra:

```scala
2(n-1 + 1) == 2(n-1) + 2
```

So if `double(n-1)` is `2(n-1)` then we should *add* 2, not multiply by 2.
The correct method is

```scala mdoc:reset:silent
def double(n: Int): Int =
  n match {
    case 0 => 0
    case n => 2 + double(n-1)
  }
```
</div>
