## Method declarations

Sometimes we want to repeat a process, but each time we do it we change some part of what we're doing. For example, imagine creating chess boards where each chess board has a different combination of colors. It would be extremely tedious to declaration each chess board as we have above for each choice of colors. What we'd like is to be able to define some process for making chess boards that allows the user to specify the color choice for the particular chess board we're making. This is what *methods* allow us to do.

We have already seen method calls. In this section we are going to see how we can create our own methods, using *method declarations*. Like value declarations, method declarations define a name. Instead of giving a name to a value, a method declaration gives a name to a process for creating values. Let's see an example.

~~~ scala
def twoByTwo(color1: Color, color2: Color): Image = {
  val square1 = Rectangle(30, 30) fillColor color1
  val square2 = Rectangle(30, 30) fillColor color2

  (square1 beside square2) above
  (square2 beside square1)
}
~~~ 

This declares a method called `twoByTwo`. The method has two parameters, called `color1` and `color2`, which we have declared to have type `Color`. We have also declared the type of the value the method returns when called, which is `Image`. The body of the method, which is enclosed with optional braces (the `{` and `}` pair) defines how we create the `Image`. This mirrors the process for creating a two by two chess board that we saw above, but in this case we are using the colors we are passed as parameters.

**Syntax summary here**

**Substition model?**

**Exercise: Chess Board**

Declare a method called `fourByFour` that constructs a four-by-four chess board using `twoByTwo` declared above. The method should have two parameters, both `Color`s, that it passes on to `twoByTwo`. 

You should be able to call `fourByFour` like so

~~~ scala
fourByFour(Color.cornflowerBlue, Color.seaGreen) beside
fourByFour(Color.chocolate, Color.darkSalmon)
~~~

to create this picture:

![Two Chessboards](src/pages/declarations/two-chessboards.png)

<div class="solution">
The structure of `fourByFour` is identical to `twoByTwo` except that we use `twoByTwo` to construct the squares we build the chessboard from.

~~~ scala
def fourByFour(color1: Color, color2: Color): Image = {
  val square = twoByTwo(color1, color2)

  (square beside square) above
  (square beside square)
}
~~~
</div>
