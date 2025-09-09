# base.g8

A quickstart for a basic empty pekko + pekko http project, preconfigured with some helpful settings.

This includes:
  - the latest versions of pekko + pekko http (at time of writing)
  - a `SpecKit` trait / helper to extend in your tests, making them all consistent (you can change the
    traits this extends if you like a different test style, but still keep things consistent across
    the board)
  - Scalafmt + a config which isn't completely terrible (usually)
  - Makefile to make things easier, including a little script which runs a CI build (including juggling
    scoverage so that it doesn't mess up an assembly build, which is a thing)
  - Some other useful plugins
  - A "core" and "service" subproject layout, to make it easy to separate your service layer from core
    components of your application; this makes it easier to build those separate submodules if you want
    them, and if you don't want them you can simply delete `core` and flatten `build.sbt` to only have
    a single `root` project. You can also add additional submodules easily this way.

## Usage

`sbt new giftig/base.g8` and then fill in your organisation and project names.
