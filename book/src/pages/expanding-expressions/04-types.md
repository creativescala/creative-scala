# Types

Now that we can write more complex expressions, we can talk a little more about types.

One use of types is stopping us from calling methods that don't exist. The type of an expression tells the compiler what methods exist on the value it evaluates to. Our code won't compile if we try to call to a method that doesn't exist. Here are some simple examples.

```scala mdoc:fail
"Brontë" / "Austen"
```

```scala mdoc:fail
1.take(2)
```

It really is the type of the expression that determines what methods we can call, which we can demonstrate by calling methods on the result of more complex expressions.

```scala mdoc:fail
(1 + 3).take(1)
```

This process of *type checking* also applies to the parameter of methods.

```scala mdoc:fail
1.min("zero")
```

Types are a property of expressions and thus exist at compile time (as we have discussed before.) This means we can determine the type of an expression even if evaluating it results in an error at run time. For example, dividing an `Int` by zero causes a run-time error.

```scala mdoc:crash
1 / 0
```

The expression `1 / 0` still has a type. One way we can find out that type is by putting an incorrect type declaration on the expression. A type declaration tells the compiler what we believe the type of an expression is. If the compiler cannot show this is the case, it will print an error message telling us the type it believes is correct. In the example below we use a type declaration of `String` and the compiler responds that the correct type is `Int`.

```scala mdoc:fail
(1 / 0): String
```

We can also write a compound expression including a sub-expression that will fail at run-time.

```scala mdoc:crash
(2 + (1 / 0) + 3)
```

This expression also has a type.

```scala mdoc:fail
(2 + (1 / 0) + 3): String
```
