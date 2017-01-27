## Types

Now we can write more complex expressions we can talk a little more about types. 

One use of types is stopping us from calling methods that don't exist. The type of an expression tells the compiler what methods exist on the value it evaluates to. Our code won't compile if we try to call to a method that doesn't exist. Here are some simple examples.

```tut:fail:book
"Brontë" / "Austen"
1.take(2)
```

It really is the type of the expression that determines what methods we can call, which we can demonstrate by calling methods on the result of more complex expressions.

```tut:fail:book
(1 + 3).take(1)
```

This process of *type checking* also applies to the parameter of methods.

```tut:fail:book
1.min("zero")
```

Types are a property of expressions and thus exist at compile-time (as we have discussed before.) This means we can determine the type of expression even if evaluating it results in an error at run-time. For example, dividing an `Int` by zero causes a run-time error.

```tut:fail:book
1 / 0
```

The expression `1 / 0` still has a type, and we can get that type from the console as shown below.

```scala
:type 1 / 0
// Int
```

We can also write a compound expression including a sub-expression that will fail at run-time.

```tut:fail:book
(2 + (1 / 0) + 3)
```

This expression also has a type.

```scala
:type (2 + (1 / 0) + 3)
// Int
```
