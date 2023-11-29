# Expressive Expressions

We're now going to look at creating images. 
In this chapter we have a basic introduction.
In later chapters we'll learn much more about creating images alongside learning more advanced programming techniques.

To create image we will use a library called [Doodle](https://www.creativescala.org/doodle/).

@:callout(info)
You will need to add the following to the worksheet to be able to use Doodle.

```scala mdoc:silent
import cats.effect.unsafe.implicits.global
import doodle.core.*
import doodle.image.*
import doodle.syntax.all.*
import doodle.image.syntax.all.*
import doodle.java2d.*
```
@:@

