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

### Conditional Expressions
  * Conditionals
    * Evaluate an expression based on some condition
    * Syntax
    * Examples
    * The entire conditional evaluates to the value of the expression that is evaluated
  * Exercises
    * Math.random() generates a random number between 0 and 1. This is a side-effect, as it differs everytime we run it. We'll talk more about side-effects in the next section.
    * Draw a circle with randomly chosen hue, saturation, and lightness.
    * Draw two circles with randomly chosen hue, saturation, and lightness. This can get tedious. What would make this less tedious? (Functions)
    * What if we wanted to make a sequence of boxes coloured with a gradient that starts with a randomly chosen color. What do we need to do this? (Functions or names)
    * Using Math.random() write a program that evaluates to a blue circle if the random number is < 0.5 and a red square otherwise.
    * Using Math.random() write a program that evaluates to a circle or a square inside a circle. (Emphasising that if is an expression.)

### The Substitution Model of Evaluation
  * We need to build a mental model of how Scala expressions are evaluated so we can understand what our programs are doing.
  * We've been getting by with an informal model so far. In this section we make our model a bit more formal.
  * Take an expression like (1 + 2) + (3 + 4). This consists of sub expressions (1 + 2) and (3 + 4) combined together.
  * When we see an expression, we can subsistute in the value it evaluates to. So 
    (1 + 2) + (3 + 4) == 3 + 7 == 10
  * This is known as *substitution*, *reducing an expression*, or *equational reasoning* (and no doubt some terms I have forgotten). The latter term is an allusion to doing the same operation in high school algebra.
  * In what order does substitution take place? Does it matter?
  * We can't tell the order. Expressions that we can freely substitute are sometimes called *pure*.
  * We need to add *side effects* or *impure* expressions so we can observe order of evaluation.
  * Blocks
    * A group of expressions
    * Evaluates to the value of the last expression
  * Println
    * Expression that prints on the console and returns Unit.
    * Many (but not all) side-effecting expressions evaluate to Unit, as we evaluate them for their side effect (printing, drawing), not for their value. Unit is the "uninteresting" value.
    * Can't substitute calls to println. Example.
  * With blocks and println we can observe order of evaluation. Example: {println("a"); 3} + {println("b"); 4}
  * Exercises
    * What is the order of evaluation for expressions? (Left to right)
    * What is the order of evaluation for expressions in a block? (left to right and top to bottom)

### Declarations
  * We're now going to add the ability to give names to values. We *bind* names to values.
  * Anywhere we might we use expression we can instead use the name bound to the value the expression evalutes to, if we have previously given it a name. Example.
  * Names allow us to *abstract* over expressions, replacing repetition of an expression with the name.
  * This allows us to write simpler programs.
  * Example: boxes in a line with gradient fill
  * It also allows us to write programs that were previously impossible
  * Example: Five boxes in a line with gradient fill starting from random color.
  * Exercise: Can substitution using names change the meaning of a program? (Give example.) (Like other expressions, we can't substitute where there are side effects)
  * Scope
    * There is also a concept of *scope*, which determines which names we can use in which parts of our program. If we can use a name, it is *in scope* or *bound*.
    * Scala has what is called *lexical scoping*, which basically means if you can find a name declared above where it is used AND in an enclosing block it is in scope. Example.
  * Exercises
    * Rainbow nested circles
    * Gradient grid
    * Chessboard
    * Using Math.random() write a program that evaluates to a sequence of gradient filled circle if the random number is < 0.5 and a sequence of gradient filled squares otherwise. Make the color starting the gradient random.

### Writing Larger Programs
  * We're getting to the point where it's inconvenient to type programs into the console
  * Saving code to a file and using :load and :paste
  * Using the console is not going to work for real programs. 
  * Install sbt
  * Create a build.sbt
  * Run sbt
  * compile, console, run
  * Adding doodle as a dependency
  * Code is different in a real program
    * Can't write methods and vals without wrapping them in an object
    * objects
    * imports
    * packages

### First-order Methods
  * Vals allow us to abstract over values. Methods allow us to abstract over expressions.
  * We've already used methods. Now we'll see how to write our own methods.
  * Method syntax
    * def name
    * parameters
    * Type declarations
    * = body (can use a block)
  * Method semantics
    * Parameters are in the scope in the body
    * The value of a method call is value of the body expression
    * Method is in scope in the block in which it's defined
    * We can substitute a method call with the value it evaluates to, if the body of the method is pure (has no side-effects)
  * Exercises
    * Gradient boxes as a method
    * Gradient nested circles as a method
    * Gradient square where starting color is given as a parameter
    * Abstract over shape that is drawn
    * Chessboard as a method (w/o recursion)
    * Nested circles, but replace circles with a call to gradient circles
    * When are the arguments of a method evaluated? 
    * Is a method a value?

### Recursion
  * Methods can call other methods. We've seen examples before.
  * Methods can call themselves. Example: nested circles
  * Understanding recursion
    * Substitution
    * Attempting to expand recursive calls is the road to madness
    * Assume the recursive call works, and see what we do on this step
    * We can do better than that, with a recipe for writing recursive functions that always work
  * Structural recursion over the natural numbers
    * Base case
    * Inductive case
  * Now we have all the tools to create some interesting pictures
  * Exercises
    * Gradient sequence of shape with length
    * Concentric circles
    * Chessboard
    * Variant of chessboard with varying size squares (similar to square limit)
    * Sierpinski triangle
    * Sierpinski triangle w/ colors
    * Mutually recursive many shapes
    * Trees using polar coordinates?
    * Spirals?
    * Random circles?
    
### Digital Art
  * Do we have a section on ideas for creating digital art? Just some basics? Fractals, random art, etc.
  * A larger project to create something interesting on a particular theme?

### Higher-order Methods
  * We can make methods values by following the name of the method with the mighty underscore!
  * This is called a function
  * We can also write function literals. Function literal syntax.
  * This means methods can evaluate to functions, and take functions as parameters.
  * Methods or functions that take functions as parameters are known as higher-order methods /functions
  * Exercises
    * Draw many shapes where the function to draw individual shapes is a parameter parameterised by color
    * Fractals doing the same

### Animations
  * We're now going to introduce animations. Good use of higher-order functions.
  * Overview of animation AP
    * Streams of events
    * One of these streams is the screen refresh
    * map. Example 
    * scan, carry some state---like a recursive function. Example
    * Exercises
      * Animate drawing some of the examples we've already seen
      * Need to think more about these
        * 
### Case Classes
  * Define our own types
  * A class is a template for creating objects
  * Particle effects

### Next Steps
  * Translating techniques here into other domains
    * Collections, Spark
  * Other bits of Scala
    * Algebraic data types
    * Structural recursion extended beyond natural numbers
