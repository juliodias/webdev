(defproject webdev "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [ring "1.9.6"]
                 [compojure "1.7.0"]]
  :min-lein-version "2.0"
  :uberjar-name     "webdev.jar"
  :main webdev.core
  :profiles {:dev
             {:main webdev.core/-dev-main}}
  :repl-options {:init-ns webdev.core})