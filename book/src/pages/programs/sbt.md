# Using The Build System

At the moment we are only working with code in a single file. 
We know how to make it runnable, and it's easy enough to do this when our code is small.
When a project becomes larger, as is split across multiple files, this becomes inconvenient.
In larger projects there are also others things we want to do with the code, such as run tests against it and package it for deployment.
For these reasons we usually use a *build system* to manage working with larger code bases.
In Scala, [sbt] is the most common build system.

If you're using the Creative Scala Template, sbt is already setup. 
This section we walk through some of the basics of using sbt.

sbt is controlled by the settings in the file `build.sbt`.
It's worth taking a look at that file, though we aren't going to go through the details here.

sbt is usually run from the terminal.
To open a terminal from Visual Studio Code, go to the `Terminal` menu and choose "New Terminal".
Then type `sbt` in the terminal to start sbt.

After a little wait while sbt loads, you should see output like

```bash
[info] sbt server started at local:///home/noel/.sbt/1.0/server/edad42af9aa982f4b258/sock
[info] started sbt server
sbt:creative-scala-template> 
```

and sbt is ready to accept commands.

Try the following commands:

* `compile` to compile all the code in the project.
* `test` to run any tests in the project. (There aren't any tests in this project.)
* `run` to run the project. If there is more than one main method (which is probably the case) you'll be prompted to choose one.

In sbt you can prefix any command with `~` to have it run whenever code changes. Try running `~compile` and making a change to your code.

That's all we have to say about sbt at the moment, but we'll return to it when your projects become more complex.

[sbt]: https://www.scala-sbt.org/
