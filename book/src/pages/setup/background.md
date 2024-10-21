## Background

This section gives some background information on some of the tools we'll be using.
If you're an experienced developer a lot of this will be old hat, and you can skip it.
If you're not, this will hopefully give some useful context to the software we'll be working with.



### The Terminal

Back when the world was young and computing was in its infancy, the common user interface of graphical windows, a cursor controlled by a mouse, and interaction by *direct manipulation* didn't exist.
Instead users typed in commands at a device called a *terminal*.
The direct manipulation interface is superior for most uses, but there are some cases for which the terminal or *command line* is preferable.
For example, if we wanted to work out how much space was used by all the files with names starting with `data` in Linux or OS X we can execute the command

```bash
du -hs data*
```

We can break this down into three components:

- the command `du` means disk usage;
- the flags `-hs` mean to print a human readable summary; and
- the pattern `data*` means all the files whose names begin with `data`.

Doing this with a direct manipulation interface would involve clicking on every file we were interested in and be much more time consuming.

The command line has a steep learning curve, but the reward is an extremely powerful tool.
Our usage of the terminal will be very limited, so don't worry if you find the example above intimidating!


### Text Editors

You're probably used to writing documents in a word processor.
A word processor allows us to write text and control the formatting of how it appears on the (increasingly rare) printed page.
A word processor includes powerful commands, such as a spell checker and automatic table of contents generation, to make editing prose easier.

A *text editor* is like a word processor for code.
Whereas a word processor is concerned about visual presentation of text, a text editor has many programming specific functions.
Typical examples include powerful tools to search and replace text, and the ability to quickly jump between the many different files that make up a project.

Text editors date back to the days of terminals and perhaps surprisingly some of these tools are still in use.
The two main ancient and glorious text editors that survive are called Emacs and Vim.
They have very different approaches (except when they don't) and developers tend to use one or the other.
I've been using Emacs for about twenty years, and thus I know in my bones that Emacs is the greatest of all possible text editors and Vim users are knuckle-draggers lumbered with poor taste and an inferior tool.
Vim users no doubt think the same about me.

If there is one thing that unites Vim and Emacs users it's the sure knowledge that new-fangled text editors like Visual Studio Code are bringing about the downfall of our civilization.
Nonetheless we recommend using Visual Studio Code if you're new to this text editing game.
Both Vim and Emacs were created before the common interfaces in use today were created, and using them requires learning a very different way of working.


### The Compiler

The code we write in a text editor is not in a form that a computer can run.
A *compiler* translates it into something the computer can run.
As it does this it performs certain checks on the code.
If these checks don't pass the code won't be compiled and the compiler will print an error message instead.
We'll learn more about what the compiler can check and what it can't in the rest of this book.

When we said the compiler translates the code into something the computer can run, this is not the complete truth in the case of Scala.
The output of the compiler is something called bytecode, and another program, called the Java Virtual Machine (JVM), runs this code@:fnref(complications).

### Integrated Development Environments

Integrated development environments (IDEs) are an alternative approach that combine a text editor, a compiler, and several other programmer tools into a single program.
Some people swear by IDEs, while some people prefer to use the terminal and a text editor.
Our recommendation if you're new to programming is to take the terminal-and-text-editor approach.
If you're already used to an IDE then IntelliJ IDEA is currently the best IDE for Scala development.


### Version Control

Version control is the final tool we'll use.
A version control system is a program that allows us to keep a record of all the changes that have been made to a group of files.
It's very useful for allowing multiple people to work on a project at the same time, and it ensures people don't accidentally overwrite each other's changes.
This is not a huge concern in Creative Scala, but it is good to get some exposure to version control now.

The version control software we'll use is called Git.
It's powerful but complex.
The good news is we don't need to learn much about Git.
Most of our use of Git will be via a website called GitHub, which allows people to share software that is stored in Git.
We use GitHub to share the software used in Creative Scala.


### Onward!

Now that we've got some background, let's move on to installing the software we need to write Scala code.


@:footnote(complications)
This is not itself the entire truth! We usually run Scala code on the JVM, but we can compile Scala to three different formats. The first and most common is JVM bytecode. We can also compile to Javascript, another programming language, which allows us to run Scala code in a web browser. Finally, Scala Native will compile Scala to something a computer *can* run directly without requiring the JVM.
@:@