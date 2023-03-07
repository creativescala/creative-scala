## Local Reasoning

We've seen that the order of evaluation is only really important when we have side effects.
For example, if the following expressions produce side effects

```scala
disableWarheads()
launchTheMissles()
```

we really want to ensure that the expressions are evaluated top to bottom so the warheads are disabled before the missles are launched.

All useful programs must have some effect, because effects are how the program interacts with the outside world.
The effect might just be printing out something when the program has finished, but it's still there.
Minimising side effects is a key goal of functional programming so we will spend a few more words on this topic.

Substitution is really easy to understand.
When the order of evaluation doesn't matter it means any other code cannot change the meaning of the code we're looking at.
`1 + 1` is always `2`, no matter what other code we have in our program, but the effect of `launchTheMissles()` depends on whether we have already disabled the warheads or not.

The upshot of this is that pure code can be understood in isolation.
Since no other code can change its meaning, if we're only interested in one fragment we can ignore the rest of the code.
The meaning of impure code, on the other hand, depends on all the code that will have run before it is evaluated.
This property is known as *local reasoning*.
Pure code has it, but impure code does not.

As programs get larger it becomes harder and harder to keep all the details in our head.
Since the size of our head is a fixed quantity the only solution is to introduce abstraction.
Remember that an abstraction is the removal of irrelevant details.
Pure code is the ultimate abstraction, because it tells us that everything else is an irrelevant detail.
This is one of the properties that gets functional programmers really excited: the ability to make large programs understandable.
Functional programming doesn't mean avoiding effects, because all useful programs have effects.
It does, however, mean controlling effects so the majority of the code can be reasoned about using the simple model of substitution.


### The Meaning of Meaning

So far, we've talked a lot about the meaning of code, where we've taken "meaning" to mean to the result it evaluates to, and perhaps the side effects it performs.

In substitution, the meaning of a program is exactly what it evaluates to.
Thus two programs are equal if they evaluate to the same result.
This is precisely why side effects break substitution: the substitution model has no notion of side effects and therefore cannot distinguish two programs that differ by their effects.

There are other ways in which programs can differ.
For example, one program may take longer than another to produce the same result.
Again, substitution does not distinguish them.

Substitution is an abstraction, and the details it throws away are everything except for the value.
Side effects, time, and memory usage are all irrelevant to substitution, but perhaps not to the people writing or running the program.
There is a tradeoff here.
We can employ richer models that capture more of these details, but they are much harder to work with.
For most people most of the time substitution makes the right tradeoff of being dead simple to use while still being useful.
