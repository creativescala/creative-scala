# Impressionist Scala

<div class="callout callout-info">
*Functional programming concepts*

 - Composition
 - Evaluation model
 - Names/values
 - Recursion
 - Functions/methods
 - Comprehensions
 - Types
 - Functions (animation: Int => Shape)
</div>

## Formats
Two lengths: 2 hours and 8 hours

## Expressions, Values, and Types

Scala programs have three fundamental building blocks:
*expressions*, *values*, and *types*:

An *expression* is a fragment of Scala code that we write in an a text editor.
Valid expressions have a *type* and calculate a *value*. For example,
this expression has the type `String` and calculates the value `HELLO WORLD!`

~~~ scala
"Hello world!".toUpperCase()
~~~

A Scala program goes through two distinct stages. First it is *compiled*;
if compiles successfully it can then be *executed*.

The most important distinction between types and values is that
types are determined at compile time,
whereas values can only be determined at run time.
Values can change each time we run the code, whereas types are fixed.

For example, the following expression is certainly of type `String`,
but its value could change each time we evaluate it
(depending on the text the user enters):

~~~ scala
readLine().toUpperCase()
~~~

### Literals
Literals are the atoms of programming

Basic literal / expression / value stuff from Essential Scala

### Compound Expressions
We can't get very far with literals. We need to combine them into more complicated values.

In Scala everything is an object. We interact with objects by method calls.

Expression syntax.

Simple examples.

Apply syntax. DITCH?

### Images
Numbers and strings are boring. Let's switch to images.

Examples. E.g. Circle(20)

We can combine images with methods like on, beside, and above.

Checkerboard example

### Exercises
Concentric circles

Stick figure Vitruvian man

## Declarations

### Bindings
Bind a name to a value. We can refer to them by name rather than repeating ourselves

Substitution model of evaluation.

Checkerboard using names
- define 2x2
- 4x4 is built from 2x2
- 8x8 is built from 4x4

### Colour
Lines and fills

RGB and HSL

Checkerboard using colour

### Methods
Parameters

Difference from bindings
- Methods are not values
- Methods evaluate their body every time they are called

### Exercises
Checkerboard without recursion?

### Recursion
Structural recursion over the natural numbers

### Exercises
Sierpinski triangle

Concentric circles

Checkerboards

# Extended Exercise: Colour palettes
Interesting sequences of colours
- Analagous colours, complements, triads, tetrads.
- Period of repetition
- GCD
- Linear congruential generator

# Extended Exercise: Fractals
Trees? Or Sierpinkski stuff?
