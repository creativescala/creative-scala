## Exercises

```scala mdoc:invisible
import doodle.core._
import doodle.image._
import doodle.syntax.all._
import doodle.image.syntax.all._
import doodle.java2d._
```

@:exercise(Compilation Target)

Create a line drawing of an archery target with three concentric scoring bands, as shown in @:fref(pictures:target1).

@:figure{ img = "target1.svg", key = "#fig:pictures:target1", caption = "Simple archery target" }

For bonus credit add a stand so we can place the target on a range, as shown in @:fref(pictures:target2).

@:figure{ img = "target2.svg", key = "#fig:pictures:target2", caption = "Archery target with a stand" }
@:@

@:solution
The simplest solution is to create three concentric circles using the `on` method:

```scala mdoc:silent
Image
  .circle(20)
  .on(Image.circle(40))
  .on(Image.circle(60))
```

For the extra credit we can create a stand using two rectangles:

```scala mdoc:silent
Image
  .circle(20)
  .on(Image.circle(40))
  .on(Image.circle(60))
  .above(Image.rectangle(6, 20))
  .above(Image.rectangle(20, 6))
```
@:@


@:exercise(Stay on Target)

Colour your target red and white, the stand in brown (if applicable),
and some ground in green. See @:fref(pictures:target3) for an example.

@:figure{ img = "target3.svg", key = "#fig:pictures:target3", caption = "Colour archery target" }
@:@

@:solution
The trick here is using parentheses to control the order of composition.
The `fillColor()`, `strokeColor()`, and `strokeWidth()` methods
apply to a single image---we need to make sure that image
comprises the correct set of shapes:

```scala mdoc:silent
Image
  .circle(20).fillColor(Color.red)
  .on(Image.circle(40).fillColor(Color.white))
  .on(Image.circle(60).fillColor(Color.red))
  .above(Image.rectangle(6, 20).fillColor(Color.brown))
  .above(Image.rectangle(20, 6).fillColor(Color.brown))
  .above(Image.rectangle(80, 25).noStroke.fillColor(Color.green))
```
@:@


@:exercise(Floating Point Failings)

When we introduced Doubles, I said they are an approximation to the real numbers. Why do you think this is? Think about representing numbers like ⅓ and π. How much space would it take to represent these numbers in decimal?
@:@

@:solution
`Double` is an approximation because it has the fit within the computer's finite memory. A `Double` takes up precisely 64-bits, which is enough space to store a lot of digits but not enough to store a number that, like π, has an infinite expansion.

The number ⅓ also has an infinite expansion in decimal. Because Doubles are stored in binary there are some numbers that can be represented in a finite number of decimal digits but have no finite representation in binary. 0.1 turns out to be one such number.

In general, floating point numbers can lead to nasty surprises if you expect them to act like the reals. They are fine for our purposes in Creative Scala, but don't go using them to write accounting software!
@:@


@:exercise(Appending Strings)

Join together two strings (known as *appending* strings) using the `++` method. Write equivalent expressions using both the normal method call style and the operator style.
@:@

@:solution
Something like the below should do.

```scala mdoc
"It is a truth ".++("universally acknowledged")
"It is a truth " ++ "universally acknowledged"
```
@:@

@:exercise(Beyond Expressions)

In our current model of computation there are only three components: expressions (program text) with types, that evaluate to values (something within the computer's memory). Is this sufficient? Could we write a stock market or a computer game with just this model? Can you think of ways to extend this model? (We haven't so far addressed this in the book, so there is no reason you should be able to answer this question.)
@:@

@:solution
This is very open ended question. There are several ways to go beyond the model we have so far.

To be useful our programs must be capable of creating effects—changes in the world that go beyond the computer's memory. For example, displaying things on the screen, making sound, sending messages to other computers, and the like. The console implicitly does some of this for us, by printing values on the screen. We'll need to go a bit beyond that for more useful programs.

We also don't have any way to define our own objects and methods, or reuse values in our programs. If we want to, say, use someone's name across a program we have to repeat that name everywhere. We need more methods of *abstraction* and that's what we'll turn to soon.
@:@
