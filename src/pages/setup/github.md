## GitHub

We have created a [template] for you that will get you set up with all the code you need to work through Creative Scala.
This template is stored on [GitHub][github], a website for sharing code.

You can copy the template onto your computer, which Git calls cloning, but this means you won't be able to save any changes you make back to GitHub where other people can view them.

If you want to be able to share your changes, you need to make a copy of the template project on GitHub that you own.
Git calls this forking.
You fork the repository on GitHub and then clone *your fork* to your computer.
Then you can save your changes back to your fork on GitHub.

To start this process you need to create a GitHub account, if you do not have one already.

Once you have an account, visit the [template project][template] in your browser.
At the top right is button called "Fork".
Press this button to create your own copy of the template.
You will be taken to a web page displaying your own fork of the template.
Remember the name of this repository. It should be something like `yourname/creative-scala-template` where `yourname` is your GitHub user name.

Now cloning your fork is as simple as running this command and replacing `yourname` with your actual GitHub user name.

```bash
git clone git@github.com:yourname/creative-scala-template.git
```

Now any changes you make can be sent back to your fork on GitHub.
The process for doing this in Git is a bit involved.
When you've made a change you must:

  - `add` the change to what's called Git's index;
  - `commit` the change; and finally
  - `push` the change to the fork.

Here's an example of using the command line to do this.

```bash
git add
git commit -m "Explain here what you did"
git push
```

GitHub makes a nice free graphical tool for using Git, called [GitHub Desktop](https://desktop.github.com/).
It's probably the easiest way to use Git when you're getting started.

[github]: https://github.com/
[template]: https://github.com/creativescala/creative-scala-template
