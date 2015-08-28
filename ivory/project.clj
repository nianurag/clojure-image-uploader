(defproject ivory "0.1.0-SNAPSHOT"
  :description "Image enhancement web app"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [http-kit "2.1.18"]
                 [hiccup "1.0.5"]
                 [io.aviso/pretty "0.1.19"]
                 [metosin/compojure-api "0.22.2"]]
  :plugins [[lein-kibit "0.1.2"]]
  :repl-options {
                 :nrepl-middleware [io.aviso.nrepl/pretty-middleware]
                 }
  :main ivory.core
  )
