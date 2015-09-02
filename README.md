# cljs-Hello-d3
Clojurescript says hello to d3.js: A very simple illustration of
use of d3.js from Clojurescript without additional libraries.

Simple Clojurescript + D3.js example based on an example at
https://github.com/dribnet/strokes and on Bostock's General Update
Pattern I example at http://bl.ocks.org/mbostock/3808218 .
Think of this as a "Hello World" for d3.js in Clojurescript.
See `src/cljs/hello_world/hello.cljs` for details.

One of the things this example illustrates, apparently, is that using
d3.js from Clojurescript is not much more difficult than using it from
Javascript.  And if you like Clojure, maybe it's easier.  (However, you
will not be able to avoid studying the use of d3.js in Javascript to use
this strategy.)

#### Basic usage:

Open `resources/public/index.html` in a browser.  
Run `lein cljsbuild auto` at a prompt.  
Refresh `index.html` in the browser.

This will display three overlapping circles that darken upon rollover,
followed (to the right or below) by a sequence of letters that's
periodically modified.

#### source files:

`resources/public/index.html`  
`src/cljs/hello_world/hello.cljs`

`hello.cljs` includes comments that no experienced Clojurescript
programmer would include, but that might be helpful to someone new to
Clojurescript though experienced with Clojure.

Note that running `lein cljsbuild auto` will regenerate
`resources/public/hello.js` from `hello.cljs` whenever the latter is
changed.
