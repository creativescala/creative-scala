# Expressions, Values, and Types

Scala programs have three fundamental building blocks:
*expressions*, *values*, and *types*.
An *expression* is a fragment of Scala code that we write in an a text editor.
Valid expressions have a *type* and calculate a *value*. For example,
this expression has the type `String` and calculates the value `HELLO WORLD!`

~~~ scala
"Hello world!".toUpperCase
// res0: String = "HELLO WORLD!"
~~~

A Scala program goes through two distinct stages. First it is *compiled*;
if compiles successfully it can then be *executed*.
The most important distinction between types and values is that
types are determined at compile time,
whereas values can only be determined at run time.
Values can change each time we run the code, whereas types are fixed.
For example, the following expression is certainly of type `String`,
but its value depends on the the user input each time it is run:

~~~ scala
readLine.toUpperCase
~~~

We are used to thinking of types that refer to sets of literals
such as `Int`, `Boolean`, and `String`,
but in general a type is defined as
*anything we can infer about a program without running it*.
Scala developer use types to gain assurances about our programs
before we put them into production.
