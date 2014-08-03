(defproject jotemdown "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [instaparse "1.3.3"]]
  :main jotemdown.core
  :profiles {:repl {:dependencies [[lein-light-nrepl "0.0.18"]]}}
  :repl-options {:nrepl-middleware [lighttable.nrepl.handler/lighttable-ops]})
