## Our Recommended Setup

This section is our recommended setup for people new to programming, and describes how to setup Creative Scala with the terminal and [Visual Studio Code][vs-code].
We need to install:

- the JVM;
- Git;
- a text editor (Visual Studio Code); and
- the template project for Creative Scala.


### Installing the JVM

The easiest way to install the JVM is to install [Coursier][coursier]. 
It will also install some other useful Scala programs at the same time.
Follow the instructions on the home page to install it.

Then follow the instructions below that are specific to your operating system.

### MacOs

After installing [Coursier][coursier]:

1. Open the terminal. (Click the magnifying glass icon on the top righthand side of the toolbar. Type in "terminal".)
2. Install [homebrew][homebrew], following the instructions on its site.
3. Install `git` using homebrew. At the terminal, type
    ```bash
    brew install git
    ```
4. Now install the text editor Visual Studio Code. Again type at the terminal
    ```bash
    brew install --cask visual-studio-code
    ```
5. Install Scala support inside Visual Studio Code: Code > Preferences > Extensions and install:

    - Scala (Metals)
    - Scala Syntax (official)

6. Now we will use Git to get an SBT project that will work with Creative Scala. Type
    ```bash
    git clone https://github.com/creativescala/creative-scala-template.git
    ```

@:callout(info)
#### Sharing Your Work

There is an alternative setup that involves first forking the Creative Scala template project, and then cloning it to your computer.
This is the setup to choose if you want to share your work with other people; for example you might be taking Creative Scala with a remote instructor or you might quite rightfully be proud of your work and want others to see it.

In this setup you first *fork* the Creative Scala template.
Then you make a clone of *your* fork.
This alternative setup is described in more detail in the section on GitHub later in this chapter.
@:@

To finish, change to the directory we just created and run sbt:

```bash
cd creative-scala-template
sbt
```

sbt should start. Within sbt type `run` and an image should appear. It it does you have everything correctly installed!

The final step is to load Visual Studio Code and use it to open `Example.scala`, which you can find in `src/main/scala`.


### Windows

After installing [Coursier][coursier]:

1. Download and install [Visual Studio Code][vs-code] using the installer on their site.
2. Open Visual Studio Code and add Scala support: Code > Preferences > Extensions and install:

    - Scala (Metals)
    - Scala Syntax (official)
3. Download and install [Git](https://git-scm.com). At the very end of the installer it gives you the option to open Git. Select that option. A window will open up with a command prompt.
4. In the Git command prompt type
    ```bash
    git clone https://github.com/creativescala/creative-scala-template.git
    ```

@:callout(info)
#### Sharing Your Work

There is an alternative setup that involves first forking the Creative Scala template project, and then cloning it to your computer.
This is the setup to choose if you want to share your work with other people; for example you might be taking Creative Scala with a remote instructor or you might just (quite rightfully) be proud of your work.

In this setup you first *fork* the Creative Scala template.
Then you make a clone of *your* fork.
This alternative setup is described in more detail in the section on GitHub later in this chapter.
@:@

5. Open a normal command-prompt. Click on the Windows icon on the bottom left of the screen. In the search box enter "cmd" and run the program it finds. In the window that is opened up type

    ```bash
    cd creative-scala-template
    ```

    which will change into the directory of the Creative Scala template project we just downloaded.
6. Type

    ```bash
    sbt
    ```

    sbt should start. Within sbt type `run` and an image should appear. It it does you have everything correctly installed!

If you've made it this far you've successfully installed all the software you need for work through Creative Scala.

The final step is to load Visual Studio Code and use it to open `Example.scala`, which you can find in `src\main\scala`.


### Linux

Follow the OS X instructions, using your distributions package manager to install software in place of Homebrew.

[coursier]: https://get-coursier.io/docs/cli-installation
[vs-code]: https://code.visualstudio.com/
[homebrew]: https://brew.sh/
[temurin]: 
