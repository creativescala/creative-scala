# Loading Data

Acquiring data, and transforming it into a workable state, can be one of the most time consuming parts of data analysis. In a later chapter we'll look at this process in more detail, but right now we're going to take a short-cut. If you're using the [project template][template], as described in the introduction, the data is already available and ready to go.

Open up the file `Explore.scala`

`data` is defined. Let's take a sneak peek at it.

```scala
creativescala.Explore.data
val res0: List[creativescala.data.HadCrut5.Record] =
  List(
    Record(1850,1,-0.67456436,-0.98177195,-0.3673568),
    Record(1850,2,-0.333416,-0.700901,0.034069024),
    Record(1850,3,-0.59132266,-0.9339191,-0.2487262),
    ...
```

Explain the data.

`Record` is year, month, anomaly, lower, upper.

What is a `List`? Zero or more elements, with a defined order.

How can we get an understanding of the data?

```scala
creativescala.Explore.data.size
// 2082
```

```scala
creativescala.Explore.data.head
// creativescala.data.HadCrut5.Record = Record(1850,1,-0.67456436,-0.98177195,-0.3673568)
```

This won't scale. Better to visualize the data.
What should we visualize? For each year as a line of month versus anomaly.

[template]: https://github.com/creativescala/creative-scala-data-template
