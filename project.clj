(defproject cljs1 "0.1.0-SNAPSHOT"
  :plugins [[lein-cljsbuild "1.0.6"]]
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.48"]
		 ;[net.drib/strokes "0.5.1"]
		 ;[net.drib/strokes "0.5.2-SNAPSHOT"]
		 ]
  :source-paths ["src/cljs"]
  :cljsbuild {:builds [{:source-paths ["src/cljs"]
                        :compiler {:output-to "resources/public/hello.js"
                                   :optimizations :whitespace
                                   :pretty-print true}}]})
