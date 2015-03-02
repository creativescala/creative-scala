# Creative Scala

Written by [Dave Gurnell](http://twitter.com/davegurnell) and
[Noel Welsh](http://twitter.com/noelwelsh).
Copyright Underscore Consulting LLP, 2015.

<a rel="license" href="http://creativecommons.org/licenses/by-nc-sa/4.0/"><img alt="Creative Commons Licence" style="border-width:0" src="https://i.creativecommons.org/l/by-nc-sa/4.0/88x31.png" /></a><br /><span xmlns:dct="http://purl.org/dc/terms/" href="http://purl.org/dc/dcmitype/Text" property="dct:title" rel="dct:type">Creative Scala</span> by <a xmlns:cc="http://creativecommons.org/ns#" href="http://underscore.io" property="cc:attributionName" rel="cc:attributionURL">Underscore Consulting LLP</a> is licensed under a <a rel="license" href="http://creativecommons.org/licenses/by-nc-sa/4.0/">Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License</a>.<br />Based on a work at <a xmlns:dct="http://purl.org/dc/terms/" href="https://github.com/underscoreio/creative-scala" rel="dct:source">https://github.com/underscoreio/creative-scala</a>.

## Overview

Creative Scala is a free ebook aimed at developers
who have no prior experience in Scala.
It is designed to give you a vital first taste of functional programming.
We assume you have some familiarity with another programming language
but little or no experience with Scala or other functional languages.

Our goal is to demonstrate the building blocks that Scala developers use
to create programs in a clear, succinct, declarative manner.
Working through the exercises in the book should take two to three hours,
after which we hope you will have a feel of what Scala can do for your applications.

The exercises in Creative Scala are based on
a functional graphics library called [Doodle][doodle].
Although Doodle is primarily designed to make programming fun and creative,
it is based on universal concepts that can be applied to any business application.

## Building

Creative Scala uses [Underscore's ebook build system][ebook-template],
which is based on *Node*, *Pandoc*, and *Grunt*.
You'll need to install the Grunt project dependencies the
first time you check the project out:

~~~
brew install pandoc
npm install -g grunt-cli coffee-script
npm install
~~~

You will also need to install *LaTeX* to generate PDF output.
Mac users are encouraged to install [MacTeX][mactex].

Once you have installed the required dependencies,
use the following commands to build a single format.
Targets are placed in the `dist` directory:

~~~
grunt pdf
grunt html
grunt epub
~~~

The default behaviour is to build all formats:

~~~
grunt
~~~

Run the following to build the HTML format,
start a local web server,
and rebuild if you change any files:

~~~
grunt watch
~~~

[doodle]: https://github.com/underscoreio/doodle
[ebook-template]: https://github.com/underscoreio/underscore-ebook-template
[mactex]: https://tug.org/mactex/
