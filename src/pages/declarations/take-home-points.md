## Take Home Points

Some programs cannot be written in a single expression!

Scala provides *value declarations* as a means to
capture and re-use values in the rest of our code.
Because Doodle is based on immutable data structures,
we can re-use single values multiple times without
worrying about unintended side-effects.
The chessboard example demonstrates this nicely---it
re-uses the `fourByFour` value four times and the
`twoByTwo` value sixteen times,
resulting in a compact memory-efficient representation.

Method declarations do a different job.
They allow us to *abstract over parameters*,
creating blocks of code that work with a variety of inputs.
Functional programming places emphasis on
writing methods that *return useful values*,
effectively turning methods into high-level constructors.

For example, we can view a method like `tetradChessBoard()`
as a constructor for a chess board.
Even though the method creates many objects internally,
the substitution model allows us to ignore
the implementation details and treat the method as a black box.
