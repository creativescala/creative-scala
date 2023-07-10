## Working Without Worksheets

```scala mdoc:invisible
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._
import cats.effect.unsafe.implicits.global
```

Worksheets are convenient for short programs and quick experiments.
Long worksheets, however, are difficult to read and don't lend themselves to comprehensible code organization.
For larger programs we will need to learn how to work outside of a worksheet.

The first thing we need to know is where we put code that is outside of a worksheet.
We need to save files in the directory `src/main/scala` so that Scala compiler (actually, the build system, but we haven't talked about that yet) can find them.
If you are using the Creative Scala template you already have this directory and there is an example file within it.

Let's create another file to practice the process.
We will create a file called `Circles.scala`,
containing an object called `Circles`.
If you're using Visual Studio Code, follow this process:

1. Right-click on the directory `src/main/scala`
2. Select "New Scala File"
3. Choose "Object" as the type of file
4. Name the object `Circles`

You should have a file that looks like

```scala mdoc:silent
object Circles {
}
```

Now try the following:
define and draw a circle.

```scala
object Circles {
  val circle = Image.circle(100)
  circle.draw()
}
```

You should see a problem, that says something like `Not Found: Image`.
If you are using Visual Studio Code you should see a red underline on some code.
This is saying that the compiler doesn't know what the name `Image` refers to.
This is caused by the missing imports.
Let's add them in.
Your file should look like the following.

```scala mdoc:silent:reset
import cats.effect.unsafe.implicits.global
import doodle.core.*
import doodle.image.*
import doodle.syntax.all.*
import doodle.image.syntax.all.*
import doodle.java2d.*

object Circles {
  val circle = Image.circle(100)
  circle.draw()
}
```

There shouldn't be any errors now,
but how do we run our code?

Unlike a worksheet, any code we write is not automatically run.
We need to add a *main method* to make our code runnable.
A main method is a method that is evaluates when the program starts running.
We have used lots of methods already, but haven't defined our own.
In a later chapter we'll learn all about how to define our own methods.
For now, just copy the example below.

```scala
import cats.effect.unsafe.implicits.global
import doodle.core.*
import doodle.image.*
import doodle.syntax.all.*
import doodle.image.syntax.all.*
import doodle.java2d.*

object Circles {
  val circle = Image.circle(100)
  
  @main def go() = {
    circle.draw()
  }
}
```

In Visual Studio Code you should now see a little link you can click to run the code,
and a circle should appear as normal.

You may wonder why we put the code within an `object`.
We didn't have to do this in a worksheet.
Try the below.

```scala
import cats.effect.unsafe.implicits.global
import doodle.core.*
import doodle.image.*
import doodle.syntax.all.*
import doodle.image.syntax.all.*
import doodle.java2d.*

val circle = Image.circle(100)

@main def go() = {
  circle.draw()
}
```

This also works.
So why do we bother wrapping the code in an object?
There are two good reasons.
One is controlling the scope of declarations.
As programs grow in size the chance increases that we'll use the same name for more than one declaration.
If these declarations are in the same scope we'll have problems.
We've seen that `object` creates a new scope,
so putting our declarations within an object means the names don't collide with other declarations in other parts of our program.
The other reason is a restriction on the code we can write at the *top-level*.
Let's learn about the top-level now.


### The Top-Level

Scala distinguishes between what is called the top-level and other code.
Code at the top-level is code that doesn't have any other code wrapped around.
So the code we've just seen declares `circle` and `go` at the top-level.

In Scala, only declarations can be written at the top-level.
We cannot write a top-level expression.
Try putting, say, `1 + 1` on a separate line in the file.
You should see an error.

@:callout(info)
#### The Top-level in Scala 2 and Scala 3

In Scala 3 you can write any declaration at the top-level.

In Scala 2 you cannot have `val` or `def` at the top-level, only object literals, classes, and traits (the later two of which we haven't yet seen.)
@:@

