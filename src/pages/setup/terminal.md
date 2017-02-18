## Installing Terminal Software and a Text Editors

This section is for you if you've decided to go the terminal and text editor route. 
We need to install:

- the JVM;
- Git;
- a text editor; and
- the code for Creative Scala.


### OS X

Open the terminal.

Install homebrew.

Install `git` using homebrew. 
At the terminal, type

```bash
brew install git
```

Now install the text editor Atom.
Again type at the terminal

```bash
brew install Caskroom/cask/atom
```

Install Scala support: Settings > Install > language-scala

Now we will use Git to get an SBT project that will work with Creative Scala.
Type

```bash
git clone yourname/creative-scala-template
```

replacing `yourname` with your Github username. 
If you have not forked the repository from Github you can clone the original.
This means you won't be (easily) able to upload your work to Github and share it with others.

```bash
git clone underscoreio/creative-scala-template
```

Now change to the directory we just created and run SBT.

```bash
cd creative-scala-template
./sbt.sh
```

SBT should start.
Within SBT type `console`.
Finally type

```scala
Example.image.draw
```

and an image of three circles should appear!

If you've made it this far you've successfully installed all the software you need for work through Creative Scala.

The final step is to load Atom and use it to edit code.


### Windows


### Linux


### Checking It Works

Now everything is installed let's check it works.
