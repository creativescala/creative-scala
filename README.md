# Creative Scala

Written by [Dave Gurnell](http://twitter.com/davegurnell) and
[Noel Welsh](http://twitter.com/noelwelsh).
Copyright [Underscore Consulting LLP](http://underscore.io), 2015--2017.

<a rel="license" href="http://creativecommons.org/licenses/by-sa/4.0/"><img alt="Creative Commons Licence" style="border-width:0" src="https://i.creativecommons.org/l/by-sa/4.0/88x31.png" /></a><br />This work is licensed under a <a rel="license" href="http://creativecommons.org/licenses/by-sa/4.0/">Creative Commons Attribution-ShareAlike 4.0 International License</a>.

## Overview

Creative Scala is a free ebook aimed at developers
who have no prior experience in Scala.
It is designed to give you a vital first taste of functional programming.
We assume you have some familiarity with another programming language
but little or no experience with Scala or other functional languages.

This is version 2 of Creative Scala, which is still in development. 
See the `master` branch for the first version.

Our goal is to demonstrate the building blocks that Scala developers use
to create programs in a clear, succinct, declarative manner.
Working through the exercises in the book should take two to three hours,
after which we hope you will have a feel of what Scala can do for your applications.

The exercises in Creative Scala are based on
a functional graphics library called [Doodle][doodle].
Although Doodle is primarily designed to make programming fun and creative,
it is based on universal concepts that can be applied to any business application.

## Building

Creative Scala uses [Underscore's ebook build system][ebook-template].

The simplest way to build the book is to use [Docker Compose](http://docker.com):

- install Docker Compose (`brew install docker-compose` on OS X; or download from [docker.com](http://docker.com/)); and
- run `go.sh` (or `docker-compose run book bash` if `go.sh` doesn't work).

This will open a `bash` shell running inside the Docker container which contains all the dependencies to build the book. From the shell run:

- `npm install`; and then
- `sbt`.

Within `sbt` you can issue the commands `pdf`, `html`, `epub`, or `all` to build the desired version(s) of the book. Targets are placed in the `dist` directory:

[doodle]: https://github.com/underscoreio/doodle
[ebook-template]: https://github.com/underscoreio/underscore-ebook-template

