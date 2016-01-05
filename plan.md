# Creative Scala v2
## Goals
  * Accessible to people who have not programmed a computer before
  * Develop a basic machine model that can be used to predict program behavior
  * Be able to read, but perhaps not write, moderately complex Scala code
  * Provide a foundation in fundamental concepts that will transfer to Essential Scala and beyond
  * Demonstate that programming in general, and Scala in particular, can be fun

## Outline

### Introduction
  Layout goals and structure of the book

### Getting Started
  * Install required software
  * Text editor + SBT OR IntelliJ
  * Doodle
  * Run a basic program

### Expressions and Values
  * The fundamental building blocks of Scala programs are expressions, values, and types
  * Expressions are program text
    * You can write them with a text editor
    * Print them onto paper
    * Paste them on a wall
    * Literals are the simplest expression. They evaluate to "themselves".
    * Examples of literals (Int, Double, String, Boolean, Char)
  * When we run expressions they result in values
    * Running is sometimes called evaluation.
    * Run literals (or any expression) by pressing enter in the console
    * We'll see other ways to evaluate programs later.
    * Examples of evaluating literals
    * Compile time and runtime
  * Expressions have types
    * A type is something we can work out about the expression without running it
    * Ergo, types exist at compile time.
    * For example, if it runs successfully it result in an integer
    * Or it will evaluate to a stream of points representing the mouse position at each instance of time.
    * Examples of types via the console
  * Values are things that live in the computer's memory
  * In Scala all values are objects
    * This means we interact with them by calling methods.
    * A method is like a function, which you might know about from math, but it lives side a function
    * Uppercase / lowercase examples
    * A method might have parameters, also called, arguments, that give extra information to the method.
    * indexOf example
    * Access it with dot notation. object.method(arguments)
    * Examples
    * Operator style
    * Examples
    * Exercises
      * Write an expression using integer literals, addition, and subtraction that evaluates to 42.
      * Append strings using the `++` method using both method style and operator style
  * Now we can write more complex expressions we can talk about another use of types: stopping us from writing programs that don't make sense.
    * The types tells the computer what methods an object has.
    * If we try to call a method an object doesn't have, or give a parameter of the wrong type, the computer won't let us try to evaluate our code.
    * Examples
    * We can write expressions that fail to evaluate, but, because types are a compile time property, we can still give a type to.
    * Examples
    * Exercises. Which of these expressions can we evaluate? Which of these expressions evaluate successfully? Which expressions can we give a type to?
  * This is our most basic model. We will elaborate on it throughout the book.

### Pictures
  * Programs with numbers and strings aren't' so exciting, so we'll mostly deal with graphics and, later, animation in this book.
  * Primitive shapes
  * Color
    * Fill and line color
    * HSLA color
    * Color transformations
  * Layout
    * above / beside / on
    * at 
  * Exercises
    * Make five boxes in a line
    * Fill the boxes so they have a gradient in saturation from left to right. 
    * Make seven nested circles
    * Give the circles a line color so they make a rainbow gradient (i.e. from red to violet).
    * Arrange nine boxes in a square
    * Fill the boxes so they have a gradient in saturation from left to right and in hue from top to bottom
    * The archery target
      * Line drawing
      * Coloured

### Compound Expressions
  * We need to build a mental model of how Scala expressions are evaluated so we can understand what our programs are doing.
  * In this section we look at more complex expressions, and start developing that mental model.
  * Method calls
    * Our first stop is something we already know: method calls. In what order are method calls evaluated?
    * Left-to-right eager evaluation (evaluated once)
    * Not the only choice, but we won't go into detail on other possibilities.
    * For the programs we've written so far it doesn't actually matter!
    * Observe using side effects (`println`)
    * A side effect, for now, is any expression that has an observable difference depending on the order of evaluation or the number of times it is evaluated. `draw` is a side-effect.
    * Unit. Many (but not all) side-effecting expressions evaluate to Unit, as we evaluate them for their side effect (printing, drawing), not for their value. Unit is the "uninteresting" value.
  * Conditionals
    * Evaluate an expression based on some condition
    * Syntax
    * Examples
    * The entire conditional evaluates to the value of the expression that is evaluated
  * Blocks
    * A group of expressions
    * Evaluates to the value of the last expression
    * At the moment these aren't so useful to us but we'll see a use in the next chapter
  * Exercises
    * Math.random() generates a random number between 0 and 1. This is a side-effect, as it differs everytime we run it.
    * Using Math.random() write a program that evaluates to a blue circle if the random number is < 0.5 and a red square otherwise.
    * Using Math.random() six times, write a program that evalutes to two circles with randomly chosen colors.

### Declarations
  * Naming values
  * The substitution model of evaluation
  * Chessboard

### First-order Methods
  * Abstract over expressions
  * Type declarations

### Recursion
  * Structural recursion over the natural numbers

### Higher-order Methods
  * Animations

