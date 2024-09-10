# Summarizing Data

```scala mdoc:invisible
import creativescala.data.explore.HadCrut5.data
```

In the previous section we saw that one of the core problems we face with large amounts of data is summarizing it so we can gain an overview ...

In this section we'll look at two different ways of summarizing data: **summary statistics**, which compute numbers that capture some properties of the data, and **visualizations**, which present data in a graphical form that makes it easier to understand the whole data set.


## Summary Statistics

Summary statistics are values like the average (or mean, use its fancier name) that summarize some attribute of the data. In the case of the average, it is one point that is as close as possible to all the points in the data (for some suitable definition of "close".) Summary statistics are often just called statistics.

Statistics necessarily throw away a lot of information. The average, or mean, only tells us .. It doesn't tell us anything about the spread of data. For that we could use the variance, which tell us about spread but not about asymmetry in that spread. To measure the asymmetry we could use skewness, and so on.

Enough of that. We won't need to get fancy with summary statistics in this book; the humble average will do most of the time. Let's see how we could calculate it for our data. The average, as you perhaps recall, is simply the sum of all the values divided by the number of values we summed. For example, we can average two values with the following method.

```scala
def average(a: Double, b: Double): Double =
  (a + b) / 2.0
```

However, that doesn't help us work with, say, 18 data points (if we sample once per decade), or 173 points (if we sample once per year), or 2082 points (if we include all the data). What we need is a way that works with an arbitrary amount of data, which is what a `List` represents. Here's how we can define `average` on a `List`.

```scala mdoc:silent
def average(data: List[Double]): Double =
  data.sum / data.size
```

That is, maybe, shockingly simple. Later on we'll learn how we could define `sum` and `size` ourselves, but for now we'll use the methods Scala provides and move on.

Now we can compute the average we are left with two problems, which often turn out to be more important that computing the statistics of interest: how to do we transform the data into a form we can work with, and, crucially, which average should we try to compute?


## Visualization
