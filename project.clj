(defproject sudoku "0.1-SNAPSHOT"
  :description "Websockets and Sudoku solvers"
  :jvm-opts ^:replace ["-Xmx1g" "-server"]

  :dependencies [[org.clojure/clojure "1.5.1"]
                 [http-kit "2.1.16"]
                 [ring/ring "1.2.1"]
                 [compojure "1.1.6"]
                 [ring-json-params "0.1.3"]
                 [fogus/ring-edn "0.2.0"]
                 [hiccup "1.0.5"]
                 [clj-json "0.5.0"]
                 [clj-time "0.7.0"]
                 [com.taoensso/timbre "3.2.1"]

                 ; mixed deps
                 [jarohen/chord "0.4.1"]

                 ; clojurescript deps
                 [org.clojure/clojurescript "0.0-2227"]
                 [org.clojure/core.async "0.1.303.0-886421-alpha"]
                 [jayq "2.5.1"]
                 [om "0.6.4"]
                 [secretary "1.1.0"]
                 [net.drib/strokes "0.5.1"]
                 [sablono "0.2.17"]]

  :profiles {:dev {:source-paths ["dev"]
                   :dependencies [[org.clojure/tools.namespace "0.2.4"]
                                  [org.clojure/java.classpath "0.2.2"]]}}

  :main sudoku.core

  :plugins [[lein-cljsbuild "1.0.3"]]

  :source-paths ["src/clj"]

  :resource-paths ["resources"]

  :cljsbuild {:builds [{:id "dev"
                        :source-paths ["src/cljs"]
                        :compiler {
                                   :output-to "resources/public/js/main.js"
                                   :output-dir "resources/public/js/out"
                                   :optimizations :none
                                   :source-map true}}
                       {:id "prod"
                        :source-paths ["src/cljs"]
                        :compiler {
                                   :output-to "resources/public/js/main.js"
                                   :output-dir "resources/public/js/prod"
                                   :optimizations :whitespace
                                   :source-map "resources/public/js/main.js.map"
                                   :pretty-print false}}]})
