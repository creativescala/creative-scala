## Take Home Points

Scala is both functional and object oriented.
In this chapter we were introduced to some of its functional aspects.

The most important aspect of a functional language is that
*functions are first class values*.
This allows us to parameterize one method or function by another,
which is an enormously powerful abstraction tool
that we saw in action with our revisions to the `manyShapes` method:

~~~ scala
def manyShapes(n: Int, singleShape: Int => Image): Image =
  ???
~~~

So far we have mainly written code using recursion.
However, don't be fooled into think that Scala is all about recursion.
In the next chapter we will look at Scala's collections library,
which provides many convenient high level transformation methods that
take functions as parameters.
