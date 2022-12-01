# sudoku

This was an experiment in teaching myself [Compojure](https://github.com/weavejester/compojure) web development, with nifty transformer middleware, 
[Hiccup](https://github.com/weavejester/hiccup)-based page rendering, API endpoints with [EDN](https://clojuredocs.org/clojure.edn) data in transit, and other stuff.

At the time I was interested in Sudoku briefly because of [David Nolen's incredible Sudoku solver](https://swannodette.github.io/2013/03/09/logic-programming-is-underrated/) 
written in Clojure's [core.logic](https://github.com/clojure/core.logic).

However I ended up implementing Peter Norvig's solver in Clojure instead (or at least trying to—can't remember if I finished),
because I wanted to be able to intervene at each step of the solver's progress to send a WebSocket-based message from server to client, which 
in the end was probably the nicest thing about this tiny demo.
