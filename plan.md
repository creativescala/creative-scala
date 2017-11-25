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
      * Precedence rules for operators
  * Now we can write more complex expressions we can talk about another use of types: stopping us from writing programs that don't make sense.
    * The types tells the computer what methods an object has.
    * If we try to call a method an object doesn't have, or give a parameter of the wrong type, the computer won't let us try to evaluate our code.
    * Examples
    * We can write expressions that fail to evaluate, but, because types are a compile time property, we can still give a type to.
    * Examples
    * Exercises.
      * Which of these expressions can we evaluate? Which of these expressions evaluate successfully? Which expressions can we give a type to?
      * *Challenge Exercise*: Our current model of computation is an expression (program text) evaluates to a value (something within the computer's memory). Is this sufficient? Can you think of ways to extend this model? (This is very open ended. Possible answers include effects, names, functions, etc.)
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

### Writing Larger Programs
  * We're getting to the point where it's inconvenient to type programs into the console. In this section we'll learn about two tools for writing larger programs:
    * saving programs to a file so we can edit them in our editor; and
    * breaking our program into reusable parts by giving names to values.
  * Saving code to a file and using :load and :paste
  * Using the console is not going to work for real programs.
  * Code is different in a real program
    * Can't write methods and vals without wrapping them in an object
    * objects
    * imports
    * packages
    * This introduces the concept of names, so let's talk more about them
  * Names
    * We're now going to add the ability to give names to values. We *bind* names to values.
    * Anywhere we might we use expression we can instead use the name bound to the value the expression evaluates to, if we have previously given it a name. Example.
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
    * The archery target
      * Line drawing
      * Coloured
    * Make five boxes in a line
    * Fill the boxes so they have a gradient in saturation from left to right.
    * Make seven nested circles
    * Give the circles a line color so they make a rainbow gradient (i.e. from red to violet).
    * Arrange nine boxes in a square
    * Fill the boxes so they have a gradient in saturation from left to right and in hue from top to bottom
    * Rainbow nested circles
    * Gradient grid
    * Chessboard
    * Archery target
    * *Challenge Exercise* What happens if we have two bindings with the same name in scope at a given point in the program? Example. (The inner most binding shadows the outermost binding.)
    * *Challenge Exercise* Why have scoping rules? Why not just make all names visible in all places? (How would we resolve aliasing? How would we control modularity?)
    * *Challenge Exercise* Create a picture of your own devising on the theme of bubbles.
  * Setting up a standalone project
    * Running code within the Doodle framework is fine for now, but at some point you'll want to setup your own projects. Here's how to do it.
    * Install sbt
    * Create a build.sbt
    * Run sbt
    * compile, console, run
    * Adding doodle as a dependency

### The Substitution Model of Evaluation
  * We need to build a mental model of how Scala expressions are evaluated so we can understand what our programs are doing.
  * We've been getting by with an informal model so far. In this section we make our model a bit more formal.
  * Take an expression like (1 + 2) + (3 + 4). This consists of sub expressions (1 + 2) and (3 + 4) combined together.
  * When we see an expression, we can substitute in the value it evaluates to. So
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
  * The pattern for writing a method
    * Method skeleton
    * Name, parameters, and result type
    * Fill in body. Use the types.
  * Exercises
    * Gradient boxes as a method
    * Gradient nested circles as a method
    * Gradient square where starting color is given as a parameter
    * Abstract over shape that is drawn
    * Chessboard as a method (w/o recursion)
    * Nested circles, but replace circles with a call to gradient circles
    * When are the arguments of a method evaluated? (Before the method is called)
    * *Challenge Exercise* Is a method a value?

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
    * Implementation using pattern matching
    * Conditional expressions as another form of pattern matching
    * Semantics of pattern matching / conditional in terms of substitution.
    * Now we have all the tools to create some interesting pictures
  * Exercises
    * Gradient sequence of shape with length
    * Concentric circles
    * Chessboard
    * Variant of chessboard with varying size squares (similar to square limit)
    * *Challenge Exercise* Sierpinski triangle
    * *Challenge Exercise* Sierpinski triangle w/ gradient colors

### Challenge Chapter: Substitution
  * In this chapter we look deeper into the substitution model.
  * We have seen that Scala evaluates method parameters before the method is called. This is not the only possibility. We could delay evaluation of parameters until they are used in the method body. Example. This is called *call-by-need* or *lazy* evaluation, whereas the model that Scala uses is called *call-by-value* or *eager* evaluation. Example.
  * Can we write `if` as a method in a call-by-value language (e.g. Scala)? If yes, how, if not, why not? For simplicity, write `if` as a method where the true and false clauses are expressions that evaluate in an `Int`. Note that `if` is a *reserved word* in Scala, meaning we can't use it for our own method. So we might write a method like

    `perhaps(condition: Boolean, ifTrue: Int, ifFalse: Int)`

    Consider this expression `if(1 < 2, 0, {println("foo"); 1})`. (No we can't.)

    Could we write `if` as a method in a call-by-need language? (Yes!)
  * Considering only pure expressions to start with, can you find any expression that evaluates to a different value under the two evaluation strategies? (No, they are the same.)

    Now considering impure expressions (i.e. expressions that have side-effects.) Is there a difference between the two strategies. (Yes!)

    Which do you think is preferable when expressions have side-effects? (Open-ended, but the consensus answer is that strict is easier to understand than lazy in the presence of side-effects.)
  * Name capture in substitution.

### Horticulture and Higher-order Methods
  * In this chapter we're going to learn how to draw flowers and use first-class functions.
    * Define "first-class"
  * Flowers
    * A circle
    * Parametric equation over angle
    * If we modulate x and y we'll get something that looks like a flower
    * Can layout using `at`
    * How do we abstract over this, so we can add different modulations of radius?
    * Need to pass a method as a parameter
  * We can make methods values by following the name of the method with the mighty underscore!
  * This is called a function
  * We can also write function literals. Function literal syntax.
  * This means methods can evaluate to functions, and take functions as parameters.
  * Methods or functions that take functions as parameters are known as higher-order methods /functions
  * Exercises
    * Draw many shapes where the function to draw individual shapes is a parameter parameterised by color
    * Fractals doing the same

### Composing Shapes, Functions, and Data
  * In this chapter we learn how to build our own shapes out of the primitive lines and curves that Doodle supplies. As we explore this will learn how to represent sequences of data, such as the sequence of points that define a shape, and manipulate such sequences.
  * Paths
    * Paths are sequences of path elements
    * Path elements
    * Open and closed paths
    * Exercise: create open and closed paths demonstrating difference
  * My God, It's Full of Stars
    * Create star paths
    * Structural recursion over natural numbers---but constructing a List. Nil and ::
    * Lists have the same structure as natural numbers
    * We can write these expressions as transformations on lists. Hold that thought!
  * Structures of Structural Recursion
    * We keep writing programs that have the same shape---structural recursion
    * This is good! Our patterns work. They are familiar.
    * However, this is bad! We keep repeating ourselves.
    * map
      * We keep the structure the same
    * range
    * fold

### Turtle Algebra and Algebraic Data Types
  * In this chapter we explore a new way of creating open paths---turtle graphics---and learn some new ways of manipulating lists and functions.
  * Turtle Graphics
    * It's often easier to define paths in terms of relative coordinates: say how to far move forward or turn relative to our current location.
    * This model is called turtle graphics, developed in the 60s by Seymour Papert in the programming language Logo.
    * Example of triangle.
    * Exercises
      * Polygons---not much different to doing them with paths
      * Spirals---much easier to create with turtle graphics
      * Stars and spirographs---parametric equations again
      * Branching
  * L-systems

### Compositions of Generative Art
  * In this chapter we'll explore techniques from generative art, which will allow us to explore key concepts for functional programming:
    - standard methods for composing complex data;
    - handling side effects while maintaining composition; and
    - generic types.
  * Generative art means art where some part of the composition is determined by an autonomous process. Concretely, for us this will mean adding an element of randomness.
  * How do we add randomness. `math.random`. This is a side-effect. Boo. What do we do? Give up. #yolo. No! We add a new abstraction to handle this.
  * `Distribution[A]`
    * What is `Distribution[A]`. A distribution of some type `A`. Something that will produce a value of type `A` when we run it. Example. Two questions? What is `A`. Why do we care?
      * `A` is a *type parameter* or *generic type*. Like a method parameter. Define / apply distinction. Examples.

### Rendering Random Processes
  * In the previous chapter we saw how to add randomness in a way that maintains substitution, and we learn how to combine *independent* random values using the product operator. What we could not do was make one random event depend on the value of another. For example, we could not ... example here ...

### Challenge Chapter: Digital Art
  * Techniques for digital art
    * Theme and variation
      * Symmetry, shape, and color
      * Naturally applies to recursive images
      * Mixing the random and the deterministic
    * Color
      * complementary and adjacent palettes
      * saturation and transparency
    * Layout using polar coordinates
    * Examples
      Spirals
      Wandering lines
      Circle of overlapping circles
      Trees
  * Using everything you've learned, create an image on the theme of
    * Drops of water
    * Soft sunlight
    * The agony and ecstasy of consciousness in an uncaring universe
    Show the steps you used to arrive at your final image, and describe how you structured your program to reach the final effect.

### Animations
  * We're now going to introduce animations. Good use of higher-order functions.
  * Overview of animation API
    * Streams of events
    * One of these streams is the screen refresh
    * map. Transform a stream of one type of events into a stream of another type of events. Example.
    * scan. Transform a stream of one type of events into a stream of another type of events, carrying some state from one step to another. Example.
    * Sequence transformations together. Example: transform to stream of number, render these numbers of width of a circle.
  * Exercises
    * Animate drawing some of the examples we've already seen
    * Need to think more about these

### Sequences
  * Collections of objects
  * List API
    * Construction
    * map
    * scanLeft and foldLeft
  * Particle Effects

### Project Chapter
  * Animation

### Case Classes
  * Define our own types
  * A class is a template for creating objects

### Pattern Matching
  * Structural recursion over algebraic data types
  * Keyboard input and join

### Project Chapter
### Next Steps
  * Translating techniques here into other domains
    * Collections, Spark
  * Other bits of Scala
    * Algebraic data types
    * Structural recursion extended beyond natural numbers
