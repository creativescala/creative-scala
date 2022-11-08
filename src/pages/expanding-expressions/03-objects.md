# Values are Objects

In Scala all values are *objects*. An object is a grouping of data and operations on that data. For example, 2 is an object. The data is the integer 2, and the operations on that data are familiar operations like +, -, and so on. We call operations of an object the object's *methods*. 

## Method Calls

We interact with objects by *calling* or *invoking* methods. For example, we can get the uppercase version of a `String` by calling its `toUpperCase` method.

```scala mdoc
"Titan!".toUpperCase
```

Some methods accept *parameters* or *arguments*, which control how the method works. The `take` method, for example, takes characters from a `String`. We must pass a parameter to `take` to specify how many characters we want.

```scala mdoc
"Gilgamesh went abroad in the world".take(3)
"Gilgamesh went abroad in the world".take(9)
```

A method call is an expression, and thus evaluates to an object. This means we can chain method calls together to make more complex programs:

```scala mdoc
"Titan!".toUpperCase.toLowerCase
```

@:callout(info)
#### Method Call Syntax

The syntax for a method call is

```scala
anExpression.methodName(param1, ...)
```

or

```scala
anExpression.methodName
```

where

- `anExpression` is any expression (which evaluates to an object)
- `methodName` is the name of the method
- the optional `param1, ...` are one or more expressions evaluating to the parameters to the method.
@:@


## Operators

We have said that all values are objects, and we call methods with the syntax `object.methodName(parameter)`. How then do we explain expressions like `1 + 2`?

In Scala, and expression written `a.b(c)` can be written `a b c`. So these are equivalent:

```scala mdoc
1 + 2
1.+(2)
```

This first way of calling a method is known as *operator* style.

@:callout(info)
#### Infix Operator Notation {-}

Any Scala expression written `a.b(c)` can also be written `a b c`.

Note that `a b c d e` is equivalent to `a.b(c).d(e)`, not `a.b(c, d, e)`.
@:@
