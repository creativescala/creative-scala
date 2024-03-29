# Names

```scala mdoc:invisible
import doodle.core.*
import doodle.image.*
import doodle.syntax.all.*
import doodle.image.syntax.all.*
import doodle.java2d.*
import cats.effect.unsafe.implicits.global
```

We use names to refer to things.
For example, "Professeur Emile Perrot" refers to a very fragrant variety of rose, while "Cherry Parfait" is a highly disease resistant rose variety that barely smells at all.
Much ink has been spilled, and many a chin stroked, on how exactly this relationship works in spoken language.
Programming languages are much more constrained, which allows us to be much more precise: names refer to values.
If we have named a value, wherever we would write out the expression that creates the value we can instead use the name.
In other words, a name evaluates to the value it refers to.
We will sometimes say names are *bound* to values, or a name introduces a *binding*.
This naturally raises the question: how do we give names to values?
There are several ways to do this in Scala.
Let's see a few.


## Object Literals

One way to name a value is to use an *object literal*. Here is an example.

```scala mdoc:silent
object Example {
  Image.circle(100).fillColor(Color.paleGoldenrod).strokeColor(Color.indianRed).draw()
}
```

This is a literal expression, like other literals we've seen so far, but in this case it creates an object with the name `Example`.
When we use the name `Example` in a program it evaluates to that object.

```scala
Example
// Example.type = Example$@76c39258
```

Try writing `Example` a few times in a worksheet.
Do you notice any difference in uses of the name?
You might have noticed that the *first* time you entered the name `Example` a picture was drawn, but on subsequent uses this didn't happen.
The first time we use an object's name the body of the object is evaluated and the object is created.
On subsequent uses of the name the object already exists and is not evaluated again.
We can tell there is a difference in this case because the expression inside the object calls the `draw` method.
If we replaced it with something like `1 + 1` (or just dropped the call to `draw`) we would not be able to tell the difference.
We'll have plenty more to say about this in a later chapter.

Notice that the type of `Example` is `Example.type`, a unique type that no other value has.


## `val` Declarations

Declaring an object literal mixes together object creation and defining a name.
It would be useful if we could separate the two, so we could give a name to a pre-existing object.
(Remember that all values are objects.)
A `val` declaration allows us to do this.

We use `val` by writing

```scala
val <name> = <value>
```

replacing `<name>` and `<value>` with the name and the expression evaluating to the value respectively.
For example

```scala mdoc:silent
val one = 1
val anImage = Image.circle(100).fillColor(Color.red)
```

These two declarations define the names `one` and `anImage`.
We can use these names to refer to the values in later code.

```scala mdoc
one
anImage
```


## Declarations

We've talked about declarations and definitions above.
It's now time to be precise about what these terms mean.

We already know about expressions.
They are a part of a program that evaluate to a value.
A *declaration* or *definition* is a different part of a program.
Declarations do not evaluate to a value.
Instead they give a name to something (not always to a value as we can declare types in Scala.)
Both `object` and `val` are declarations.

One consequence of declarations being separate from expressions is we can't write programs like

```scala
val one = ( val aNumber = 1 )
```

because `val aNumber = 1` is not an expression and thus does not evaluate to a value.

We can however write

```scala mdoc:reset
val aNumber = 1
val one = aNumber
```


@:callout(info)
#### Declaration Syntax

A declaration gives a name to a value.

An object literal is a declaration with syntax

```scala
object name {
  body
}
```

where `name` is the name of the object, and `body` is zero or more declarations or expressions.

A `val` declaration has syntax

```scala
val name = expression
```

where `expression` is an expression and `name` is the name given to the value the expression evaluates to.
@:@


@:exercise(Anonymous Objects)
It seems a bit unsatisfactory to have both `object` literals and `val` declarations, as they both give names to values.
Can you declare an object literal without a name?
If we can, we could then use `val` for declaring names, and use `object` to create objects without naming them.
@:@

@:solution
No, Scala doesn't allow us to do this.
For example, we can't write

```scala
object {}
```

We have to give a name to any object literal we create.
@:@

@:exercise(Objecting to Objects)
If we can't have anonymous objects, why do we have `val`?
If all values are objects, why not just use `object` to give names to values of instead of `val`?
@:@

@:solution
Remember that every `object` defines a unique type.
So we cannot define an `object` that is also an `Int`, for example.
It will get hard to get to write code if we never have two values of the same type.
@:@


@:exercise(Values in Objects)
Can we declare a `val` inside an object literal?
If we can declare a `val` inside an object literal, can we later refer to that name?
@:@

@:solution
We sure can!

We can put a `val` inside an object literal like so:

```scala mdoc:reset:silent
object Example {
  val hi = "Hi!"
}
```

We can then refer to it using the `.` syntax we've been using already.

```scala mdoc
Example.hi
```

Note that we can't use `hi` on it's own

```scala mdoc:fail
hi
```

We have to tell Scala we want to refer to the name `hi` defined inside the object `Example`.
@:@


## Scope

If you did the last exercise (and you did, didn't you?) you'll have seen that a name declared inside an object can't be used outside the object without also referring to the object that contains the name.
Concretely, if we declare

```scala mdoc:reset:silent
object Example {
  val hi = "Hi!"
}
```

we can't write

```scala mdoc:fail:silent
hi
```

We must tell Scala to look for `hi` inside `Example`.

```scala mdoc
Example.hi
```

We say that a name is *visible* in the places where it can be used without qualification, and we call the places where a name is visible its *scope*.
So using our fancy-pants new terminology, `hi` is not visible outside of `Example`, or alternatively `hi` is not in scope outside of `Example`.

How do we work out the scope of a name?
The rule is fairly simple: a name is visible from the point it is declared to the end of the nearest enclosing braces (braces are `{` and `}`).
In the example above `hi` is enclosed by the braces of `Example` and so is visible there.
It's not visible elsewhere.

We can declare object literals inside object literals, which allows us to make finer distinctions about scope.
For example in the code below

```scala mdoc:silent
object Example1 {
  val hi = "Hi!"

  object Example2 {
    val hello = "Hello!"
  }
}
```

`hi` is in scope in `Example2` (`Example2` is defined within the braces that enclose `hi`).
However the scope of `hello` is restricted to `Example2`, and so it has a smaller scope than `hi`.

What happens if we declare a name within a scope where it is already declared?
This is known as *shadowing*.
In the code below the definition of `hi` within `Example2` shadows the definition of `hi` in `Example1`

```scala mdoc:reset:silent
object Example1 {
  val hi = "Hi!"

  object Example2 {
    val hi = "Hello!"
  }
}
```

Scala let's us do this, but it is generally a bad idea as it can make code very confusing.

We don't have to use object literals to create new scopes.
Scala allows us to create a new scope just about anywhere by inserting braces.
So we can write

```scala mdoc:reset:silent
object Example {
  val good = "Good"

  // Create a new scope
  {
    val morning = good ++ " morning"
    val toYou = morning ++ " to you"
  }

  val day = good ++ " day, sir!"
}
```

`morning` (and `toYou`) is declared within a new scope. We have no way to refer to this scope from the outside (it has no name) so we cannot refer to `morning` outside of the scope where it is declared.
If we had some secrets that we didn't want the rest of the program to know about this is one way we could hide them.

Braces on their own are known as a *block expression* in Scala. 
Not only do they define a new scope,
but, as their name suggests, they are an expression and therefore evaluate to a value.
That value is the last expression in the block.
So in the following the block evaluates to `3`.

```scala mdoc
{
  val one = 1
  val two = 2
  1 + 2
}
```

We can also give a name to this value.

```scala mdoc
val three = {
  val one = 1
  val two = 2
  1 + 2
}
```


The way nested scopes work in Scala is called *lexical scoping*.
Not all languages have lexical scoping.
For example, Ruby and Python do not, and Javascript has only recently acquired lexical scoping.
It is the authors' opinion that creating a language without lexical scope is an idea on par with eating a bushel of Guatemalan insanity peppers and then going to the toilet without washing your hands.


@:exercise(Scoping Out Scope)
Test your understanding of names and scoping by working out the value of `answer` in each case below.

```scala mdoc:silent
val a = 1
val b = 2
val answer = a + b
```
@:@
@:solution
A simple example to get started with. `answer` is `1 + 2`, which is `3`.
@:@

```scala mdoc:silent
object One {
  val a = 1

  object Two {
    val a = 3
    val b = 2
  }

  object Answer {
    val answer = a + Two.b
  }
}
```

@:solution
Another simple example. `answer` is `1 + 2`, which is `3`. `Two.a` is not in scope where `answer` is defined.
@:@

```scala mdoc:reset:silent
object One {
  val a = 5
  val b = 2

  object Answer {
    val a = 1
    val answer = a + b
  }
}
```

@:solution
Here `Answer.a` shadows `One.a` so `answer` is `1 + 2`, which is `3`.
@:@

```scala mdoc:reset:silent
object One {
  val a = 1
  val b = a + 1
  val answer = a + b
}
```

@:solution
This is perfectly fine. The expression `a + 1` on the right hand side of the declaration of `b` is an expression like any other so `answer` is `3` again.
@:@

```scala mdoc:reset:fail:silent
object One {
  val a = 1

  object Two {
    val b = 2
  }

  val answer = a + b
}
```

@:solution
This code doesn't compile as `b` is not in scope where `answer` is declared.
@:@

```scala mdoc:silent:fail
object One {
  val a = b - 1
  val b = a + 1

  val answer = a + b
}
```

@:solution
Trick question! This code doesn't work. Here `a` and `b` are defined in terms of each other which leads to a circular dependency that can't be resolved.
@:@
