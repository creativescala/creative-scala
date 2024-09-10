# Initial Investigations

```scala mdoc:invisible
import creativescala.data.explore.HadCrut5.data
```
Now we have data, the natural next step is to perform some initial investigations.
For example, how many measurements do we have?

```scala mdoc
data.size
```

This tells us we have 2082 records, one for each month from January 1850 to July 2023.
This isn't a *huge* amount, but it's certainly too much to analyze just by looking at it.
We'll see some approaches to analzying the data in just a moment, but for now let's continue exploring.

Perhaps the next obvious step is to look at some of the data.
Here's the first element.

```scala mdoc
data.head
```

This tells use this element refers to January of 1850, the average temperature was -0.6C below the 1961-1990 baseline, with lower and upper error intervals of approximately -0.9C and -0.3C. (How do know what the meaning of these fields? By reading [the documentation][hadcrut5], in particular the linked paper.) **TODO: Check this**

We can also look at the last element.

```scala mdoc
data.last
```

Here we have information for July of 2023, and the temperature is now above the baseline. 
The difference between the first and last measurements supports the idea that temperatures are rising over time.
However, we could quite rightly suggest this may just be a conincidence.
What do the measurements between these points show?
Looking at the same month from every year is likely to still be too much to read, but we could look at the same month from each decade.

```scala mdoc
val decades = data.filter(r => r.year % 10 == 0 && r.month == 6)
```

With only 18 measurements, this is more manageable. 
Overall, the data does seem to show a trend of increasing temperatures, but once again we've made some rather arbitrary choices: to only sample each decade and to choose July as our month of interest within the decade.
At this point we have, however, gained some knowledge of our data.
We know how many measurements we have, the time span they cover, and what each measurement contains.
We're lucky that we're using carefully prepared data, and as such we don't have any missing or erroneous measurements.
Usually things will not be so simple.

In the next section we'll look at how we can more systematically analyze the data.
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

Can you think of other ways we could analyse the data to see if there is a difference in temperature over time? This is an open-ended question; any answers are good answers!
@:@

@:solution
There are no right and wrong answers to this. Here are just a few ideas:

- The main data point is the divergence from the baseline of 1961-1990. So perhaps we could sum the divergences before that period and compare them to the sum of divergences after that baseline? We would have to normalize the sums by the number of years, as there are more years before the baseline than after it. So in effect we would compare average divergence before and after the baseline. We could also compute averages by month, and compare those, if we suspect the divergence changes by month.

- We could plot the data, which allow us to easily see trends in the data that we cannot see when reading numbers.
@:@
