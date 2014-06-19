(ns sudoku.page
  (:require [hiccup.page :refer [html5 include-js include-css]]))

(defn main []
  (html5
    [:head
     [:link {:href "http://fonts.googleapis.com/css?family=Roboto&subset=latin,cyrillic-ext,cyrillic,latin-ext" 
             :rel "stylesheet"
             :type "text/css"}] 
     [:meta {:charset "utf-8"}] 
     [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0"}] 
     [:meta {:name "description" :content "Sudoku solver with websocket bindigns"}]
     [:title "Sudoku"]
     (include-css "/css/bootstrap.min.css")
     (include-css "/css/bootstrap-custom.css")]

    [:body 
     
     [:div.container-fluid [:div#main.jumbotron [:h1 "Hello World!"]]]

     (include-js "http://code.jquery.com/jquery.js")
     (include-js "/js/bootstrap.min.js")
     (include-js "/js/history.js")
     (include-js "http://fb.me/react-0.10.0.js")
     (include-js "http://d3js.org/d3.v3.min.js")
     (include-js "/js/out/goog/base.js")
     (include-js "/js/main.js")
     [:script {:type "text/javascript"} "goog.require(\"sudoku.core\")"]]))

