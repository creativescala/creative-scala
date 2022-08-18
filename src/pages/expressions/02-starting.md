# Starting to Program

It's time to write our first program.

We're going to use a Scala *worksheet* to start with. Worksheets make is very easy to run short programs, so are useful for the kinds of programs we're going to write here. For longer programs we usually write code a different way, but we'll worry about that when we get to it.

To create a worksheet:

- Open the Creative Scala Template in Visual Studio Code: Choose `File` > `Open Folder` and select the folder where you downloaded the template.
- In the Explorer on the left hand site, navigate to `src/main/scala`. Right-click the `scala` directory (Control + click on a Mac) and choose "New Scala File".
- Select "Worksheet" when prompted to choose the kind of file.
- Name the worksheet "expressions".

You might find it easier to follow along with the video.

**INSERT VIDEO**

Now we have the worksheet setup we can write our first program. Type in

```scala mdoc:silent
1 + 2
```

and then save the worksheet. The worksheet will run your program and you should see the file change to read

```scala mdoc:silent
1 + 2 // : Int = 3
```

This may take a little while the first time you save it. It will be much faster after the first time.

This very simple program provides an example of all the concepts we'll discuss in the remainder of the chapter:

- the expression `1 + 2`;
- the type `Int`; and
- the value `3`.

We'll learn about those next, but I encourage you to play around with the worksheet right now before moving on.
