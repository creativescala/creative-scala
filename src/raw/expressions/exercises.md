## Exercises

#### Floating Point Failings

Why did I say above that `Double` is an approximation of the real numbers? Think about representing numbers like ⅓ and π. How much space would it take to represent these numbers in decimal?

<div class="solution">
`Double` is an approximation because it has the fit within the computer's finite memory. A `Double` takes up precisely 64-bits, which is enough space to store a lot of digits but not enough to store a number that, like π, has an infinite expansion.

The number ⅓ also has an infinite expansion in decimal. As `Doubles` are stored in binary there are some numbers that can be represented in a finite number of decimal digits but have no finite representation in binary. 0.1 turns out to be one such number.

In general, floating point numbers can lead to nasty surprises if you expect them to act like the reals. They are fine for our purposes in Creative Scala, but don't go using them to write accounting software!
</div>
