# Initial Investigations

```scala mdoc:invisible
import creativescala.data.explore.HadCrut5.data
```
Now we have data the natural step is to perform some initial investigations.
For example, how much data we have?

```scala mdoc
data.size
```

We have 2082 records, one for each month from January 1850 to July 2023.
This isn't a *huge* amount, but it's certainly too much for us analyze just by looking at it.
This is where exploratory data analysis, the focus on this part of the book, comes into play.
We'll see many techniques over the next chapters, but we're starting with the most basic.

Perhaps the most basic technique is to just look at some of the data.
Here's the first element.

```scala mdoc
data.head
```

This tells use this element refers to January of 1850, the average temperature was -0.6C below the 1961-1990 baseline, with lower and upper error intervals of approximately -0.9C and -0.3C. (How do know what the meaning of these fields? By reading [the documentation][hadcrut5], in particular the linked paper.) **TODO: Check this**

We can also look at the last element.

```scala mdoc
data.last
```

Here we have information form July of 2023, and the temperature is now above the baseline. 
This seems like it might support global warming, but what about the data inbetween?
Looking at the same month from every year is likely to still be too much to read, but we could look at the same month from each decade.

```scala mdoc
val decades = data.filter(r => r.year % 10 == 0 && r.month == 6)
```

With only 18 measurements, this is more manageable. 
Overall, the data does seem to show increasing temperatures but it would be much easier to see a trend on a graph rather than in printed numbers, so in the next section we'll turn to visualizing data.
Before we get there, however, it's time for you to do a bit of analysis on your own.

[hadcrut5]: https://www.metoffice.gov.uk/hadobs/hadcrut5/index.html


@:exercise(Shall I compare thee to a summer's day?)

In this chapter we're learning about data analysis, but we're also learning how to work with collections of data such as `List`.

When we selected data by decades, we rather arbitrarily chose June as our month of interest.
Write code that instead selects data from January. 
Do you still see a similar trend?
@:@


@:solution
This is a small modification of the original code. 
Instead of looking for `r.month == 6` we look for  `r.month == 1`,
which is the numeric code corresponding to January.

```scala mdoc
val januaryByDecades = data.filter(r => r.year % 10 == 0 && r.month == 1)
```

The trend is not exactly the same as before, but it is simlar enough.
@:@


@:exercise(Statistics is the Grammar of Science)
